package app.commands;

import app.exceptions.WrongAmountOfArgumentsException;
import app.exceptions.WrongArgumentException;

/**
 * Abstract class that is extended by all commands.
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * Constructor
     * @param name name
     * @param description description
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Method for executing the command
     * @param scriptMode Current mode of the command manager (interactive/script)
     * @return Response
     */
    public abstract String execute(boolean scriptMode);

    /**
     * Method for describing the command
     * @return Name and description of the command in a readable format
     */
    public String describe() {
        return name + " — " + description;
    }

    /**
     * Method for setting the command's arguments
     * @param input An array that contains command and its arguments (line read from console/script split by whitespace characters)
     * @return Command with its arguments set if the arguments meet the requirements
     * @throws WrongAmountOfArgumentsException Is thrown when the amount of arguments doesn't equal the expected amount for the command
     * @throws WrongArgumentException Is thrown when some of the arguments don't meet the requirements
     */
    public abstract Command setArgs(String[] input) throws WrongAmountOfArgumentsException, WrongArgumentException;
}
