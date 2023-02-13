package server;

import java.util.Map;

public class GetMessageCommand implements Command {
    private Database database;
    private String key;

    public GetMessageCommand(Database database, String key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public Map<String, String> execute() {
        return database.getMessage(this.key);
    }
}

