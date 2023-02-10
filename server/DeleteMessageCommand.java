package server;

public class DeleteMessageCommand implements Command {
    private Database database;
    private int index;

    public DeleteMessageCommand(Database database, int index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public String execute() {
        return database.deleteMessege(this.index);
    }
}
