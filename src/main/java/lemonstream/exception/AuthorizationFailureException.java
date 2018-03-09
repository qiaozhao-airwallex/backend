package lemonstream.exception;

public class AuthorizationFailureException extends RuntimeException {
    public AuthorizationFailureException() {
        super("You are not authorized to perform this operation");
    }

    public AuthorizationFailureException(String msg) {
        super(msg);
    }

}
