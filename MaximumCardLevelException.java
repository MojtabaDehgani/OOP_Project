package game.exceptions;

public class MaximumCardLevelException extends RuntimeException{
    public MaximumCardLevelException() {
    }

    public MaximumCardLevelException(String message) {
        super(message);
    }

    public MaximumCardLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximumCardLevelException(Throwable cause) {
        super(cause);
    }

    public MaximumCardLevelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
