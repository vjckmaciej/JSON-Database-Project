package server.Commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.Command;
import server.Database;

public class DeleteMessageCommand implements Command {
    private Database database;
    private JsonElement key;

    public DeleteMessageCommand(Database database, JsonElement key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        return database.deleteMessege(this.key);
    }
}
