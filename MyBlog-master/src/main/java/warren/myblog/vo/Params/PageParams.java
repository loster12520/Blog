package warren.myblog.vo.Params;

import lombok.Data;

/*
 * author: Warren
 */

/**
 * 分页参数类
 */
@Data
public class PageParams {
    private int page=1;
    private int pagesize=1000;

    /**
     * 这是后面添加的两个属性,如果不添加的话,进入对应的分类标签,会查询到全部的文章而不是在当前分类下的文章
     */

    private  Long categoryId;
    private Long TagId;

    private String year;

    private String month;


}
