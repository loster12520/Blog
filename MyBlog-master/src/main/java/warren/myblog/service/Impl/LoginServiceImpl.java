package warren.myblog.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import warren.myblog.security.MyUserDetails;
import warren.myblog.utils.JWTUtils;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.LoginService;
import warren.myblog.service.SysUserService;
import warren.myblog.Params.ErrorCode;
import warren.myblog.Params.LoginParams;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * author: Warren
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${spring.security.salt}")
    private String salt;

    @Autowired
    @Lazy
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     *
     * @param loginParams 登录参数:账号,密码
     * @return 登录成功返回 Token，失败返回错误信息
     */
    @Override
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();

        // 1. 检查参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 2. 加盐并进行 MD5 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((password + salt).getBytes());

        // 3. 根据用户名和密码查询用户是否存在
        SysUser sysUser = sysUserService.findUserByAccount(account, encryptedPassword);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        // 4. 登录成功，更新 lastLogin 时间

        sysUser.setLastLogin(LocalDateTime.now()); // 让 MyBatis-Plus 触发自动填充
        sysUserService.updateById(sysUser); // 触发 updateFill 逻辑

        // 5. 生成 Token
        String token = JWTUtils.createToken(String.valueOf(sysUser.getId()));

        // 6. 存入 Redis，设置 24 小时有效期
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }


    /**
     * 校验 Token 是否有效，必要时重新生成 Token
     *
     * @param token 用户的 Token
     * @return 关联的 SysUser 对象，如果无效则返回 null
     */
    @Override
    public SysUser checkToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

<<<<<<< HEAD
        return myUserDetails.getSysUser();

//        // 1. 判断 Token 是否为空
//        if (StringUtils.isBlank(token)) {
//            return null;
//        }
//
//        // 2. 验证 Token 是否有效
//        Map<String, Object> claims = JWTUtils.checkToken(token);
//        if (claims == null) {
//            return null;
//        }
//
//        // 3. 检查 Redis 是否存在 Token
//        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
//        if (StringUtils.isBlank(userJson)) {
//            // Redis 里没有 Token，可能是过期了，尝试重新生成
//            String userId = claims.get("userId").toString();
//            SysUser sysUser = sysUserService.findUserById(Long.parseLong(userId));
//
//            if (sysUser != null) {
//                // 重新生成 Token
//                String newToken = JWTUtils.createToken(String.valueOf(sysUser.getId()));
//                redisTemplate.opsForValue().set("TOKEN_" + newToken, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
//                return sysUser;
//            }
//            return null;
//        }
//
//        return JSON.parseObject(userJson, SysUser.class);
=======
        // 3. 检查 Redis 是否存在 Token
        String token_ = redisTemplate.opsForValue().get("SYSUSER_TOKEN_");
        if (StringUtils.isBlank(token_)) {
            // Redis 里没有 Token，可能是过期了，尝试重新生成
            String userId = claims.get("userId").toString();
            SysUser sysUser = sysUserService.findUserById(Long.parseLong(userId));

            if (sysUser != null) {
                // 重新生成 Token
                String newToken = JWTUtils.createToken(String.valueOf(sysUser.getId()));
                redisTemplate.opsForValue().set("TOKEN_" + newToken, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
                return sysUser;
            }
            return null;
        }

        return JSON.parseObject(token_, SysUser.class);
>>>>>>> 129a09a9d62a77f0914d885c1cb589e7b50629cf
    }

}
