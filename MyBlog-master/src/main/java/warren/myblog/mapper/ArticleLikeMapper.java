package warren.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import warren.myblog.pojo.ArticleLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章点赞表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2025-04-17
 */
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

    @Select("select * from ms_article_like where article_id=#{articleId} and user_id=#{userId}")
    ArticleLike findByArticleIdAndUserId(Long articleId, Long userId);
}
