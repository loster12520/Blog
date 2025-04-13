package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Category;
import warren.myblog.vo.CategoryVo;

/*
 * author: Warren
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据分类id查找分类
     *
     * @param categoryId
     * @return
     */
    CategoryVo findCategotyById(Long categoryId);

    Result findAllCategory();

    /**
     * 导航,文章分类
     * @return
     */
    Result findAllCategoryDetails();

    /**
     * 实现点进文章分类后, 在文章列表上面显示当前分类的信息
     * @return
     */
    Result findCategoryDetailsById(Long id);
}
