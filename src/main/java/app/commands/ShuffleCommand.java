package app.commands;

import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;

public class ShuffleCommand extends Command {
    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        super("shuffle", "shuffle the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        collectionManager.shuffle();
        if (!scriptMode) return "The collection has been shuffled.";
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
