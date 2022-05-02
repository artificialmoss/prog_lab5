package app.utils;

import java.util.Scanner;

/**
 * Class for storing the current mode of the command manager.
 * Contains the mode (interactive/script) and the Scanner that is currently used by the command manager for reading the commands.
 */
public class Mode {
    private boolean scriptMode;
    private Scanner s;

    public Mode() {
        s = new Scanner(System.in);
        scriptMode = false;
    }

    public boolean getScriptMode() {
        return scriptMode;
    }

    public void setScriptMode(boolean scriptMode) {
        this.scriptMode = scriptMode;
    }

    public Scanner getScanner() {
        return s;
    }

    public void setScanner(Scanner s) {
        this.s = s;
    }
}
