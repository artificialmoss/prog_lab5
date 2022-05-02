package app.exceptions;

/**
 * Exception thrown for fields that can be null when the (interactive/script) input implies the null value for them
 */
public class NullElementException extends NullPointerException {
    public NullElementException() {
        super();
    }
}
