package warren.myblog.exception;

/**
 * 当用户未登录或认证失败时抛出此异常。
 * @throws UnauthenticatedException 如果未登录或认证失败
 */
public class UnauthenticatedException extends RuntimeException {
    // 推荐为可序列化类显式声明 serialVersionUID
    private static final long serialVersionUID = 1L;

    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthenticatedException(Throwable cause) {
        super(cause);
    }
}
