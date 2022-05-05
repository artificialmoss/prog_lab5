package app.commands;

import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.CollectionManager;
import app.utils.JsonParser;

/**
 * Command that saves the collection to either the path specified by environment variable LAB5_PATH or the default path.
 * If both are unavailable, the collection will not be saved.
 */
public class SaveCommand extends Command {
    private CollectionManager collectionManager;
    private JsonParser parser;

    public SaveCommand(CollectionManager collectionManager, JsonParser parser) {
        super("save" , "save the collection to file");
        this.collectionManager = collectionManager;
        this.parser = parser;
    }

    @Override
    public String execute(boolean scriptMode) {
        String res = collectionManager.save(parser);
        if (!scriptMode) return res;
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
