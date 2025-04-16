package warren.myblog.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import warren.myblog.mapper.SysUserMapper;
import warren.myblog.pojo.SysUser;

import java.util.ArrayList;
import java.util.Objects;

/*
 * author: Warren
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**根据用户名获取 sysuser对象
     *
     * @param username 登录用户名
     * @return
     * @throws UsernameNotFoundException 用户名不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<SysUser>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,username);
        SysUser user = sysUserMapper.selectOne(lambdaQueryWrapper);

        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        return new MyUserDetails(user, new ArrayList<>());
    }
}
