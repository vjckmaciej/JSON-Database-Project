package server;

import java.io.*;

public class Main {
    private static int PORT = 2235;
    public static Server server;

    public static void main(String[] args) throws IOException {
        Database database = new Database();
        server = new Server(PORT, database);
        server.run();
    }
}


