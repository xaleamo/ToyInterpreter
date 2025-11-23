package view.command;

public abstract class Command {
    protected String key,description;
    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }
    public String getKey() {return key;}
    public String getDescription() {return description;}

    public abstract void execute();
}
