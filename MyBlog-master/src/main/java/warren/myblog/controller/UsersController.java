package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.SysUserService;

/*
 * author: Warren
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;
    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/currentUser")
    public Result getCurrenUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
