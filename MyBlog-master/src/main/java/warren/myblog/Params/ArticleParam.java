package warren.myblog.Params;

import lombok.Data;
import warren.myblog.vo.CategoryVo;
import warren.myblog.vo.TagVo;

import java.util.List;
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String searchKeyword;

    /** 新增：前端上传完图片后传回的 URL */
    private String pictureUrl;

}