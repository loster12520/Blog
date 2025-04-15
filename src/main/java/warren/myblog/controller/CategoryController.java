package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warren.myblog.common.Result;
import warren.myblog.service.CategoryService;

/*
 * author: Warren
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("/list")
    public Result getCategories() {
        return categoryService.findAllCategory();
    }

    /**
     * 导航文章分类
     *
     * @return
     */
    @GetMapping("/detail")
    public Result findAllCategoryDetails() {
        return categoryService.findAllCategoryDetails();
    }

    /**
     * 实现点进文章分类后, 在文章列表上面显示当前分类的信息
     *
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result findCategoryDetailsById(@PathVariable("id") Long id) {
        return categoryService.findCategoryDetailsById(id);
    }
}
