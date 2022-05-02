package app.exceptions;

/**
 * Exception thrown when instead of command the application encounters an empty line
 */
public class NoCommandException extends NullPointerException {
    public NoCommandException() {
        super();
    }
}
