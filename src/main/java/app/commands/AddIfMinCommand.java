package app.commands;

import app.data.Person;
import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.Mode;
import app.utils.PersonReader;

/**
 * Command for adding the element to the collection if it is smaller than the current minimal element of the collection, according to the elemen't natural order
 */
public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;
    private Person person;
    private final Mode mode;
    private final PersonReader personReader;

    public AddIfMinCommand(CollectionManager collectionManager, Mode mode, PersonReader personReader) {
        super("add_if_min {element}", "add element to the collection if it's smaller than the minimal element of the collection");
        this.collectionManager = collectionManager;
        this.mode = mode;
        this.personReader = personReader;
    }

    @Override
    public String execute(boolean scriptMode) {
        if (person.compareTo(collectionManager.getMin()) < 0) {
            String res = collectionManager.add(person);
            if (!scriptMode) return res;
        }
        if (!scriptMode) return "Element not added: this element is not smaller than min.";
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