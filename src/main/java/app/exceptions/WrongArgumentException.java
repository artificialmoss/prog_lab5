package app.exceptions;

/**
 * Exception thrown when one or more arguments of the command don't meet the requirements for them
 */
public class WrongArgumentException extends IllegalArgumentException {
    public WrongArgumentException() {
        super();
    }

    public WrongArgumentException(String message) {
        super(message);
    }

    public WrongArgumentException(Exception e) {
        super(e);
    }
}
