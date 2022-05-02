package app.commands;

import app.collection.Person;
import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;

import java.lang.reflect.Field;

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
        Field[] elementFields = Person.class.getDeclaredFields();
        String[] fieldNames = new String[elementFields.length];
        for (int i = 0; i < elementFields.length; i++) {
            fieldNames[i] = elementFields[i].getName();
        }
        return "Collection type: " + collectionManager.getType() + "\n" +
                "Element type: " + collectionManager.getElementType() + "\n" +
                "Collection size: " + collectionManager.getSize() + "\n" +
                "Initialization date: " + collectionManager.getDate() +"\n" +
                "Element characteristics: " + String.join(", ", fieldNames) + "\n";
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}
