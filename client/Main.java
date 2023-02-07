package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 2345;
    public static void main(String[] args) {
        System.out.println("Client started!");
        try (
                Socket socket = new Socket(SERVER_ADDRESS, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            String msg = "12";

            output.writeUTF(msg); // send a message to the server
            System.out.println("Sent: Give me a record # " + msg);

            String receivedMsg = input.readUTF(); // read the reply from the server
            System.out.println("Received: A record # " + receivedMsg + " was sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
