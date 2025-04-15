package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import org.springframework.data.repository.query.Param;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.Category;
import warren.myblog.vo.Dto.ArchiveDTO;

import java.util.List;

/*
 * author: Warren
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArchiveDTO> listArchives();

    /**
     * 首页-文章列表功能,归档功能也一并完成了
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);

}
