package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.SysUser;

/*
 * author: Warren
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
