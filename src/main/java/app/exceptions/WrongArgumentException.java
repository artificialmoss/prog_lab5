package app.exceptions;

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
