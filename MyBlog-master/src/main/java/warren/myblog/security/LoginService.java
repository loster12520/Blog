package warren.myblog.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import warren.myblog.Dto.SysUserDto;
import warren.myblog.Params.ErrorCode;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.utils.JWTUtils;

import java.util.concurrent.TimeUnit;

public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    public Result login(SysUserDto sysUserDto) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(sysUserDto.getNickname(), sysUserDto.getPassword());
            // 进行认证
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            //认证成功后获取对象
            SysUser user = (SysUser) authenticate.getPrincipal();

            String username = user.getNickname();
            Long userId = user.getId();
            //生成token
            String token = JWTUtils.createToken(username);


            redisTemplate.opsForValue().set("SYSUSER_TOKEN_" + username, token, 1, TimeUnit.DAYS);

            return Result.success(token);
        } catch (AuthenticationException e) {
            // 认证失败，返回错误信息
            return Result.fail(ErrorCode.ACCOUNT_PWD_INCORRECT.getCode(),ErrorCode.ACCOUNT_PWD_INCORRECT.getMsg());
        } catch (Exception e) {
            // 处理其他可能的异常
            return Result.fail(ErrorCode.LOGIN_ERROR.getCode(),ErrorCode.LOGIN_ERROR.getMsg());
        }
    }

}