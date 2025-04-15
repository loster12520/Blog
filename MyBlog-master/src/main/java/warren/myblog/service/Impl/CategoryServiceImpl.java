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
     * @param categoryId 分类id
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
     * 获取所有分类
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
        List<Category> categories = categoryMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(categories));
    }

    /**
     * 实点击导航栏后显示文章分类和该分类下文章(这里直接调用了首页文章列表功能)
     * @param id 分类id
     * @return
     */
    @Override
    public Result findCategoryDetailsById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    /**
     * 拷贝属性 将category转换为categoryVo
     * @param category 分类
     * @return
     */
    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    /**
     * 拷贝属性 将categoryList转为categoryVoList
     * @param categoryList 分类集合
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
