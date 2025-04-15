package warren.myblog.aop;

import java.lang.annotation.*;

/**
 * 缓存优化标记注解
 * @author Warron
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    /**
     * 一分钟
     * @return 一分钟对应的毫秒数
     */
    long expire() default 60 * 1000;

    String name() default "";

}