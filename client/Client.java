package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
public class Client {
    public void run(String host, int port, String dataToSend) {
        try (Socket socket = new Socket(InetAddress.getByName(host), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream()))
        {
            System.out.println("Client started!");
            output.writeUTF(dataToSend);
            System.out.println("Sent: " + dataToSend);
            System.out.println("Received: " + input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
