package warren.myblog.exception;

/**
 * 当无法从 SecurityContext 等上下文中正确获取用户信息时抛出此异常。
 * @throws UserInfoRetrievalException 如果无法从上下文中获取用户信息
 */
public class UserInfoRetrievalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserInfoRetrievalException() {
        super();
    }

    public UserInfoRetrievalException(String message) {
        super(message);
    }

    public UserInfoRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInfoRetrievalException(Throwable cause) {
        super(cause);
    }
}
