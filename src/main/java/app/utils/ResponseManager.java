package app.utils;

import app.exceptions.ScriptErrorException;

/**
 * Class for sending and showing responses from commands
 */
public class ResponseManager {
    public ResponseManager() {}

    /**
     * Shows message
     * @param prompt The prompt
     */
    public void showMessage(String prompt) {
        if (prompt != null) {
            System.out.print(prompt);
        }
    }

    /**
     * Shows message if the program is currently running in interactive mode
     * @param prompt The message
     * @param interactiveMode True if program is running in interactive mode
     */
    public void showMessage(String prompt, boolean interactiveMode) {
        if (interactiveMode) {
            showMessage(prompt);
        }
    }

    /**
     * Shows response. The only difference from showMessage is EOL symbol at the end
     * @param message The response
     */
    public void showResponse(String message) {
        if (message != null)
            showMessage(message +"\n");
    }

    /**
     * Shows response if the program is currently running in interactive mode
     * @param message The response
     * @param interactiveMode True if program is running in interactive mode
     */
    public void showResponse(String message, boolean interactiveMode) {
        if (interactiveMode)
            showResponse(message);
    }

    /**
     * Returns an error message if the program is running in interactive mode,
     * throws a ScriptErrorException if the program is running in script mode and throwException is set to true
     * @param message Message
     * @param interactiveMode True if program is running in interactive mode
     * @param throwException True if the exception needs to be thrown in script mode, false otherwise
     * @return The resulting message (null if in script mode and ScriptErrorException hasn't been thrown)
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
