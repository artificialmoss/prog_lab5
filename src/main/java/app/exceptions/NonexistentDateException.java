package app.exceptions;

/**
 * Exception thrown when the date read by the application doesn't correspond to a real date
 */
public class NonexistentDateException extends IllegalArgumentException {
    public NonexistentDateException() {
        super();
    }

    public NonexistentDateException(String message) {
        super(message);
    }
}
