package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.Article;
import warren.myblog.pojo.SysUser;
import warren.myblog.vo.UserVo;

/*
 * author: Warren
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据id查询作者
     * @param authorId
     * @return
     */
    SysUser findAuthorById(Long authorId);

    /**
     * 根据账号名和密码进行登录校验
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查找用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据id查找用户
     * @param l
     * @return
     */
    SysUser findUserById(long l);

    /**
     * 根据评论人id获取对应的vo对象
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);
}
