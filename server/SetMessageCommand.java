package server;

import java.util.Map;

public class SetMessageCommand implements Command {
    private Database database;
    private String key;
    private String message;

    public SetMessageCommand(Database database, String key, String message) {
        this.database = database;
        this.key = key;
        this.message = message;
    }

    @Override
    public Map<String, String> execute() {
        return database.setMessage(this.key, this.message);
    }
}
