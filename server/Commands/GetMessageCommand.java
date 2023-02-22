package server.Commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.Command;
import server.Database;

public class GetMessageCommand implements Command {
    private Database database;
    private JsonElement key;

    public GetMessageCommand(Database database, JsonElement key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        return database.getMessage(this.key);
    }
}

