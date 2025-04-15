package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warren.myblog.common.Result;
import warren.myblog.mapper.SysUserMapper;
import warren.myblog.pojo.Comment;
import warren.myblog.pojo.SysUser;
import warren.myblog.service.LoginService;
import warren.myblog.service.SysUserService;
import warren.myblog.vo.CommentVo;
import warren.myblog.vo.LoginUserVo;
import warren.myblog.vo.Params.ErrorCode;
import warren.myblog.vo.UserVo;

/*
 * author: Warren
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;


    /**
     * 根据账号名和密码进行登录校验
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getAccount, account).eq(SysUser::getPassword, password);
        sysUserLambdaQueryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname);
        //加快查询速度
        sysUserLambdaQueryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        return sysUser;
    }

    /**
     * 根据token查找用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        //校验token
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ILLEGAL.getCode(), ErrorCode.TOKEN_ILLEGAL.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    /**
     * 根据id查找作者(用户)信息,不存在则返回一个默认用户
     * @param id
     * @return
     */
    @Override
    public SysUser findUserById(long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return sysUser==null?new SysUser("warren"):sysUser;
    }

    /**
     * 根据评论人id获取对应的vo对象
     *
     * @param id
     * @return
     */
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser commentor = sysUserMapper.selectById(id);
        //如果为空设置默认昵称
        if (commentor == null) {
            commentor = new SysUser();
            commentor.setId(1L);
            commentor.setAvatar("static/img/1.png");
            commentor.setNickname("Warren");
        }
        UserVo commentVo = new UserVo();
        BeanUtils.copyProperties(commentor, commentVo);
        return commentVo;
    }

}
