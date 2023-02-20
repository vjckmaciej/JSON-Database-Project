package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors() * 3;
    private final int PORT;
    private final Database database;
    private final ServerSocket serverSocket;
    private final ExecutorService executor;

    public Server(int PORT, Database database) throws IOException {
        this.PORT = PORT;
        this.database = database;
        this.serverSocket = new ServerSocket(PORT);
        this.executor =  Executors.newFixedThreadPool(POOL_SIZE);
    }

    public void run() {
        System.out.println("Server started!");
        try {
            while (true) {
                executor.submit(new Session(serverSocket.accept(), database));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
