package warren.myblog.vo;

/*
 * author: Warren
 */
import lombok.Data;

/**
 * 标签前端视图对象
 */
@Data
public class TagVo {

    private Long id;

    private String tagName;

    // 新增返回 createId 字段
    private Long createId;
}