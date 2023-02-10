package server;

public class SetMessageCommand implements Command {
    private Database database;
    private int index;
    private String message;

    public SetMessageCommand(Database database, int index, String message) {
        this.database = database;
        this.index = index;
        this.message = message;
    }

    @Override
    public String execute() {
        return database.setMessage(this.index, this.message);
    }
}
