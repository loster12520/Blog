package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.aop.Cache;
import warren.myblog.aop.LogAnnotation;
import warren.myblog.common.Result;
import warren.myblog.common.UserThreadLocal;
import warren.myblog.mapper.ArticleMapper;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.ArticleService;
import warren.myblog.vo.Params.ArticleParam;
import warren.myblog.vo.Params.PageParams;

import java.util.List;

import static warren.myblog.vo.Params.ErrorCode.DELETE_ERROR;
import static warren.myblog.vo.Params.ErrorCode.PARAMS_ERROR;

/**
 * author: Warren
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private static final int limit = 3;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 查询文章信息
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    public Result ListArticle(@RequestBody PageParams pageParams) {
        return articleService.ListArticle(pageParams);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 最新文章
     *
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result newArticle() {
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     *
     * @return
     */
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 获取文章详情
     *
     * @return
     */
    @PostMapping("/view/{id}")
    public Result viewArticle(@PathVariable Long id) {
        return articleService.viewArticleById(id);
    }

    /**
     * 发布文章
     *
     * @param articleParam
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    /**
     * 删除文章
     *
     * @param ids
     * @return
     */
    @PostMapping("/deleteBatch")
    public Result deleteArticles(@RequestBody List<Long> ids) {
        return articleService.deleteArticles(ids);
    }

    /**
     * 搜索文章
     *
     * @param articleParam
     * @return
     */
    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam) {
        //写一个搜索接口
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }
}
