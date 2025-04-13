package warren.myblog.controller;

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
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     * @param sysUser
     * @return
     */
    @PostMapping
    public Result register(@RequestBody SysUser sysUser){
        return registerService.register(sysUser);
    }
}
