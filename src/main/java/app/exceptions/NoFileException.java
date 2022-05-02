package app.exceptions;

public class NoFileException extends NullPointerException {
    public NoFileException() {
        super();
    }

    public NoFileException(String s) {
        super(s);
    }
}
