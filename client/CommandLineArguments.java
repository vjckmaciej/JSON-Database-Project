package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandLineArguments {
    @Parameter(names= "-in", description = "name of file with containing request")
    public String fileName;
    @Parameter(names= "-t", description = "set/get/delete/exit")
    public String type;

    @Parameter(names= "-k", description = "key")
    public String key;

    @Parameter(names= "-v", description = "value")
    public String value;

    public String getType() {
        return type;
    }

    public void setType(String crudOperation) {
        this.type = crudOperation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    private String readFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public String toJson() {
        if (this.fileName != null) {
            try {
                return readFromFile(System.getProperty("user.dir") + "/src/client/data/" + this.fileName);
            } catch (IOException e) {
                System.out.println("Cannot read file: " + e.getMessage());
                System.exit(1);
            }
        }

        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", this.type);
        map.put("key", String.valueOf(this.key));
        map.put("value", String.valueOf(this.value));
        return new Gson().toJson(map);
    }
}
