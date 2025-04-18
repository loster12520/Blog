package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.SysUserService;

/*
 * author: Warren
 */
@RestController
@RequestMapping("/sysuser")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Operation(tags = "获取用户登录信息")
    @GetMapping("/currentUser")
    public Result getCurrenUser(@RequestHeader("token") String token){
        return sysUserService.findUserByToken(token);
    }
}
