package warren.myblog.vo;

/*
 * author: Warren
 */
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * 文章前端视图对象
 */
@Data
public class ArticleVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createDate; // 直接使用 Date，前端自动变成 "yyyy-MM-dd HH:mm"

    private UserVo author;

   private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;


    private List<CategoryVo> categorys;

    private List<ArticleVo>relatedArticles;

}
