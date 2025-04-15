package warren.myblog.aop;


import com.alibaba.fastjson2.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import warren.myblog.common.Result;

import java.lang.reflect.Method;
import java.time.Duration;


/**
 * 使用aop实现缓存优化
 * @author Warren
 */
@Aspect
@Component
public class CacheAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(warren.myblog.aop.Cache)")
    public void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Signature signature = proceedingJoinPoint.getSignature();
            //类名
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();


            Class[] parameterTypes = new Class[proceedingJoinPoint.getArgs().length];
            Object[] args = proceedingJoinPoint.getArgs();
            //参数
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params.append(JSON.toJSONString(args[i]));
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params.toString())) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = new StringBuilder(DigestUtils.md5Hex(params.toString()));
            }
            Method method = proceedingJoinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)) {
                log.info("走了缓存~~~,{},{}", className, methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ {},{}", className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            log.warn(throwable.getMessage());
        }
        return Result.fail(-999, "系统错误");
    }

}
