package warren.myblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class CommentVo  {

    //防止前端精度损失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //评论的人
    private UserVo commentator;

    private String content;

    private List<CommentVo> childrens;

    private LocalDateTime createDate;

    private Integer level;
    //给谁评论
    private UserVo toUser;


}
