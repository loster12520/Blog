package warren.myblog.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import warren.myblog.utils.JWTUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. 从请求头中获取 token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 如果没有 token，则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 解析 token，获取其中的载荷数据
        Map<String, Object> claims = JWTUtils.checkToken(token);
        if (claims == null) {
            // 如果解析失败，说明 token 无效
            throw new RuntimeException("无效的 token");
        }

        // 3. 从 token 中获取 username（当前只存储了 username）
        String username = (String) claims.get("username");
        if (username == null) {
            throw new RuntimeException("token 中无有效的用户名信息");
        }

        // 4. 从 Redis 中根据 username 获取已保存的用户信息
        Object loginUser = redisTemplate.opsForValue().get("SYSUSER_TOKEN_" + username);
        if (loginUser == null) {
            throw new RuntimeException("用户未登录或登录信息已过期");
        }

        // 5. 将登录信息包装成 UsernamePasswordAuthenticationToken，并放入 SecurityContextHolder 中，以便后续权限校验使用
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行过滤链
        filterChain.doFilter(request, response);
    }
}
