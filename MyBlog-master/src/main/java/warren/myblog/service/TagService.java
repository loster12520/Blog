package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Tag;
import warren.myblog.Dto.TagDTO;
import warren.myblog.vo.TagVo;

import java.util.List;

/*
 * author: Warren
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId 文章id
     * @return List<TagVo>
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 获取最热标签,取前六条
     * @param tagNumber 需要的最热标签数
     * @return
     */
    Result getHotTags(int tagNumber);

    /**
     * 查询所有标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有标签的详细信息
     * @return
     */
    Result findAllTagsDetails();

    /**
     * 实现点击标签可以查询到所有的文章
     * @param id 标签id
     * @return
     */
    Result findAllDetailsByTagId(Long id);

    /**
     * 新增标签
     * @return
     */
    Result addTag(TagDTO tagDto);

    /**
     * 删除标签
     * @param id 标签id
     * @return
     */
    Result removeTagById(Long id);
}
