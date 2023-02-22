package server;

import com.google.gson.Gson;
import server.Commands.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Session implements Runnable {
    private final Socket socket;
    private final Database database;
    DatabaseController databaseController = new DatabaseController();

    public Session(Socket socket, Database database) {
        this.socket = socket;
        this.database = database;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream()))
        {
            String requestJSON = input.readUTF();
            System.out.println("Received: " + requestJSON);
            Gson gson = new Gson();
            Request messageCommand = gson.fromJson(requestJSON, Request.class);
            switch (messageCommand.getType()) {
                case "set":
                    databaseController.setCommand(new SetMessageCommand(database, messageCommand.getKey(), messageCommand.getValue()));
                    break;
                case "get":
                    databaseController.setCommand(new GetMessageCommand(database, messageCommand.getKey()));
                    break;
                case "delete":
                    databaseController.setCommand(new DeleteMessageCommand(database, messageCommand.getKey()));
                    break;
                case "exit":
                    Map<String,String> outputResponse = new HashMap<>();
                    outputResponse.put("response", "OK");
                    output.writeUTF(outputResponse.toString());
                    Main.server.stop();
                    break;
            }
            String outputParameters = gson.toJson(databaseController.executeCommand());
            output.writeUTF(outputParameters);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
