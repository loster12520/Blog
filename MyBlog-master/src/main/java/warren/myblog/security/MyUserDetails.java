package warren.myblog.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import warren.myblog.pojo.SysUser;

import java.util.Collection;
import java.util.List;

/**
 * 自定义userdrtails实现UserDetails接口
 */
@Data
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private SysUser sysUser;    // 你自己定义的用户pojo
    private Collection<GrantedAuthority> grantedAuthorities;    // 权限组信息

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
