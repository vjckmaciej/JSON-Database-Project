package server;

public class GetMessageCommand implements Command {
    private Database database;
    private int index;

    public GetMessageCommand(Database database, int index) {
        this.database = database;
        this.index = index;
    }

    @Override
    public String execute() {
        return database.getMessage(this.index);
    }
}

