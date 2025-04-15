package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Comment;
import warren.myblog.pojo.SysUser;
import warren.myblog.vo.Params.CommentParam;

/*
 * author: Warren
 */
public interface CommentsService extends IService<Comment> {
    /**
     * 根据文章id查询评论
     * @param articleId 文章id
     * @return
     */
    Result findCommentsByArticleId(Long articleId);

    /**
     * 评论
     * @param commentParam 评论参数
     * @return
     */
    Result comment(CommentParam commentParam);

    /**
     * 删除评论
     * @param commentId  评论的id
     * @param currentUser 当前登录用户
     * @return
     */
    Result deleteComment(Long commentId, SysUser currentUser);
}
