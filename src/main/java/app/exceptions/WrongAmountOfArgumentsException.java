package app.exceptions;

/**
 * Exception thrown when the amount of arguments of the command doesn't meet the requirements
 */
public class WrongAmountOfArgumentsException extends IllegalArgumentException {
    public WrongAmountOfArgumentsException() {
        super();
    }

    public WrongAmountOfArgumentsException(String message) {
        super(message);
    }
}
