package client;

import java.io.*;
import java.net.Socket;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {
    @Parameter(names= "-t", description = "set/get/delete/exit")
    private String crudOperation;
    @Parameter(names= "-i", description = "index")
    private int index;

    @Parameter(names= "-m", description = "message")
    private String message = "";

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 1235;
    public static void main(String ... argv) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.run();
    }

    public void run() {
        System.out.println("Client started!");

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            switch (crudOperation) {
                case "set":
                    output.writeUTF(crudOperation + " " + index + " " + message);
                    System.out.println("Sent: " + crudOperation + " " + index + " " + message);
                    break;
                case "get":
                    output.writeUTF(crudOperation + " " + index);
                    System.out.println("Sent: " + crudOperation + " " + index);
                    break;
                case "delete":
                    output.writeUTF(crudOperation + " " + index);
                    System.out.println("Sent: " + crudOperation + " " + index);
                    break;
                case "exit":
                    output.writeUTF(crudOperation);
                    System.out.println("Sent: " + crudOperation);
                    break;
            }
            String receivedMsg = input.readUTF(); // read the reply from the server
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}

