package app.exceptions;

/**
 * Exception thrown when the application encounters a wrong command
 */
public class WrongCommandException extends IllegalArgumentException {
    public WrongCommandException() {
        super();
    }

    public WrongCommandException(String message) {
        super(message);
    }

    public WrongCommandException(Exception e) {
        super(e);
    }
}
