package warren.myblog.common;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.LoginService;
import warren.myblog.vo.Params.ErrorCode;

/**
 * 登录拦截器（LoginInterceptor）
 * 该拦截器用于拦截所有请求，检查请求头中是否包含合法的 Token，
 * 以此判断用户是否已登录，并决定是否放行请求。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 自动注入 LoginService，用于 Token 验证
    @Autowired
    private LoginService loginService;

    /**
     * 在请求处理之前执行拦截
     * @param request  HttpServletRequest 对象，包含请求信息
     * @param response HttpServletResponse 对象，用于返回响应
     * @param handler  处理器对象，通常是一个 Controller 方法
     * @return 如果返回 true，表示放行请求；false 则表示拦截请求
     * @throws Exception 可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果请求的目标不是一个控制器方法（可能是静态资源等），直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取请求头中的 Authorization 令牌（Token）
        String token = request.getHeader("Authorization");

        // 打印请求信息，方便调试
        System.out.println("=================request start===========================");
        String requestURI = request.getRequestURI();
        System.out.println("request uri:" + requestURI);
        System.out.println("request method:" + request.getMethod());
        System.out.println("token:" + token);
        System.out.println("=================request end===========================");

        // 如果 Token 为空，则返回未登录的错误信息
        if (token == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        // 通过 LoginService 校验 Token 并获取用户信息
        SysUser sysUser = loginService.checkToken(token);

        // 如果用户信息为空，说明 Token 非法或已过期，返回未登录错误
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        // 用户已登录，放行请求
        //我希望在controller中 直接获取用户的信息 怎么获取?
        UserThreadLocal.put(sysUser);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
