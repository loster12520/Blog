package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warren.myblog.common.Result;
import warren.myblog.mapper.CategoryMapper;
import warren.myblog.pojo.Category;
import warren.myblog.service.CategoryService;
import warren.myblog.vo.CategoryVo;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Warren
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据分类id查找分类
     * @param categoryId
     * @return
     */
    @Override
    public CategoryVo findCategotyById(Long categoryId) {

        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo=new CategoryVo();
        categoryVo.setCategoryName(category.getCategoryName());
        categoryVo.setId(categoryId);
        categoryVo.setAvatar(category.getAvatar());
        return categoryVo;
    }

    /**
     * 获取文章分类
     * @return
     */
    @Override
    public Result findAllCategory() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(lambdaQueryWrapper);

        return Result.success(copyList(categories));
    }

    /**
     * 导航,文章分类  比上面的获取文章分类更加详细
     * @return
     */
    @Override
    public Result findAllCategoryDetails() {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(lambdaQueryWrapper);

        return Result.success(copyList(categories));
    }

    /**
     * 实现点进文章分类后, 在文章列表上面显示当前分类的信息
     * @param id
     * @return
     */
    @Override
    public Result findCategoryDetailsById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    /**
     * 拷贝属性
     * @param category
     * @return
     */
    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    /**
     * 拷贝属性
     * @param categoryList
     * @return
     */
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }
}
