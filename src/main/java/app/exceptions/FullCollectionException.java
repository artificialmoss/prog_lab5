package app.exceptions;

/**
 * Exception thrown when all available ids for elements are taken
 */
public class FullCollectionException extends IndexOutOfBoundsException {
    public FullCollectionException(String message) {
        super(message);
    }
}
