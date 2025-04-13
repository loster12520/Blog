package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warren.myblog.common.Result;
import warren.myblog.common.UserThreadLocal;
import warren.myblog.mapper.ArticleBodyMapper;
import warren.myblog.mapper.ArticleMapper;
import warren.myblog.mapper.ArticleTagMapper;
import warren.myblog.pojo.*;
import warren.myblog.service.*;
import warren.myblog.vo.ArticleBodyVo;
import warren.myblog.vo.ArticleVo;
import warren.myblog.vo.Params.ArticleParam;
import warren.myblog.vo.Params.PageParams;
import warren.myblog.vo.TagVo;
import warren.myblog.vo.UserVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static warren.myblog.vo.Params.ErrorCode.DELETE_ERROR;
import static warren.myblog.vo.Params.ErrorCode.PARAMS_ERROR;

/*
 * author: Warren
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentsService commentsService;

    /**
     * 文章列表,归档功能也一并完成了
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result ListArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPagesize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    /**
     * 显示最热文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        articleLambdaQueryWrapper.select(Article::getId, Article::getTitle);
        //注意这里的空格
        articleLambdaQueryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(articleLambdaQueryWrapper);
        //sql:select id,title from article order by viewcounts desc limit 3
        //后面两个属性设置为false,表示不需要tag和author属性
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 查询最新文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        articleLambdaQueryWrapper.select(Article::getId, Article::getTitle);
        articleLambdaQueryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(articleLambdaQueryWrapper);
        //sql:select id,title from article order by createDate desc limit 3
        //后面两个属性设置为false,表示不需要tag和author属性
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 文章归档
     *
     * @return
     */
    @Override
    public Result listArchives() {
        return Result.success(articleMapper.listArchives());
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public Result viewArticleById(Long id) {
        //1.根据id查询文章信息
        //2.根据bodyid和categoryid查询文章详情
        Article article = this.articleMapper.selectById(id);

        threadService.updateViewCount(articleMapper, article);

        ArticleVo articleVo = copy(article, true, true, true, true);
        return Result.success(articleVo);
    }


    /**
     * 发布文章
     *
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1.发布文章就是构建 article对象
         *  2. authorId 就是当前登录用户的id
         *  3.拿到标签 发布文章后会生成一个文章id 这时需要将标签和文章关联起来
         *  4.存储文章内容body
         */
        //获取当前登录的用户,即发布文章的作者
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        boolean isEdit = false;
        //如果传入id,则是编辑,没有id,则是新增(发布)文章
        if (articleParam.getId() != null) {
            article = new Article();
            article.setId(articleParam.getId());

            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(articleParam.getCategory().getId());
            articleMapper.updateById(article);
            isEdit = true;
        } else {
            article = new Article();
            //设置作者id为当前登录用户的id
            article.setAuthorId(sysUser.getId());

            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(articleParam.getCategory().getId());
            article.setCreateDate(LocalDateTime.now());
            article.setCommentCounts(0);
            //插入文章后会生成一个id
            this.articleMapper.insert(article);
        }

        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            Long articleId = article.getId();
            for (TagVo tag : tags) {
                //标签和文章关联表的操作
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());

        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Result deleteArticles(List<Long> ids) {
         if (ids == null || ids.isEmpty()) {
            return Result.fail(PARAMS_ERROR.getCode(), "没有需要删除的文章");
        }

        List<Article> articles = articleMapper.selectBatchIds(ids);
        if (articles == null || articles.isEmpty()) {
            return Result.fail(PARAMS_ERROR.getCode(), "没有找到对应的文章");
        }

        // 如果都属于当前用户，则执行批量删除
        int count = articleMapper.deleteBatchIds(ids);
        if (count > 0) {
            return Result.success("删除成功!");
        } else {
            return Result.fail(DELETE_ERROR.getCode(), DELETE_ERROR.getMsg());
        }
    }

    /**
     *  编辑文章
     * @param id
     * @return
     */



    /**
     * 拷贝属性
     *
     * @param articleRecords
     * @param isTag
     * @param isAuthor
     * @return
     */
    private List<ArticleVo> copyList(List<Article> articleRecords, Boolean isTag, Boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article articleRecord : articleRecords) {
            articleVoList.add(copy(articleRecord, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    /**
     * 重载方法
     *
     * @param articleRecords
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> articleRecords, Boolean isTag, Boolean isAuthor, Boolean isBody, Boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article articleRecord : articleRecords) {
            articleVoList.add(copy(articleRecord, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    /**
     * 拷贝属性
     *
     * @param article
     * @param isTag      是否需要标签
     * @param isAuthor   是否需要作者
     * @param isBody     是否需要文章内容
     * @param isCategory 是否需要文章分类
     * @return
     */
    private ArticleVo copy(Article article, Boolean isTag, Boolean isAuthor, Boolean isBody, Boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategotyById(categoryId));
        }
        return articleVo;
    }


    /**
     * 根据文章内容id获取内容
     *
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        //前端视图
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();

        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;

    }

    /**
     * 搜索文章
     * @param search
     * @return
     */
    @Override
    public Result searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.like(Article::getTitle,search);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }
}
