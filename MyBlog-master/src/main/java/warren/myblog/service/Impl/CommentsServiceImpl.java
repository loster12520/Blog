package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warren.myblog.common.Result;

import warren.myblog.mapper.ArticleMapper;
import warren.myblog.mapper.CommentsMapper;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.Comment;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.CommentsService;
import warren.myblog.service.SysUserService;
import warren.myblog.utils.SecurityUtils;
import warren.myblog.vo.CommentVo;
import warren.myblog.Params.CommentParam;
import warren.myblog.vo.UserVo;

import java.util.ArrayList;
import java.util.List;


/*
 * author: Warren
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comment> implements CommentsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 根据文章id查询评论列表
     * @param articleId 文章id
     * @return
     */
    @Override
    public Result findCommentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        commentLambdaQueryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentsMapper.selectList(commentLambdaQueryWrapper);
        return Result.success(copyList(comments));
    }

    /**
     * 评论
     * @param commentParam 评论参数
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {

        //获取当前登录的用户,即发布文章的作者
        SysUser sysUser = SecurityUtils.getCurrentUser();
        Comment comment = new Comment();

        // 设置文章ID、评论人ID、评论内容
        comment.setArticleId(commentParam.getArticleId());
        comment.setCommentatorId(sysUser.getId());
        comment.setContent(commentParam.getContent());

        // 处理父评论及层级
        Long parent = commentParam.getParent();
        comment.setLevel((parent == null || parent == 0) ? 1 : 2);
        comment.setParentId(parent == null ? 0 : parent);

        // 处理被评论的用户ID
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);

        // 插入数据库
        this.commentsMapper.insert(comment);

        // 更新文章评论总数
        Long articleId = commentParam.getArticleId();
        Article article = articleMapper.selectById(articleId);
        if (article != null) {
            article.setCommentCounts(article.getCommentCounts() + 1);
            articleMapper.updateById(article);
        }

        // 通过selectById获取完整的Comment数据
        Comment insertedComment = this.commentsMapper.selectById(comment.getId());

        // 调用copy()方法来构造CommentVo，其中会设置commentator字段
        CommentVo commentVo = copy(insertedComment);

        return Result.success(commentVo);
    }


    /**
     * 拷贝属性,将commentList转化为commentVoList
     * @param comments 评论集合
     * @return
     */
    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    /**
     * 拷贝属性,将comment转化为commentVo
     * @param comment 评论
     * @return
     */
    private CommentVo copy(Comment comment) {
        //前端 评论视图对象
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        //评论人的id
        Long commentatorId = comment.getCommentatorId();
        //根据评论人的id返回评论人的 基本信息:头像,id,昵称
        UserVo userVo = this.sysUserService.findUserVoById(commentatorId);
        //设置 评论人的基本信息
        commentVo.setCommentator(userVo);

//        if (1 == comment.getLevel()) {
            //评论的评论 ,comment.getId()是评论的id
            List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
            //获取子评论
            commentVo.setChildrens(commentVoList);

//        }
        //如果当前是子评论
        if (comment.getLevel() > 1) {
            //拿到并设置被评论人的id
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }


    /**
     * 根据父评论的id获取子评论
     * @param id 父评论id
     * @return
     */
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //父评论id
        queryWrapper.eq(Comment::getParentId, id);
        //子评论在第二层,因为只设置了两层评论
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = this.commentsMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    /**
     * 删除评论
     * @param commentId 评论的id
     * @param currentUser 当前登录用户
     * @return
     */
    @Override
    public Result deleteComment(Long commentId, SysUser currentUser) {
        // 1. 查找评论
        Comment comment = commentsMapper.selectById(commentId);
        if (comment == null) {
            return Result.fail(404, "要删除的评论不存在哦~");
        }
        // 2. 查找评论对应的文章
        Article article = articleMapper.selectById(comment.getArticleId());
        if (article == null) {
            return Result.fail(404, "文章不存在哦~");
        }
        // 3. 判断是否有权限删除
        boolean isCommentAuthor = comment.getCommentatorId().equals(currentUser.getId());
        boolean isArticleAuthor = article.getAuthorId().equals(currentUser.getId());
        if (!isCommentAuthor && !isArticleAuthor) {
            return Result.fail(403, "您无权删除该评论哦~");
        }
        // 4. 执行删除
        int rows = commentsMapper.deleteById(commentId);
        if (rows > 0) {
            article.setCommentCounts(article.getCommentCounts()-1);
            articleMapper.updateById(article); // 确保更新数据库中的评论数量
            return Result.success("删除成功!");
        } else {
            return Result.fail(500, "删除失败");
        }
    }
}
