package app.commands;

import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;
import app.exceptions.WrongArgumentException;

public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private Long id;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove id", "remove the element with the specified id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 2) {
            throw new WrongAmountOfArgumentsException();
        }
        try {
            id = Long.parseLong(input[1]);
        } catch (NumberFormatException e) {
            throw new WrongArgumentException(e);
        }
        return this;
    }

    @Override
    public String execute(boolean scriptMode) {
        String res = collectionManager.removeById(id);
        if (!scriptMode) return res;
        return null;
    }
}
