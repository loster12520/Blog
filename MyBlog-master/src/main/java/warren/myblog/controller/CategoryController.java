package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
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
     * @return
     */
    @Operation(tags = "获取所有文章分类")
    @GetMapping("/list")
    public Result getAllCategory() {
        return categoryService.findAllCategory();
    }

    /**
     * 导航栏的文章分类功能
     * @return
     */
    @Operation(tags = "导航栏的文章分类功能")
    @GetMapping("/detail")
    public Result getAllCategoryDetails() {
        return categoryService.findAllCategoryDetails();
    }

    /**
     * 点击导航栏文章分类后, 在文章列表上面显示当前分类和该分类下的文章(这里直接调用了首页文章列表功能)
     * @return
     */
    @Operation(tags = "点击导航栏后显示文章分类和该分类下文章")
    @GetMapping("/detail/{id}")
    public Result findCategoryDetailsById(@PathVariable("id") Long id) {
        return categoryService.findCategoryDetailsById(id);
    }
}
