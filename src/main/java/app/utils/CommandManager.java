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
    private final Stack<String> scriptHistory = new Stack<>();
    private final Mode mode;
    private final String filepath;
    private final JsonParser parser;
    private final Map<String, Command> commandMap = new HashMap<>();

    /**
     * Constructor, initializes the collection stored in the file specified by filepath
     * @param filepath String Filepath
     */
    public CommandManager(String filepath) {
        mode = new Mode();
        parser = new JsonParser();
        this.filepath = filepath;
        this.collectionManager = parser.fileToCollection(filepath);
        initializeCommandMap();
    }

    public Scanner getScanner() {
        return mode.getScanner();
    }

    public boolean getScriptMode() {
        return mode.getScriptMode();
    }

    /**
     * Method for initializing all commands
     */
    public void initializeCommandMap() {
        commandMap.put("help", new HelpCommand(commandMap));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("add", new AddCommand(collectionManager, mode));
        commandMap.put("update", new UpdateByIdCommand(collectionManager, mode));
        commandMap.put("remove", new RemoveByIdCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("save", new SaveCommand(collectionManager, parser, filepath));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("execute", new ExecuteScriptCommand(this));
        commandMap.put("add_if_max", new AddIfMaxCommand(collectionManager, mode));
        commandMap.put("add_if_min", new AddIfMinCommand(collectionManager, mode));
        commandMap.put("shuffle", new ShuffleCommand(collectionManager));
        commandMap.put("count_by_birthday", new CountByBirthdayCommand(collectionManager));
        commandMap.put("print_birthdays", new PrintBirthdaysCommand(collectionManager));
        commandMap.put("group", new GroupByHeightCommand(collectionManager));
    }

    /**
     * Method for executing a command
     * @param s String Command and arguments
     * @return String The result
     * @throws ScriptErrorException Thrown when the mistake in the script is encountered
     */
    public String execute(String[] s) throws ScriptErrorException {
        try {
            Command c = getCommand(s).setArgs(s);
            return c.execute(getScriptMode());
        } catch (NoScriptException e) {
            if (!getScriptMode()) return "This script doesn't exist or can't be accessed.";
            else throw new ScriptErrorException();
        } catch (NoCommandException e) {
            if (!getScriptMode()) return "No such command, try again";
            return null;
        } catch (WrongCommandException e) {
            if (getScriptMode()) {
                throw new ScriptErrorException();
            }
            if (!getScriptMode()) return "No such command, try again.";
        } catch (WrongAmountOfArgumentsException | WrongArgumentException e) {
            if (getScriptMode()) {
                throw new ScriptErrorException();
            }
            if (!getScriptMode()) return "Wrong arguments, try again.";
        }
        return null;
    }

    /**
     * Method for matching an input line with the corresponding command
     * @param s String[] Input line, split by whitespace characters
     * @return Command The corresponding commands
     * @throws NoCommandException When the input is empty
     * @throws WrongCommandException When the input is not empty and doesn't correspond to any command
     */
    public Command getCommand(String[] s) throws NoCommandException, WrongCommandException {
        if (s.length == 0) {
            throw new NoCommandException();
        }
        String commandName = s[0].trim().toLowerCase();
        if (s[0].trim().isEmpty()) throw new NoCommandException();
        if (commandMap.containsKey(commandName)) {
            return commandMap.get(commandName);
        } else throw new WrongCommandException();
    }

    /**
     * Method for running the process of command executing
     */
    public void run() {
        while (true) {
            if (!getScriptMode()) {
                System.out.print("$ ");
            }
            if (getScanner().hasNextLine()) {
                String[] command = getScanner().nextLine().trim().split("[\\s]+");
                String res = execute(command);
                if (res != null) System.out.println(res);
            } else {
                if (!getScriptMode()) {
                    System.out.println("\nYou have entered the end of file symbol. The collection will be saved and the application will be closed.");
                    collectionManager.save(parser, filepath);
                }
                return;
            }
        }
    }

    /**
     * Method for adding a script to the script history and setting the mode of the command manager for its execution
     * @param file File The script file
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
     * Method for removing the script from the script history and adjusting the mode of the command manager for further work
     * @param prevScanner Scanner Scanner that was used before the execution of the script
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
     * Method for getting the last (most recent) element of the script history
     * @return String The canonical path of the last script in the script history
     */
    public String peek() {
        return scriptHistory.peek();
    }

    /**
     * Method for clearing the script history
     * @return String The first (the oldest) element of the script history
     */
    public String clearScriptHistory() {
        String filename = null;
        while (!scriptHistory.empty()) {
            filename = scriptHistory.pop();
        }
        mode.setScanner(new Scanner(System.in));
        mode.setScriptMode(false);
        return filename;
    }
}
