package app.exceptions;

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
