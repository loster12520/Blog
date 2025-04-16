package warren.myblog.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import warren.myblog.Params.ErrorCode;
import warren.myblog.Params.LoginParams;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.SysUserService;
import warren.myblog.utils.JWTUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class SecurityLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;


    public Result login(LoginParams loginParams) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginParams.getAccount(), loginParams.getPassword());
            // 进行认证
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            // 认证成功后获取对象
            MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
            //这里不直接使用强制转换获取sysuser对象
            SysUser user = myUserDetails.getSysUser();

            //使用用户名生成 token
            String username = user.getAccount();
            String token = JWTUtils.createToken(username);

            //存入redis
            redisTemplate.opsForValue().set("SYSUSER_TOKEN_" + username, token, 1, TimeUnit.DAYS);

            return Result.success(token);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.fail(ErrorCode.ACCOUNT_PWD_INCORRECT.getCode(), ErrorCode.ACCOUNT_PWD_INCORRECT.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(ErrorCode.LOGIN_ERROR.getCode(), ErrorCode.LOGIN_ERROR.getMsg());
        }
    }

}