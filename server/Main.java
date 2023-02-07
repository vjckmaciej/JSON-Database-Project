package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 2345;

    public static void main(String[] args) {
        System.out.println("Server started!");
        try(ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Session session = new Session(server.accept());
                session.run(); //it doesnt create new thread but run code from session object within current thread
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Session extends Thread {
    private Socket socket;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String msg = input.readUTF();
            System.out.println("Received: Give me a record # " + msg);

            output.writeUTF(msg);
            System.out.println("Sent: A record # " + msg + " was sent!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
