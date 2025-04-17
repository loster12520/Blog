package warren.myblog.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import warren.myblog.utils.JWTUtils;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. 从请求头中获取 Token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 验证 Token 的有效性
        var claims = JWTUtils.checkToken(token);
        if (claims == null) {
            throw new RuntimeException("无效的 token");
        }

        // 3. 从 Token 中获取用户名
        String username = (String) claims.get("username");
        if (!StringUtils.hasText(username)) {
            throw new RuntimeException("token 中无有效的用户名信息");
        }

        // 4. 从 Redis 中获取存储的 Token
        Object redisTokenObj = redisTemplate.opsForValue().get("SYSUSER_TOKEN_" + username);
        if (redisTokenObj == null) {
            throw new RuntimeException("用户未登录或登录信息已过期");
        }

        String redisToken = redisTokenObj.toString();
        if (!token.equals(redisToken)) {
            throw new RuntimeException("Token 不匹配或已失效");
        }

        // 5. 加载用户详情
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new RuntimeException("无法加载用户详情");
        }

        // 6. 设置认证信息到上下文
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 7. 放行请求
        filterChain.doFilter(request, response);
    }
}
