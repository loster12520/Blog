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
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Operation(tags = "登录后获取用户详细信息")
    @GetMapping("/currentUser")
    public Result getCurrenUser(){
        return sysUserService.getUserInfo();
    }

    /**
     * 点赞文章
     * @param articleId 文章id
     * @return
     */
    @Operation(tags = "点赞文章")
    @GetMapping("/likes/{articleId}")
    public Result likes(@PathVariable Long articleId){
        return sysUserService.likes(articleId);
    }




}
