package app.exceptions;

public class FullCollectionException extends IndexOutOfBoundsException {
    public FullCollectionException(String message) {
        super(message);
    }
}
