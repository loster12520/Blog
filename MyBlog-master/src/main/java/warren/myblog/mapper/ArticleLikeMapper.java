package warren.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
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

    @Select("SELECT * FROM ms_article_like WHERE user_id = #{userId} AND article_id = #{articleId} LIMIT 1")
    ArticleLike findByArticleIdAndUserId(@Param("userId") Long userId, @Param("articleId") Long articleId);



}
