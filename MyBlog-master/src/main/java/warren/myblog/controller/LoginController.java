package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.security.SecurityLoginService;
import warren.myblog.Params.LoginParams;

/*
 * author: Warren
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private SecurityLoginService loginService;

    /**
     * security自定义登录
     * @param loginParams 用户对象,包含账户名和密码
     * @return
     */
    @Operation(tags = "用户相关", summary = "登录")
    @PostMapping("/public/login")
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }

}
