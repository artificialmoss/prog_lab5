package app.commands;

import app.collection.Person;
import app.utils.CollectionManager;
import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.Mode;

import java.util.Scanner;

public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;
    private Person person;
    private final Mode mode;

    public AddIfMinCommand(CollectionManager collectionManager, Mode mode) {
        super("add_if_min {element}", "add element to the collection if it's smaller than the minimal element of the collection");
        this.collectionManager = collectionManager;
        this.mode = mode;
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
        boolean scriptMode = mode.getScriptMode();
        if (scriptMode) {
            Scanner s = mode.getScanner();
            person = collectionManager.readPersonFromScript(s);
        } else {
            person = collectionManager.readPerson();
        }
        return this;
    }
}