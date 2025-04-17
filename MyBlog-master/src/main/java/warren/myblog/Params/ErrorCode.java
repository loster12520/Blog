package warren.myblog.Params;

import warren.myblog.pojo.Article;

/**
 * 统一错误码
 */
public enum ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    TOKEN_ILLEGAL(10003,"token不合法"),
    ACCOUNT_EXIST(10004,"账号已存在"),
    ARTICLE_NOT_EXIST(10005,"文章不存在"),
    DELETE_ERROR(10006,"删除失败"),
    NOT_FOUND(10007,"不存在"),
    ACCOUNT_PWD_INCORRECT(10008,"用户名或密码错误!~~~"),
    LOGIN_ERROR(10009,"登录过程中发生错误"),
    USERINFO_GET_ERROR(10010,"无法获取用户信息"),
    ALERADAY_LIKES(10011,"您已点赞过该文章"),
    NO_PERMISSION(10012,"无访问权限"),
    NO_LOGIN(10013,"未登录");

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}