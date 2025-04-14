package warren.myblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import warren.myblog.common.LoginInterceptor;
import warren.myblog.common.UserThreadLocal;

/**
 * 网络配置
 *
 * @author Warren
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    public WebMVCConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    private final LoginInterceptor loginInterceptor;

    /**
     * 配置跨域
     *
     * @param registry 跨域注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 如果仅需允许 http://localhost:8080，可以在这里指定
        // 如果需要允许多个域名，可以多次调用 allowedOrigins 或改用 allowedOriginPatterns
        registry.addMapping("/**")
                // 允许前端的域名（端口）
                .allowedOrigins("http://localhost:8080")
                // 允许的 HTTP 方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许携带的请求头，这里使用 * 可以匹配所有
                .allowedHeaders("*")
                // 是否允许跨域携带 Cookie、Authorization 等凭证信息
                .allowCredentials(true);
    }

    /**
     * 增加登录拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                        "/test",
                        "/comments/create/change",
                        "/articles/publish",
                        "/comments/delete",
                        "/articles/deleteBatch",
                        "/tags/*" // 拦截所有 /tags/ 下的单级请求
                )
                // 排除不需要拦截的接口（例如 GET 请求的接口）
                .excludePathPatterns(
                        "/tags/hot",       // 获取最热标签
                        "/tags",           // 获取所有标签（findAll）
                        "/tags/detail",    // 查询所有标签详情
                        "/tags/detail/*"   // 查询单个标签对应的文章详情
                );
    }

}
