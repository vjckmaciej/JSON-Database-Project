package server;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final int PORT = 1235;

    public static void main(String[] args) {
        System.out.println("Server started!");
        Database database = new Database();

        boolean exitProgram = false;
        try(ServerSocket server = new ServerSocket(PORT)) {
            while (!exitProgram) {
                Session session = new Session(server.accept(),database);
                session.run();
                exitProgram = session.isFlagExit();//it does not create new thread but run code from session object within current thread
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

class Session extends Thread {
    private Socket socket;
    private Database database;
    DatabaseController databaseController = new DatabaseController();
    private boolean flagExit = false;

    public boolean isFlagExit() {
        return flagExit;
    }

    public Session(Socket socketForClient, Database database) {
        this.database = database;
        this.socket = socketForClient;
    }

    public void run() {
//        do {
            try (
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                String msg = input.readUTF();
                Gson gson = new Gson();
                MessageCommand messageCommand = gson.fromJson(msg, MessageCommand.class);
                Map<String,String> outputResponse = new HashMap<>();

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
                        outputResponse.put("response", "OK");
                        flagExit = true;
                        socket.close();
                        break;
                }
                String outputParam = gson.toJson(databaseController.executeCommand());
                output.writeUTF(outputParam);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}


