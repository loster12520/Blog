package warren.myblog.aop;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import warren.myblog.utils.HttpContextUtils;
import warren.myblog.utils.IpUtils;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // ANSI 颜色代码
    private static final String RESET = "\u001B[0m";   // 重置颜色
    private static final String GREEN = "\u001B[32m"; // 绿色（模块 & 操作）
    private static final String BLUE = "\u001B[34m";  // 蓝色（方法）
    private static final String CYAN = "\u001B[36m";  // 青色（IP）
    private static final String YELLOW = "\u001B[33m";// 黄色（参数）
    private static final String RED = "\u001B[31m";   // 红色（执行时间）
    private static final String PURPLE = "\u001B[35m";// 紫色（分割线）

    @Pointcut("@annotation(warren.myblog.aop.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 记录日志
        recordLog(point, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        log.info("{}===================== Log Start ==============================={}", PURPLE, RESET);

        if (logAnnotation != null) {
            log.info("{}[模块] {}{}", GREEN, logAnnotation.module(), RESET);
            log.info("{}[操作] {}{}", GREEN, logAnnotation.operation(), RESET);
        } else {
            log.info("{}[提示] No LogAnnotation present on method.{}", GREEN, RESET);
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("{}[请求方法] {}.{}(){}", BLUE, className, methodName, RESET);

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params;
        try {
            params = (args.length > 0) ? JSON.toJSONString(args[0]) : "No parameters";
        } catch (Exception e) {
            params = "Error parsing parameters";
        }
        log.info("{}[参数] {}{}", YELLOW, params, RESET);

        // 获取 request 并设置 IP 地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = (request != null) ? IpUtils.getIpAddr(request) : "unknown";
        log.info("{}[IP] {}{}", CYAN, ip, RESET);

        log.info("{}[执行时间] {} ms{}", RED, time, RESET);

        log.info("{}===================== Log End ==============================={}", PURPLE, RESET);
    }
}
