package warren.myblog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;
    //评论数
    private Integer commentCounts;
    //浏览量
    private Integer viewCounts;

    private String pictureUrl;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     * 类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight = Article_Common;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 点赞数
     */
    private Long likes;

}
