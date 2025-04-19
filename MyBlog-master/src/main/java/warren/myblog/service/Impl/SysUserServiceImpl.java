package warren.myblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warren.myblog.common.Result;
import warren.myblog.mapper.ArticleLikeMapper;
import warren.myblog.mapper.ArticleMapper;
import warren.myblog.mapper.SysUserMapper;
import warren.myblog.pojo.ArticleLike;
import warren.myblog.pojo.SysUser;
import warren.myblog.security.MyUserDetails;

import warren.myblog.service.LoginService;
import warren.myblog.service.SysUserService;
import warren.myblog.Params.ErrorCode;
import warren.myblog.vo.SysUserVo;
import warren.myblog.vo.UserVo;

import java.time.LocalDateTime;

import static warren.myblog.Params.ErrorCode.*;

/*
 * author: Warren
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ArticleLikeMapper articleLikeMapper;

    @Autowired
    private ArticleMapper articleMapper;


    /**
     * 根据账号名和密码进行登录
     *
     * @param account 账号名
     * @param password 密码
     * @return  登录返回一个SysUser对象
     */
    @Override
    public SysUser findUserByAccount(String account, String password) {
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
     * @param
     * @return
     */
    @Override
    public Result getUserInfo() {
        // 1. 从 SecurityContext 中获取 Authentication 并判断登录状态
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }

        // 2. 从 principal 获取自定义的 MyUserDetails
        Object principal = auth.getPrincipal();
        if (!(principal instanceof MyUserDetails)) {
            return Result.fail(ErrorCode.USERINFO_GET_ERROR.getCode(), ErrorCode.USERINFO_GET_ERROR.getMsg());
        }
        MyUserDetails userDetails = (MyUserDetails) principal;

        // 3. 构造返回的 VO 对象
        SysUser user = userDetails.getSysUser();
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(user, vo);
        return Result.success(vo);
    }


    /**
     * 根据评论人id获取对应的vo对象
     *
     * @param id 评论人id
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


    @Transactional
    @Override
    public Result likes(Long articleId) {
        // 1. 获取当前用户的 ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Result.fail(NO_LOGIN.getCode(),NO_LOGIN.getMsg());
        }

        Object principal = authentication.getPrincipal();
        Long userId;
        if (principal instanceof MyUserDetails) {
            userId = ((MyUserDetails) principal).getSysUser().getId();
        } else {
            return Result.fail(USERINFO_GET_ERROR.getCode(), USERINFO_GET_ERROR.getMsg());
        }

        // 2. 检查是否已点赞
        ArticleLike existingLike = articleLikeMapper.findByArticleIdAndUserId(userId, articleId);
        if (existingLike != null) {
            return Result.fail(ALERADAY_LIKES.getCode(), ALERADAY_LIKES.getMsg());
        }

        // 3. 插入点赞记录
        ArticleLike newLike = new ArticleLike();
        newLike.setArticleId(articleId);
        newLike.setUserId(userId);
        newLike.setCreateTime(LocalDateTime.now());
        articleLikeMapper.insert(newLike);

        // 4. 更新文章的点赞数
        articleMapper.increseLikes(articleId);

        return Result.success("点赞成功!");
    }

    /**
     * 根据用户id 获取用户
     * @param authorId 用户id
     * @return
     */
    @Override
    public SysUser findUserById(Long authorId) {
        return sysUserMapper.selectById(authorId);

    }

}
