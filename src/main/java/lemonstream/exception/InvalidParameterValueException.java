package lemonstream.exception;

public class InvalidParameterValueException extends RuntimeException {

    public InvalidParameterValueException(String key, String value) {
        super("Invalid input parameter:" + key + "=[" + value + "]");
    }
}
