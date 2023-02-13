package server;

import java.util.Map;

public class DeleteMessageCommand implements Command {
    private Database database;
    private String key;

    public DeleteMessageCommand(Database database, String key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public Map<String, String> execute() {
        return database.deleteMessege(this.key);
    }
}
