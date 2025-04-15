package warren.myblog.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.aop.Cache;
import warren.myblog.aop.LogAnnotation;
import warren.myblog.common.Result;
import warren.myblog.service.ArticleService;
import warren.myblog.vo.Params.ArticleParam;
import warren.myblog.vo.Params.PageParams;
import java.util.List;

/**
 * author: Warren
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    private static final int limit = 3;
    @Autowired
    private ArticleService articleService;


    /**
     * 首页-文章列表功能,归档功能也一并完成了
     * @param pageParams
     * @return
     */
   // TODO: 请求方法需要改进
    @Operation(tags = "首页文章列表")
    @PostMapping("/list")
    public Result ListArticle(@RequestBody PageParams pageParams) {
        return articleService.ListArticle(pageParams);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @Operation(tags = "最热文章")
    @PostMapping("/hot")
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
    @Operation(tags = "最新文章")
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
    @Operation(tags = "文章归档")
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
    @PostMapping("/delete")
    public Result deleteArticles(@RequestBody List<Long> ids) {
        return articleService.deleteArticles(ids);
    }

    /**
     * 搜索文章
     *
     * @param articleParam
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody ArticleParam articleParam) {
        //写一个搜索接口
        String search = articleParam.getSearch();
        return articleService.searchArticle(search);
    }
}
