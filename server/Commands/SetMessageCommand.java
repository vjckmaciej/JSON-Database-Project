package server.Commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.Command;
import server.Database;

public class SetMessageCommand implements Command {
    private Database database;
    private JsonElement key;
    private JsonElement message;

    public SetMessageCommand(Database database, JsonElement key, JsonElement message) {
        this.database = database;
        this.key = key;
        this.message = message;
    }

    @Override
    public JsonObject execute() {
        return database.setMessage(this.key, this.message);
    }
}
