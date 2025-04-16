package warren.myblog.security;

import org.springframework.http.HttpMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import warren.myblog.filter.JwtAuthenticationTokenFilter;


import java.util.List;

/**
 * security配置类
 */
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity //启用方法级安全注解，如 @PreAuthorize
public class SecurityConfig {

    /**
     * 配置密码加密器
     * 使用 BCryptPasswordEncoder 对用户密码进行加密，保障密码存储安全
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 AuthenticationManager
     * AuthenticationManager 是认证流程的核心接口，通过 AuthenticationConfiguration 获取
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 未认证（401）时调用自定义认证入口点
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    /**
     * 权限不足（403）时调用自定义访问拒绝处理器
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    /**
     * 安全过滤链配置：禁用 CSRF，配置 CORS，指定公共接口放行，
     * 其余接口要求认证，设置 Session 为无状态，并添加 JWT 自定义过滤器。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CorsConfigurationSource corsConfigurationSource,
                                                   JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                                                   AuthenticationEntryPoint authenticationEntryPoint,
                                                   AccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                // 启用 CORS 并配置跨域资源共享
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // 关闭 CSRF 防护（JWT 已提供足够的安全性）
                .csrf(csrf -> csrf.disable())
                // 设置 Session 为无状态，适合基于 JWT 的认证
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置全局异常处理器
                .exceptionHandling(exceptions -> exceptions
                        // 未认证（401）时调用自定义认证入口点
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 权限不足（403）时调用自定义访问拒绝处理器
                        .accessDeniedHandler(accessDeniedHandler)
                )
                // 定义请求授权规则
                .authorizeHttpRequests(auth -> auth
                        // 允许 /public 接口无需认证（登录接口公开）
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/doc.html","/swagger-ui/**","/v3/api-docs/**","/webjars/**").permitAll()
                        // 其他所有请求均要求认证
                        .anyRequest().authenticated()
                )  .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                // 将自定义 JWT 认证过滤器添加到过滤器链中，在 UsernamePasswordAuthenticationFilter 之前执行
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * CORS 跨域配置，允许任意来源访问 GET、POST、PUT、DELETE 方法，
     * 设置预检请求缓存时间为 3600 秒。
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of(HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
