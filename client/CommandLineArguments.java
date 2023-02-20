package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandLineArguments {
    @Parameter(names= "-t", description = "set/get/delete/exit")
    public String crudOperation;

    @Parameter(names= "-k", description = "key")
    public String key;

    @Parameter(names= "-v", description = "value")
    public String value;

    @Parameter(names= "-in", description = "name of file with containing request")
    public String fileName;

    public String getCrudOperation() {
        return crudOperation;
    }

    public void setCrudOperation(String crudOperation) {
        this.crudOperation = crudOperation;
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

    private String readFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        map.put("type", this.crudOperation);
        map.put("key", this.key);
        map.put("value", this.value);
        return new Gson().toJson(map);
    }

    @Override
    public String toString() {
        if (fileName != null) {
            try (Reader reader = Files.newBufferedReader(Path.of("src/client/data/" + fileName))) {
                return JsonParser.parseReader(reader).getAsJsonObject().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Gson().toJson(this);
    }
}
