package app.commands;

import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.CollectionManager;

/**
 * Command for grouping elements of the collection by their heights and showing the size of each group
 */
public class GroupByHeightCommand extends Command {
    private final CollectionManager collectionManager;

    public GroupByHeightCommand(CollectionManager collectionManager) {
        super("group", "group elements by their height, show the size of each group");
        this.collectionManager = collectionManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        return collectionManager.groupByHeight();
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        return this;
    }
}