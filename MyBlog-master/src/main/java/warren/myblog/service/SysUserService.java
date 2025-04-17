package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.vo.UserVo;

/*
 * author: Warren
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据账号名和密码进行登录
     * @param account 账号名
     * @param password 密码
     * @return 登录返回一个SysUser对象
     */
    SysUser findUserByAccount(String account, String password);

    /**
     * 根据token查找用户信息
     * @param token jwt生成的用户token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据用户名查找作者(用户)信息
     * @param username 作者(用户)信息
     * @return
     */
    SysUser findUserByUserName(String username);

    /**
     * 根据评论人id获取对应的vo对象
     * @param id 评论人id
     * @return
     */
    UserVo findUserVoById(Long id);

    /**
     * 点赞文章
     * @param articleId 文章id
     * @return
     */
    Result likes(Long articleId);

    SysUser findUserById(Long authorId);
}
