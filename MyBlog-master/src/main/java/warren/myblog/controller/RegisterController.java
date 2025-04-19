package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.RegisterService;

/*
 * author: Warren
 */
@RestController
@RequestMapping
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     * @param sysUser 用户对象
     * @return
     */
    @Operation(tags = "用户相关", summary = "注册")
    @PostMapping("/public/register")
    public Result register(@RequestBody SysUser sysUser){
        return registerService.register(sysUser);
    }
}
