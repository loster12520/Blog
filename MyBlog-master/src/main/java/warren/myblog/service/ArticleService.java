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
     * @param pageParams 分页参数
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit 需要显示的最热文章数
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit 需要显示的最新文章数
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchive();

    /**
     * 获取文章详情
     * @param id 文章id
     * @return
     */
    Result viewArticle(Long id);

    /**
     * 发布文章
     * @param articleParam 和文章有关的参数
     * @return
     */
    Result publishArticle(ArticleParam articleParam);


    /**
     * 删除文章
     * @param ids 需要删除的文章的id集合
     * @return
     */
    Result deleteArticle(List<Long> ids);


    /**
     * 搜索文章
     * @param searchKeyword 和文章标题有关的关键词
     * @return
     */
    Result searchArticle(String searchKeyword);
}
