package app.utils;

import app.commands.*;
import app.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Class that manages all commands for the collection
 */
public class CommandManager {
    private final CollectionManager collectionManager;
    private final Deque<String> scriptHistory = new ArrayDeque<>();
    private final Mode mode;
    private final JsonParser parser;
    private final Map<String, Command> commandMap = new HashMap<>();
    private final ResponseManager responseManager = new ResponseManager();
    private final PersonReader personReader = new PersonReader(3);

    /**
     * Constructor, initializes the collection stored in the file specified by filepath
     * @param filepath Filepath
     */
    public CommandManager(String filepath) {
        mode = new Mode();
        parser = new JsonParser(filepath, "saved_collection_default.json");
        this.collectionManager = parser.fileToCollection();
        initializeCommandMap();
    }

    public Scanner getScanner() {
        return mode.getScanner();
    }

    public boolean getScriptMode() {
        return mode.getScriptMode();
    }

    /**
     * Initializes map with all available commands
     */
    public void initializeCommandMap() {
        commandMap.put("help", new HelpCommand(commandMap));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("add", new AddCommand(collectionManager, mode, personReader));
        commandMap.put("update", new UpdateByIdCommand(collectionManager, mode, personReader));
        commandMap.put("remove", new RemoveByIdCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("save", new SaveCommand(collectionManager, parser));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("execute", new ExecuteScriptCommand(this));
        commandMap.put("add_if_max", new AddIfMaxCommand(collectionManager, mode, personReader));
        commandMap.put("add_if_min", new AddIfMinCommand(collectionManager, mode, personReader));
        commandMap.put("shuffle", new ShuffleCommand(collectionManager));
        commandMap.put("count_by_birthday", new CountByBirthdayCommand(collectionManager));
        commandMap.put("print_birthdays", new PrintBirthdaysCommand(collectionManager));
        commandMap.put("group", new GroupByHeightCommand(collectionManager));
    }

    /**
     * Executes the command
     * @param s Command and arguments
     * @return The result
     * @throws ScriptErrorException Thrown when the mistake in the script is encountered
     */
    public String execute(String[] s) throws ScriptErrorException {
        try {
            Command c = getCommand(s).setArgs(s);
            return c.execute(getScriptMode());
        } catch (NoScriptException e) {
            return responseManager.sendErrorMessage("This script doesn't exist or can't be accessed.", !getScriptMode(), true);
        } catch (NoCommandException e) {
            return responseManager.sendErrorMessage("No command, try again", !getScriptMode(), false);
        } catch (WrongCommandException e) {
            return responseManager.sendErrorMessage("No such command, try again.", !getScriptMode(), true);
        } catch (WrongAmountOfArgumentsException | WrongArgumentException e) {
            return responseManager.sendErrorMessage("Wrong arguments, try again.", !getScriptMode(), true);
        }
    }

    /**
     * Matches the input line with the corresponding command
     * @param s Input line, split by whitespace characters
     * @return The corresponding commands
     * @throws NoCommandException Thrown when the input is empty
     * @throws WrongCommandException Thrown when the input is not empty and doesn't correspond to any command
     */
    public Command getCommand(String[] s) throws NoCommandException, WrongCommandException {
        if (s.length == 0) {
            throw new NoCommandException();
        }
        String commandName = s[0].trim().toLowerCase();
        if (commandName.isEmpty()) throw new NoCommandException();
        if (commandMap.containsKey(commandName)) {
            return commandMap.get(commandName);
        } else throw new WrongCommandException();
    }

    /**
     * Runs the process of command execution
     */
    public void run() {
        while (true) {
            responseManager.showMessage("$ ", !getScriptMode());
            if (getScanner().hasNextLine()) {
                String[] command = getScanner().nextLine().trim().split("\\s+");
                responseManager.showResponse(execute(command));
            } else {
                if (!getScriptMode()) {
                    responseManager.showResponse("\nYou have entered the end of file symbol. The collection will be saved and the application will be closed.");
                    collectionManager.save(parser);
                }
                return;
            }
        }
    }

    /**
     * Adds a script to the script history, sets the mode of the command manager for its execution
     * @param file The script file
     * @throws FileNotFoundException Thrown when the process of reading the script encounters an unexpected input error
     */
    public void addScript(File file) throws FileNotFoundException {
        try {
            String path = file.getCanonicalPath();
            if (scriptHistory.contains(path)) {
                throw new RecursiveScriptException("You have tried to execute the script recursively.");
            } else {
                mode.setScriptMode(true);
                mode.setScanner(new Scanner(file));
                scriptHistory.push(path);
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Removes the script from the script history, adjusts the mode of the command manager for further work
     * @param prevScanner Scanner that was used before the execution of the script
     */
    public void removeScript(Scanner prevScanner) {
        scriptHistory.pop();
        if (scriptHistory.isEmpty()) {
            mode.setScriptMode(false);
        }
        if (prevScanner != null) {
            mode.setScanner(prevScanner);
        } else {
            throw new NullPointerException("Unknown error: no scanner.");
        }
    }

    /**
     * Returns the last (most recent) element of the script history
     * @return The canonical path of the last script in the script history
     */
    public String peekLast() {
        return scriptHistory.peekLast();
    }

    /**
     * Returns the first (the oldest) element of the script history
     * @return The canonical path of the first script in the script history
     */
    public String peekFirst() {
        return scriptHistory.peekFirst();
    }

    /**
     * Clears the script history
     */
    public void clearScriptHistory() {
        scriptHistory.clear();
        mode.setScanner(new Scanner(System.in));
        mode.setScriptMode(false);
    }
}
