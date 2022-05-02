package app.exceptions;

public class WrongAmountOfArgumentsException extends IllegalArgumentException {
    public WrongAmountOfArgumentsException() {
        super();
    }

    public WrongAmountOfArgumentsException(String message) {
        super(message);
    }
}
