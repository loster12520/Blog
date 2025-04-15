package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import warren.myblog.pojo.Tag;

import java.util.List;

/*
 * author: Warren
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章 id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> getHotTags(int tagNumber);

    List<Tag> getTagById(List<Long> tagIds);
}
