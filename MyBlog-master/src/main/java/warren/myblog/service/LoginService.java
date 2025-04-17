package warren.myblog.service;

import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.Params.LoginParams;

/*
 * author: Warren
 */
public interface LoginService {
    /**
     * 登录
     * @param loginParams 登录参数,包含account和password
     * @return 登录成功返回 Token，失败返回错误信息
     */
    Result login(LoginParams loginParams);

    /**
     * 校验token是否合法
     * @param token jwt生成的token
     * @return
     */
    SysUser checkToken(String token);

//    /**
//     * 退出登录
//     * @param token jwt生成的token
//     * @return
//     */
//    Result logout(String token);
}
