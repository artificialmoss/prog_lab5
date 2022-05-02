package app.exceptions;

public class NoScriptException extends IllegalArgumentException {
    public NoScriptException() {
        super();
    }

    public NoScriptException(String message) {
        super(message);
    }
}
