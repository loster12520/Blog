package warren.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warren.myblog.common.Result;
import warren.myblog.service.TagService;
import warren.myblog.vo.Dto.TagDTO;

/*
 * author: Warren
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 新增标签
     * @param tagDto
     * @return
     */
    @PostMapping("/add")
    public Result addTag(@RequestBody TagDTO tagDto){
        return tagService.addtag(tagDto);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result removeTagById(@PathVariable("id") Long id){

        System.out.println("进入该方法!");return tagService.removeTagById(id);
    }

    /**
     * 获取最热标签,前六条
     * @return
     */
    @GetMapping("/hot")
    public Result getHots() {
        int tagNumber = 6;
        return tagService.getHotTags(tagNumber);
    }


    /**
     * 查询所有标签
     * @return
     */
    @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }

    /**
     * 导航-查询所有标签的详细信息
     * @return
     */
    @GetMapping("/detail")
    public Result findAllDetails() {
        return tagService.findAllDetails();
    }

    /**
     * 实现点击标签可以查询到所有的文章
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result findAllDetailsById(@PathVariable("id")Long id) {
        return tagService.findAllDetailsById(id);
    }
}
