package warren.myblog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Long id;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private  LocalDateTime createDate;

    private Long articleId;

    private Long commentatorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

}