package app.commands;

import app.collection.Person;
import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;
import app.exceptions.WrongArgumentException;
import app.utils.Mode;

public class UpdateByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private Long id;
    private Person p;
    private final Mode mode;

    public UpdateByIdCommand(CollectionManager collectionManager, Mode mode) {
        super("update id", "update the element with the specified id");
        this.collectionManager = collectionManager;
        this.mode = mode;
    }

    @Override
    public String execute(boolean scriptMode) {
        if (collectionManager.getById(id) != null) {
            collectionManager.updateById(id, p);
            if (!scriptMode) return "Element has been updated.";
        }
        if (!scriptMode) return "Element with this id doesn't exist.";
        return null;
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 2) {
            throw new WrongAmountOfArgumentsException();
        }
        try {
            id = Long.parseLong(input[1]);
            if (mode.getScriptMode()) {
                p = collectionManager.readPersonFromScript(mode.getScanner(), id);
            } else {
                if (collectionManager.getById(id) != null) {
                    System.out.println("Currently stored element with this id:\n" + collectionManager.getById(id).toString());
                    System.out.println("Input the replacement:");
                    p = collectionManager.readPerson(id);
                } else p = null;
            }
        } catch (NumberFormatException e) {
            throw new WrongArgumentException();
        }
        return this;
    }
}
