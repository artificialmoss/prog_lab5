package app.commands;

import app.data.Person;
import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.Mode;
import app.utils.CollectionManager;
import app.utils.PersonReader;

/**
 * Command for adding the element to the collection if it is larger than the current maximal element of the collection, according to the elemen't natural order
 */
public class AddIfMaxCommand extends Command {
    private final CollectionManager collectionManager;
    private Person person;
    private final Mode mode;
    private final PersonReader personReader;

    public AddIfMaxCommand(CollectionManager collectionManager, Mode mode, PersonReader personReader) {
        super("add_if_max {element}", "add element to the collection if it's larger than the maximal element of the collection");
        this.collectionManager = collectionManager;
        this.mode = mode;
        this.personReader = personReader;
    }

    @Override
    public String execute(boolean scriptMode) {
        if (person.compareTo(collectionManager.getMax()) > 0) {
            String res =  collectionManager.add(person);
            if (!scriptMode) return res;
        }
        if (!scriptMode) return "Element not added: this element is not larger than max.";
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
