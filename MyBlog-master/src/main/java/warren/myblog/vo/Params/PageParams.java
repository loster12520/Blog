package warren.myblog.vo.Params;

import lombok.Data;

/*
 * author: Warren
 */

/**
 * 分页参数
 *
 */
@Data
public class PageParams {
    private int page=1;
    private int pagesize=1000;

    private  Long categoryId;
    private Long TagId;

    private String year;

    private String month;


}
