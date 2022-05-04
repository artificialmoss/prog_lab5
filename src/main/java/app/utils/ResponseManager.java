package app.utils;

import app.exceptions.ScriptErrorException;

/**
 * Class for sending and showing responses from commands
 */
public class ResponseManager {
    public ResponseManager() {}

    /**
     * Shows a message
     * @param message
     */
    public void showMessage(String message) {
        if (message != null)
            System.out.println(message);
    }

    /**
     * Shows message if the program is currently running in interactive mode
     * @param message String message
     * @param interactiveMode boolean True if program is running in interactive mode
     */
    public void showMessage(String message, boolean interactiveMode) {
        if (interactiveMode)
            showMessage(message);
    }

    /**
     * Shows prompt. The only difference from the showMessage is the lack of EOL symbol
     * @param prompt String The prompt
     */
    public void showPrompt(String prompt) {
        if (prompt != null) {
            System.out.print(prompt);
        }
    }

    /**
     * Shows prompt if the program is currently running in interactive mode
     * @param prompt String The prompt
     * @param interactiveMode boolean True if program is running in interactive mode
     */
    public void showPrompt(String prompt, boolean interactiveMode) {
        if (interactiveMode) {
            showPrompt(prompt);
        }
    }

    /**
     * Returns an error message if the program is running in interactive mode,
     * throws a ScriptErrorException if the program is running in script mode and throwException is set to true
     * @param message String Message
     * @param interactiveMode boolean True if program is running in interactive mode
     * @param throwException boolean True if the exception needs to be thrown in script mode, false otherwise
     * @return String The resulting message (null if in script mode and ScriptErrorException hasn't been thrown)
     * @throws ScriptErrorException Thrown when interactiveMode = false and throwException = true
     */
    public String sendErrorMessage(String message, boolean interactiveMode, boolean throwException) throws ScriptErrorException {
        if (interactiveMode) {
            return message;
        }
        if (throwException) {
            throw new ScriptErrorException();
        }
        return null;
    }
}
