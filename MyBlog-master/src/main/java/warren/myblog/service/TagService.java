package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.Tag;
import warren.myblog.vo.Dto.TagDTO;
import warren.myblog.vo.TagVo;

import java.util.List;

/*
 * author: Warren
 */
public interface TagService extends IService<Tag> {
    /**
     * 根据文章 id查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 获取最热标签
     * @param tagNumber
     * @return
     */
    Result getHotTags(int tagNumber);


    /**
     * 根据标签id查找标签名
     * @param id
     * @return
     */
    String findTagNameById(Long id);

    /**
     * 查询所有标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有标签的详细信息
     * @return
     */
    Result findAllDetails();

    /**
     * 实现点击标签可以查询到所有的文章
     * @param id
     * @return
     */
    Result findAllDetailsById(Long id);

    /**
     * 新增标签
     * @return
     */
    Result addtag(TagDTO tagDto);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Result removeTagById(Long id);
}
