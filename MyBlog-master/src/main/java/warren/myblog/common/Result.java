package warren.myblog.common;

/*
 * author: Warren
 */


/**
 * 统一响应结果集
 */
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(boolean success, Integer code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }

    /**
     * 获取
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{success = " + success + ", code = " + code + ", msg = " + msg + ", data = " + data + "}";
    }
}