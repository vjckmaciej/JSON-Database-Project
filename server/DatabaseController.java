package server;

import java.util.Map;

public class DatabaseController {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Map<String, String> executeCommand() {
        return command.execute();
    }
}
