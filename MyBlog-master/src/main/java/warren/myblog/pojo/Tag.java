package warren.myblog.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Tag {
    @TableId(type = IdType.AUTO)  // 指定自增策略
    private Long id;

    private String avatar;

    private String tagName;

    private Long createId;
}