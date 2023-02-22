package server;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;

public class Request {
    public String fileName;
    public String type;
    public JsonElement key;
    public JsonElement value;

    public Request(String fileName, String type, JsonElement key, JsonElement value) {
        this.fileName = fileName;
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonElement getKey() {
        return key;
    }

    public void setKey(JsonElement key) {
        this.key = key;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }
}


