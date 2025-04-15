package warren.myblog.service;

import warren.myblog.common.Result;
import warren.myblog.pojo.SysUser;

/*
 * author: Warren
 */
public interface RegisterService {
    /**
     * 注册
     * @param sysUser 用户对象
     * @return
     */
    Result register(SysUser sysUser);
}
