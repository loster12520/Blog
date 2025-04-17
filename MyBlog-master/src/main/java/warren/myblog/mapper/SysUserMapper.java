package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import warren.myblog.pojo.SysUser;

import java.util.List;

/*
 * author: Warren
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select *from ms_sys_user where account=#{username}")
    SysUser findUserByUserName(String username);
}
