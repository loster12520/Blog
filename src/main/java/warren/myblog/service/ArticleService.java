package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Article;
import warren.myblog.vo.Params.ArticleParam;
import warren.myblog.vo.Params.PageParams;

import java.util.List;

/*
 * author: Warren
 */
public interface ArticleService extends IService<Article> {
    /**
     * 首页-文章列表功能,归档功能也一并完成了
     * @param pageParams
     * @return
     */
    Result ListArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    Result viewArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);


    /**
     * 删除文章
     * @param ids
     * @return
     */
    Result deleteArticles(List<Long> ids);


    /**
     * 搜索文章
     * @param search
     * @return
     */
    Result searchArticle(String search);
}
