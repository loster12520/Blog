package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;

import warren.myblog.pojo.SysUser;
import warren.myblog.security.MyUserDetails;
import warren.myblog.service.CommentsService;
import warren.myblog.Params.CommentParam;
import warren.myblog.utils.SecurityUtils;

import java.util.Map;


/*
 * author: Warren
 */
@RestController
@RequestMapping
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 根据文章id查询所有评论
     * @param articleId
     * @return
     */
    @Operation(tags = "查询文章下的所有评论")
    @GetMapping("/public/comment/listComments/{articleId}")
    public Result comments(@PathVariable Long articleId) {
        return commentsService.findCommentsByArticleId(articleId);
    }

    /**
     * 评论功能
     * @param commentParam 评论参数
     * @return
     */
    @Operation(tags = "评论")
    @PostMapping("/user/remark")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentsService.comment(commentParam);
    }

    /**
     * 删除评论功能
     * 仅评论所属文章作者或评论作者可以删除
     * @param params 一个map,包含评论id和当前登录用户
     * @return
     */
    @Operation(tags = "删除评论")
    @PostMapping("/user/deleteComment")
    public Result deleteComment(@RequestBody Map<String, Long> params) {
        Long commentId = params.get("commentId");
        if (commentId == null) {
            return Result.fail(400, "缺少评论ID");
        }

        SysUser currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return Result.fail(401, "请先登录");
        }
        // 调用 service 层执行删除逻辑
        return commentsService.deleteComment(commentId, currentUser);
    }

}
