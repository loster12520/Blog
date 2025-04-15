package warren.myblog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import warren.myblog.pojo.Article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 元数据处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createDate",  LocalDateTime.class,  LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastLogin",  LocalDateTime.class,  LocalDateTime.now());
        this.strictInsertFill(metaObject, "weight", Integer.class, Article.Article_Common);
    }

    /**
     * 更新时自动填充
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "lastLogin", LocalDateTime.class,  LocalDateTime.now());
    }
}
