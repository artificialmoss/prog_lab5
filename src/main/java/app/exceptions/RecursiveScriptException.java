package app.exceptions;

public class RecursiveScriptException extends RuntimeException {
    public RecursiveScriptException() {
        super();
    }

    public RecursiveScriptException(String message) {
        super(message);
    }

    public RecursiveScriptException(Exception e) {
        super(e);
    }
}
