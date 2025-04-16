package warren.myblog.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import warren.myblog.pojo.SysUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义userdetails实现UserDetails接口
 */
@Data
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private SysUser sysUser;// 用户
    private Collection<GrantedAuthority> grantedAuthorities;  // 权限组信息

    public MyUserDetails(SysUser user, List<GrantedAuthority> authorities) {
        this.sysUser = user;
        this.grantedAuthorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getAccount();
    }

    /**
     * 账户是否过期,true表示未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定,true表示未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的凭据（如密码）是否过期,true表示未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否启用,true表示启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
