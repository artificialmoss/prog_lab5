package app.commands;

import app.exceptions.WrongAmountOfArgumentsException;

public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "close the program without saving");
    }

    @Override
    public String execute(boolean scriptMode) {
        System.out.println("Closing application...");
        System.exit(0);
        return null;
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}
