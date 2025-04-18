package warren.myblog.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import warren.myblog.exception.UnauthenticatedException;
import warren.myblog.exception.UserInfoRetrievalException;
import warren.myblog.pojo.SysUser;
import warren.myblog.security.MyUserDetails;

public class SecurityUtils {

    /**
     * 从 Spring Security 上下文中获取当前已认证的 SysUser。
     * @return 当前已登录的 SysUser
     * @throws UnauthenticatedException 如果未登录或认证失败
     * @throws UserInfoRetrievalException 如果无法从上下文中获取用户信息
     */
    public static SysUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new UnauthenticatedException("未登录，无法获取用户信息");
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof MyUserDetails)) {
            throw new UserInfoRetrievalException("用户信息格式错误");
        }

        return ((MyUserDetails) principal).getSysUser();
    }
}
