package warren.myblog.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.aop.Cache;
import warren.myblog.aop.LogAnnotation;
import warren.myblog.common.Result;
import warren.myblog.service.ArticleService;
import warren.myblog.Params.ArticleParam;
import warren.myblog.Params.PageParams;
import java.util.List;

/**
 * author: Warren
 */
@RestController
@RequestMapping
public class ArticleController {

    private static final int limit = 3;
    @Autowired
    private ArticleService articleService;


    /**
     * 首页-文章列表功能,归档功能也一并完成了
     * @param pageParams 分页参数,包含page,pageSize,categoryId等
     * @return
     */
    @Operation(tags = "首页文章列表")
    @PostMapping("/public/article/list")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }


    /**
     * 最热文章
     * @return
     */
    @Operation(tags = "最热文章")
    @GetMapping("/public/article/hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @Operation(tags = "最新文章")
    @GetMapping("/public/article/new")
    @Cache(expire = 5 * 60 * 1000, name = "new_article")
    public Result newArticle() {
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @Operation(tags = "文章归档")
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    @GetMapping("/public/article/archive")
    public Result listArchive() {
        return articleService.listArchive();
    }


    /**
     * 获取文章详情
     * @return
     */
    @Operation(tags = "获取文章详情")
    @GetMapping("/public/article/view/{articleId}")
    public Result viewArticle(@PathVariable("articleId") Long id) {
        return articleService.viewArticle(id);
    }

    /**
     * 发布文章
     * @param articleParam 发布文章有关的参数
     * @return
     */
    @Operation(tags ="发布文章" )
    @PostMapping("/user/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publishArticle(articleParam);
    }

    /**
     * 删除文章
     * @param ids 文章id集合
     * @return
     */
    @Operation(tags="删除文章")
    @PostMapping("/user/deleteArticle")
    public Result deleteArticle(@RequestBody List<Long> ids) {
        return articleService.deleteArticle(ids);
    }

    /**
     * 搜索文章
     * @param articleParam 和文章有关的参数
     * @return
     */
    @Operation(tags = "搜索文章")
    @PostMapping("/public/article/search")
    public Result search(@RequestBody ArticleParam articleParam) {
        String searchKeyword = articleParam.getSearchKeyword();
        return articleService.searchArticle(searchKeyword);
    }
}
