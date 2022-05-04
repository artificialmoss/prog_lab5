package app.commands;

import app.exceptions.ScriptErrorException;
import app.utils.CommandManager;
import app.exceptions.NoScriptException;
import app.exceptions.RecursiveScriptException;
import app.exceptions.WrongAmountOfArgumentsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Command for executing scripts
 */
public class ExecuteScriptCommand extends Command {
    private final CommandManager commandManager;
    private File file;

    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute filepath", "execute script from the specified file");
        this.commandManager = commandManager;
    }

    @Override
    public String execute(boolean scriptMode) {
        Scanner curScanner = commandManager.getScanner();
        try {
            commandManager.addScript(file);
            commandManager.run();
            file = new File(commandManager.peekLast());
            commandManager.removeScript(curScanner);
        } catch (RecursiveScriptException e) {
            file = new File(commandManager.peekFirst());
            commandManager.clearScriptHistory();
            return file.getAbsolutePath() + " contains recursion and can't be executed. All commands before the recursive call have been executed.";
        } catch (FileNotFoundException e) {
            if (scriptMode) throw new ScriptErrorException();
            return "File not found.";
        } catch (ScriptErrorException e) {
            file = new File(commandManager.peekLast());
            commandManager.removeScript(curScanner);
            if (scriptMode) throw new ScriptErrorException();
            return file.getAbsolutePath() + " contains a mistake. All commands before the mistake have been executed.";
        }
        if (!scriptMode) return file.getAbsolutePath() + " has been successfully executed.";
        return null;
    }

    @Override
    public Command setArgs(String[] input) throws NoScriptException {
        if (input.length != 2) {
            throw new WrongAmountOfArgumentsException();
        }
        String filename = input[1];

        Path path = Paths.get(filename);
        if (!Files.exists(path) | Files.isDirectory(path) | !Files.isReadable(path)) {
            throw new NoScriptException("This script doesn't exist or can't be accessed.");
        }
        file = new File(filename);
        return this;
    }
}
