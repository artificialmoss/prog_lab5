package app.commands;

import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;

import java.time.format.DateTimeFormatter;

/**
 * Command that provides basic information about the collection (its type, size, initialization date, element size and element fields)
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "show information about the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Collection type: " + collectionManager.getType() + "\n" +
                "Element type: " + collectionManager.getElementType() + "\n" +
                "Collection size: " + collectionManager.getSize() + "\n" +
                "Initialization date: " + collectionManager.getDate() +"\n";
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}
