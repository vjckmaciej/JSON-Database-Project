package server;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private JsonObject db;
    private String pathToDBFile = System.getProperty("user.dir") + "/src/server/data/db.json";
    private final ReentrantReadWriteLock RReadWriteLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock.ReadLock readLock = RReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = RReadWriteLock.writeLock();

    public Database() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(pathToDBFile)));
            db = new Gson().fromJson(content, JsonObject.class);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonObject getMessage(JsonElement key) {
        JsonObject returnValues = new JsonObject();
        try {
            readLock.lock();
            if ((key.isJsonPrimitive() || key.getAsJsonArray().size() == 1) && db.has(key.getAsString())){
                returnValues.addProperty("response", "OK");
                returnValues.add("value", db.get(key.getAsString()));
                return returnValues;
            }
            else if (key.isJsonArray()) {
                returnValues.addProperty("response", "OK");
                returnValues.add("value", findElement(key.getAsJsonArray()));
                return returnValues;
            }
            throw new RuntimeException("Key not found in database.");
        } finally {
            readLock.unlock();
        }
    }

    public JsonObject setMessage(JsonElement key, JsonElement value) {
        JsonObject returnValues = new JsonObject();
        JsonObject dbCurrentLvl = db;
        try {
            writeLock.lock();
            if (key.isJsonPrimitive()) {
                db.add(key.getAsString(), value);
                returnValues.addProperty("response", "OK");
                return returnValues;
            } else if (key.isJsonArray()){
                JsonArray keys = key.getAsJsonArray();
                String addKey = keys.remove(keys.size() - 1).getAsString();
                for (var currentKey : keys) {
                    if (!dbCurrentLvl.has(currentKey.getAsString()) ||
                            dbCurrentLvl.get(currentKey.getAsString()).isJsonPrimitive()) {
                        dbCurrentLvl.add(currentKey.getAsString(), new JsonObject());
                    }
                    dbCurrentLvl = (JsonObject) dbCurrentLvl.get(currentKey.getAsString());
                }
                dbCurrentLvl.add(addKey, value);
                returnValues.addProperty("response", "OK");
                return returnValues;
            }
            throw new RuntimeException("Key not found in database");
        } finally {
            writeJsonToFile();
            writeLock.unlock();
        }
    }

    public JsonObject deleteMessege(JsonElement key) {
        JsonObject returnValues = new JsonObject();
        try {
            writeLock.lock();
            if (key.isJsonPrimitive()  && db.has(key.getAsString())){
                db.remove(key.getAsString());
                returnValues.addProperty("response", "OK");
                return returnValues;
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toDelete = keys.remove(keys.size() - 1).getAsString();
                findElement(keys).getAsJsonObject().remove(toDelete);
                returnValues.addProperty("response", "OK");
                return returnValues;
            }
            throw new RuntimeException("Key not found in database");
        } finally {
            writeJsonToFile();
            writeLock.unlock();
        }
    }

    private void writeJsonToFile() {
        try (FileWriter writer = new FileWriter(pathToDBFile)) {
            writer.write(new GsonBuilder().create().toJson(db));
        } catch (Exception e) {
            System.out.printf("Write to db failed!\nMsg:%s%n", e.getMessage());
        }
    }

    private JsonElement findElement(JsonArray keys)  {
        JsonElement tmp = db;
        for(JsonElement key: keys){
            tmp = tmp.getAsJsonObject().get(key.getAsString());
        }
        return tmp;
    }
}


