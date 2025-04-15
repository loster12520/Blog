package warren.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import warren.myblog.pojo.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
