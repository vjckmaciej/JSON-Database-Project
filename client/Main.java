package client;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class Main {
    @Parameter(names= "-t", description = "set/get/delete/exit")
    private String crudOperation;
    @Parameter(names= "-k", description = "key")
    private String key;

    @Parameter(names= "-v", description = "message")
    private String message;

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 1235;
    private static String outputParameters;
    public static void main(String ... argv) {
        Gson gson = new Gson();
        Map<String, String> parameters = new HashMap<>();
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        parameters.put("type", main.crudOperation);
        parameters.put("key", String.valueOf(main.key));
        parameters.put("value", main.message);
        outputParameters = gson.toJson(parameters);
        main.run();
    }

    public void run() {
        System.out.println("Client started!");

        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Sent: " + outputParameters);
            output.writeUTF(outputParameters);
            String receivedMsg = input.readUTF(); // read the reply from the server
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}

