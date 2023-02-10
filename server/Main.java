package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                exitProgram = session.isFlagExit();//it doesnt create new thread but run code from session object within current thread
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

    public Session(Socket socketForClient, Database database) {
        this.database = database;
        this.socket = socketForClient;
    }

    public void run() {
            try (
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                String msg = input.readUTF();
                String[] arrayOfClientParameters = msg.split(" "); //parameters given in the command line when running client
                String methodToCall = arrayOfClientParameters[0]; //crud operation on database
                int index = 0; //index of cell we want to reach
                if (arrayOfClientParameters.length >= 2) {
                    index = Integer.parseInt(arrayOfClientParameters[1]);
                }
                switch (methodToCall) {
                    case "set":
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < arrayOfClientParameters.length; i++) {
                            sb.append(arrayOfClientParameters[i] + " ");
                        }
                        databaseController.setCommand(new SetMessageCommand(database, index, sb.toString()));
                        break;
                    case "get":
                        databaseController.setCommand(new GetMessageCommand(database, index));
                        break;
                    case "delete":
                        databaseController.setCommand(new DeleteMessageCommand(database, index));
                        break;
                    case "exit":
                        output.writeUTF("OK");
                        flagExit = true;
                        socket.close();
                        break;
                }
                output.writeUTF(databaseController.executeCommand());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public boolean isFlagExit() {
        return flagExit;
    }
}