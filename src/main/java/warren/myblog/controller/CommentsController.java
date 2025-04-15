package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.common.UserThreadLocal;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.CommentsService;
import warren.myblog.vo.Params.CommentParam;

import java.util.Map;

/*
 * author: Warren
 */
@RestController
@RequestMapping("/comment")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * 根据文章id查询评论列表
     *
     * @param id
     * @return
     */
    @GetMapping("/list/{articleId}")
    public Result comments(@PathVariable("articleId") Long id) {
        return commentsService.CommentsByArticlId(id);
    }

    /**
     * 评论功能
     *
     * @param commentParam
     * @return
     */
    @PostMapping("/user/remark")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentsService.comment(commentParam);
    }

    /**
     * 删除评论功能
     * 仅评论所属文章作者 或 评论作者 可以删除
     */
    @PostMapping("/user/delete")
    public Result deleteComment(@RequestBody Map<String, Long> params) {
        System.out.println("删除评论请求到达");
        Long commentId = params.get("commentId");
        if (commentId == null) {
            return Result.fail(400, "缺少评论ID");
        }
        // 获取当前登录用户
        SysUser currentUser = UserThreadLocal.get();
        if (currentUser == null) {
            return Result.fail(401, "请先登录");
        }
        // 调用 service 层执行删除逻辑
        return commentsService.deleteComment(commentId, currentUser);
    }

}
