package warren.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.vo.SysUserVo;
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
     * @param
     * @return
     */
    Result getUserInfo();


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

    /**
     * 根据用户id查找用户
     * @param authorId 用户id
     * @return
     */
    SysUser findUserById(Long authorId);

    /**
     * 修改用户信息
     * @param sysUserVo 用户信息
     * @return
     */
    Result modifyUserInfo(SysUserVo sysUserVo);

//    /**
//     * 点击用户头像获取用户详情
//     * @return
//     */
//    Result getUserDetails();
}
