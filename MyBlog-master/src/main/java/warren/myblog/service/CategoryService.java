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
     * 获取所有分类
     * @return
     */
    Result findAllCategory();

    /**
     * 导航栏的文章分类功能
     * @return
     */
    Result findAllCategoryDetails();

    /**
     * 根据分类id查找分类
     *
     * @param categoryId 分类id
     * @return
     */
    CategoryVo findCategotyById(Long categoryId);


    /**
     * 点击导航栏后显示文章分类和该分类下文章(这里直接调用了首页文章列表功能)
     * @param id 分类id
     * @return
     */
    Result findCategoryDetailsById(Long id);
}
