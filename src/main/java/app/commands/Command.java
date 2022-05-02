package app.commands;

public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract String execute(boolean scriptMode);

    public String describe() {
        return name + " — " + description;
    }

    public abstract Command setArgs(String[] input);
}
