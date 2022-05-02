package app.commands;

import app.collection.Person;
import app.exceptions.WrongAmountOfArgumentsException;
import app.utils.Mode;
import app.utils.CollectionManager;

import java.util.Scanner;

public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final Mode mode;
    private Person person;

    public AddCommand(CollectionManager collectionManager, Mode mode) {
        super("add {element}", "add new element to the collection");
        this.collectionManager = collectionManager;
        this.mode = mode;
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
