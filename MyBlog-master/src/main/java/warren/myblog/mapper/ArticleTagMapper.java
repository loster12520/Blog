package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.ArticleTag;

import java.util.List;

public interface ArticleTagMapper  extends BaseMapper<ArticleTag> {


    List<Long> selectArticleIdsByTagId(Long id);
}