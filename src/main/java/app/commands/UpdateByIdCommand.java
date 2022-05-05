package app.commands;

import app.data.Person;
import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;
import app.exceptions.WrongArgumentException;
import app.utils.Mode;
import app.utils.PersonReader;
import app.utils.ResponseManager;

/**
 * Command for updating the element of the collection with the specified id
 */
public class UpdateByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private Long id;
    private Person p;
    private final Mode mode;
    private final PersonReader personReader;
    private final ResponseManager responseManager = new ResponseManager();

    public UpdateByIdCommand(CollectionManager collectionManager, Mode mode, PersonReader personReader) {
        super("update id", "update the element with the specified id");
        this.collectionManager = collectionManager;
        this.mode = mode;
        this.personReader = personReader;
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
                p = personReader.readPersonFromScript(mode, id);
            } else {
                if (collectionManager.getById(id) != null) {
                    responseManager.showResponse("Currently stored element with this id:\n" + collectionManager.getById(id).toString(), !mode.getScriptMode());
                    responseManager.showResponse("Input the replacement: ", !mode.getScriptMode());
                    p = personReader.readPersonFromConsole(mode, id);
                } else p = null;
            }
        } catch (NumberFormatException e) {
            throw new WrongArgumentException();
        }
        return this;
    }
}
