package lemonstream.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String invalidParamter) {
        super("Invalid input parameter:" + invalidParamter);
    }
}
