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
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result CommentsByArticlId(Long id);

    /**
     * 评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);

    Result deleteComment(Long commentId, SysUser currentUser);
}
