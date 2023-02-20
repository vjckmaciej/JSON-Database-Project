package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 2235;
    public static void main(String[] argv) {
        CommandLineArguments argumentsForRequest = new CommandLineArguments();
        JCommander jCommander = new JCommander(argumentsForRequest);
        jCommander.setProgramName("JSON Database");
        try {
            JCommander.newBuilder()
                    .addObject(argumentsForRequest)
                    .build()
                    .parse(argv);
        } catch (ParameterException e) {
            e.printStackTrace();
        }
        new Client().run(SERVER_ADDRESS, PORT, argumentsForRequest.toJson());
    }
}

