package app;

import app.utils.CommandManager;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Main class, starts the application.
 * @author Ryzhova Evgenia
 */
public class Main {
    public static void main(String[] args) {
        SignalHandler ignoreHandler = sig -> System.out.print("");
        Signal.handle(new Signal("INT"), ignoreHandler);

        String filepath = System.getenv("LAB5_PATH");
        //String filepath = "src\\main\\resources\\collection.json";
        CommandManager commandManager = new CommandManager(filepath);
        System.out.println(commandManager.execute(new String[]{"help"}));
        commandManager.run();
    }
}
