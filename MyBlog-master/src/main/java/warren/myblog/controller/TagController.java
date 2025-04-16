package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.TagService;
import warren.myblog.Dto.TagDTO;

/*
 * author: Warren
 */
@RestController
@RequestMapping
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 新增标签
     * @param tagDto 标签对象
     * @return
     */
    @Operation(tags = "新增标签")
    @PostMapping("/user/add")
    public Result addTag(@RequestBody TagDTO tagDto){
        return tagService.addTag(tagDto);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @Operation(tags = "删除标签")
    @DeleteMapping("/user/{id}")
    public Result removeTagById(@PathVariable("id") Long id){
      return tagService.removeTagById(id);
    }

    /**
     * 获取最热标签,取前六条
     * @return
     */
    @Operation(tags = "最热标签(前六条)")
    @GetMapping("/public/tags/hot")
    public Result getHots() {
        int tagNumber = 6;
        return tagService.getHotTags(tagNumber);
    }


    /**
     * 查询所有标签
     * @return
     */
    @Operation(tags = "查询所有标签")
    @GetMapping("/public/tags/list")
    public Result getAllTag() {
        return tagService.findAll();
    }

    /**
     * 导航栏-查询所有标签的详细信息
     * @return
     */
    @Operation(tags = "导航-查询所有标签的详细信息")
    @GetMapping("/public/tags/detail")
    public Result getAllTagDetails() {
        return tagService.findAllTagsDetails();
    }

    /**
     * 实现点击标签可以查询到所有的文章(这里也调用了首页文章列表功能)
     * @param id 标签id
     * @return
     */
    @Operation(tags = "实现点击标签可以查询到所有的文章")
    @GetMapping("/public/tags/detail/{id}")
    public Result findAllDetailsById(@PathVariable("id")Long id) {
        return tagService.findAllDetailsByTagId(id);
    }
}
