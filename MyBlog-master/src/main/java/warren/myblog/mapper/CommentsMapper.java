package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import warren.myblog.common.Result;
import warren.myblog.pojo.Comment;
import warren.myblog.vo.CommentVo;

/*
 * author: Warren
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comment> {

    @Select("SELECT c.*, u.nickname, u.avatar FROM ms_comment c " +
            "LEFT JOIN ms_sys_user u ON c.commentator_id = u.id " +
            "WHERE c.id = #{id}")
    CommentVo findById(Long id);

}
