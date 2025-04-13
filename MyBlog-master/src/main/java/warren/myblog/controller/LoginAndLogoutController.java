package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.LoginService;
import warren.myblog.vo.Params.LoginParams;

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
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
