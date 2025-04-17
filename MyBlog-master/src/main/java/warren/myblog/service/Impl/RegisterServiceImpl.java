package warren.myblog.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import warren.myblog.utils.JWTUtils;
import warren.myblog.common.Result;
import warren.myblog.mapper.SysUserMapper;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.RegisterService;
import warren.myblog.Params.ErrorCode;

import java.util.concurrent.TimeUnit;

/*
 * author: Warren
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册
     *
     * @param sysUser 用户对象
     * @return
     */
    @Override
    @Transactional
    public Result register(SysUser sysUser) {
        if (sysUser.getAccount() == null || sysUser.getPassword() == null || sysUser.getNickname() == null) {
            //必填项中有null
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getAccount, sysUser.getAccount());
        Long count = sysUserMapper.selectCount(sysUserLambdaQueryWrapper);

        if (count > 0) {
            //用户名重复
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        //设置默认头像
        sysUser.setAvatar("/img_1.png");

        //加盐并进行md5加密
        String newPassword = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(newPassword);
        sysUserMapper.insert(sysUser);

        String token = JWTUtils.createToken(String.valueOf(sysUser.getId()));
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        System.out.println("生成的 Token：" + token);
        return Result.success(token);
    }
}
