package app.exceptions;

public class NonexistentDateException extends IllegalArgumentException {
    public NonexistentDateException() {
        super();
    }

    public NonexistentDateException(String message) {
        super(message);
    }
}
