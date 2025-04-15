package warren.myblog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import warren.myblog.common.Result;

/**
 * 处理全局异常
 */
//对加了@Controller注解的方法进行拦截处理(通过AOP实现)
@ControllerAdvice
public class AllExceptionHandler {

    //处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常,请联系管理员哦~");
    }

}