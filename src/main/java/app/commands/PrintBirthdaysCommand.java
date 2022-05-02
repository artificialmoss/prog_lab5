package app.commands;

import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.CollectionManager;

/**
 * Command that prints birthdays of all elements that are currently stored in the collection
 */
public class PrintBirthdaysCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintBirthdaysCommand(CollectionManager collectionManager) {
        super("print_birthdays", "show the birthday field values for all elements in the descending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        return collectionManager.descendingBirthdays();
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}
