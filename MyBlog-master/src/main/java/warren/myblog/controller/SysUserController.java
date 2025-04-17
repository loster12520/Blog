package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
