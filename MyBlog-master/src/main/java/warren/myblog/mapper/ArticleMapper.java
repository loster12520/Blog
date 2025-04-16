package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import warren.myblog.pojo.Article;
import warren.myblog.Dto.ArchiveDTO;

import java.util.List;

/*
 * author: Warren
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 文章归档
     * @return
     */
    List<ArchiveDTO> listArchive();

    /**
     * 首页-文章列表功能,归档功能也一并完成了
     * @param page 分页对象
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param year 年份
     * @param month 月份
     * @return
     */
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);

}
