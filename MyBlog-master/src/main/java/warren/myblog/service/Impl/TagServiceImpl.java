package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import warren.myblog.common.Result;

import warren.myblog.mapper.ArticleMapper;
import warren.myblog.mapper.ArticleTagMapper;
import warren.myblog.mapper.TagMapper;
import warren.myblog.pojo.ArticleTag;
import warren.myblog.pojo.SysUser;
import warren.myblog.pojo.Tag;
import warren.myblog.service.TagService;
import warren.myblog.Dto.TagDTO;
import warren.myblog.Params.ErrorCode;
import warren.myblog.utils.SecurityUtils;
import warren.myblog.vo.TagVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * author: Warren
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 拷贝属性
     * @param tag 标签
     * @return
     */
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    /**
     * 拷贝属性
     * @param tagList 标签集合
     * @return
     */
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    /**
     * 根据文章 id查询标签列表
     * @param articleId 文章id
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //MyBatisPlus无法直接执行多表查询,这里使用xml编写sql
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**获取最热标签
     * 标签拥有的文章数量最多的为最热标签
     * sql:查询tag_id并分组进行计数,从大到小进行排列,取前六个
     * @param tagNumber
     * @return
     */
    @Override
    public Result getHotTags(int tagNumber) {
        //返回前六条标签的id集合
        List<Long>tagIds=tagMapper.getHotTags(tagNumber);

        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        //根据标签id查询对应的标签
        //sql:select * from tag where id in(1,2,3,...)
        List<Tag>tagList=tagMapper.getTagById(tagIds);
        return Result.success(tagList);
    }


    /**
     * 查询所有标签
     * @return
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    /**
     * 查询所有标签详细信息 比上面的更加详细
     * @return
     */
    @Override
    public Result findAllTagsDetails() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));

    }

    /**
     * 实现点击标签可以查询到所有的文章(这里也调用了首页文章列表功能)
     * @param id 标签id
     * @return
     */
    @Override
    public Result findAllDetailsByTagId(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    /**
     * 新增标签
     * @return
     */
    @Override
    public Result addTag(TagDTO tagDto) {

        SysUser sysUser = SecurityUtils.getCurrentUser();
        Long sysUserId = sysUser.getId();
        Tag tag=new Tag();
        tag.setTagName(tagDto.getTagName());
        tag.setCreateId(sysUserId);

        tagMapper.insert(tag);
        return Result.success("添加标签成功!");
    }

    /**
     * 删除标签
     * @param id 标签id
     * @return
     */
    @Transactional  // 保证所有操作在同一事务内，要么全部成功，要么全部回滚
    @Override
    public Result removeTagById(Long id) {

        SysUser currentUser = SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录,不能删除标签,请先登录!");
        }

        // 查询该标签
        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            return Result.fail(ErrorCode.NOT_FOUND.getCode(), "标签不存在哦~");
        }
        // 打印日志调试
        System.out.println("当前用户ID: " + currentUser.getId());
        System.out.println("标签创建者ID: " + tag.getCreateId());
        // 校验当前用户是否为标签创建者
        if (!tag.getCreateId().equals(currentUser.getId())) {
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), "只能删除自己创建的标签!");
        }

        // 1. 获取该标签下所有关联文章的ID列表
        List<Long> articleIds = articleTagMapper.selectArticleIdsByTagId(id);

        // 2. 如果存在关联文章，则删除这些文章（此操作会直接删除文章记录）
        if (!CollectionUtils.isEmpty(articleIds)) {
            articleMapper.deleteBatchIds(articleIds);
        }

        // 3. 删除 ms_article_tag 表中所有关联该标签的记录
        LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(lambdaQueryWrapper);

        // 4. 删除 ms_tag 表中的标签记录
        int tagDeleted = tagMapper.deleteById(id);

        if (tagDeleted > 0) {
            return Result.success("删除标签成功!");
        } else {
            return Result.fail(ErrorCode.DELETE_ERROR.getCode(), ErrorCode.DELETE_ERROR.getMsg());
        }
    }



}
