package app.commands;

import app.data.Person;
import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.Mode;
import app.utils.CollectionManager;
import app.utils.PersonReader;

/**
 * Command for adding elements to the collection
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final Mode mode;
    private Person person;
    private final PersonReader personReader;

    /**
     * Constructor
     * @param collectionManager CollectionManager the collection manager
     * @param mode Mode mode of the command manager
     */
    public AddCommand(CollectionManager collectionManager, Mode mode, PersonReader personReader) {
        super("add {element}", "add new element to the collection");
        this.collectionManager = collectionManager;
        this.mode = mode;
        this.personReader = personReader;
    }

    @Override
    public String execute(boolean scriptMode) {
        String res = collectionManager.add(person);
        if (!scriptMode) return res;
        return null;
    }

    @Override
    public Command setArgs(String[] input) {
        if (input.length != 1) {
            throw new WrongAmountOfArgumentsException();
        }
        person = personReader.readPerson(mode, collectionManager.generateNextId());
        return this;
    }
}
