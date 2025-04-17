package warren.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import warren.myblog.pojo.ArticleCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章收藏表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2025-04-17
 */
@Mapper
public interface ArticleCollectMapper extends BaseMapper<ArticleCollect> {

}
