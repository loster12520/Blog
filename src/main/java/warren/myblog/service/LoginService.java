package warren.myblog.service;

import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;
import warren.myblog.vo.Params.LoginParams;

/*
 * author: Warren
 */
public interface LoginService {
    Result login(LoginParams loginParams);

    /**
     * 校验token是否合法
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
