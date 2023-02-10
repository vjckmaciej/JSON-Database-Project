package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

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
                //session.join();
//                server.close();
//                System.exit(0);
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
//    Database database = new Database();

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
                String[] arrayOfClientParameters = msg.split(" "); //parameters given in the command line when running client
                String methodToCall = arrayOfClientParameters[0]; //crud operation on database
                int index = 0; //index of cell we want to reach
                if (arrayOfClientParameters.length >= 2) {
                    index = Integer.parseInt(arrayOfClientParameters[1]);
                }
                Command commandFromRequest;
                switch (methodToCall) {
                    case "set":
                        StringBuilder sb = new StringBuilder();
                        for (int i = 2; i < arrayOfClientParameters.length; i++) {
                            sb.append(arrayOfClientParameters[i] + " ");
                        }
                        commandFromRequest = new SetMessageCommand(database, index, sb.toString());
                        output.writeUTF(commandFromRequest.execute()+ " " + database.getDatabase());
                        break;
                    case "get":
                        commandFromRequest = new GetMessageCommand(database, Integer.parseInt(arrayOfClientParameters[1]));
                        output.writeUTF(commandFromRequest.execute()+ " " + database.getDatabase());
                        break;
                    case "delete":
                        commandFromRequest = new DeleteMessageCommand(database, Integer.parseInt(arrayOfClientParameters[1]));
                        output.writeUTF(commandFromRequest.execute()+ " " + database.getDatabase());
                        break;
                    case "exit":
                        output.writeUTF("OK");
                        flagExit = true;
                        socket.close();
                        break;
                }
                //output.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        } while (!flagExit);
    }
}


