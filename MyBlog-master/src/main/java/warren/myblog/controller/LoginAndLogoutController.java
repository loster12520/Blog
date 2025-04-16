package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.LoginService;
import warren.myblog.Params.LoginParams;

/*
 * author: Warren
 */
@RestController
@RequestMapping
public class LoginAndLogoutController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @return  登录成功返回 Token，失败返回错误信息
     */
    @Operation(tags = "登录")
    @PostMapping("/public/login")
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }

    /**
     * 退出登录
     * @return 登录成功返回 Token，失败返回错误信息
     */
    @Operation(tags = "退出登录")
    @GetMapping("/user/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }
}
