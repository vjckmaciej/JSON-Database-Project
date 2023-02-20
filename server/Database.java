package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private Map<String, String> database = new HashMap<>();
    private String pathToDBFile = System.getProperty("user.dir") + "/src/server/data/db.json";
    private final ReentrantReadWriteLock RReadWriteLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock.ReadLock readLock = RReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = RReadWriteLock.writeLock();


    public Map<String, String> getMessage(String key)  {
        readLock.lock();
        Map<String, String> returnValues = new HashMap<>();
        try {
            if (database.containsKey(key) && !database.get(key).isEmpty()) {
                returnValues.put("response", "OK");
                returnValues.put("value", database.get(key));
            } else {
                returnValues.put("response", "ERROR");
                returnValues.put("reason", "No such key");
            }
        } finally {
            readLock.unlock();
        }
        return returnValues;
    }

    public Map<String, String> setMessage(String key, String message) {
        writeLock.lock();
        Map<String, String> returnValues = new HashMap<>();
        try {
            database.put(key, message);
            returnValues.put("response", "OK");
            writeJsonToFile(database);
        } finally {
            writeLock.unlock();
        }
        return returnValues;
    }

    public Map<String, String> deleteMessege(String key) {
        Map<String, String> returnValues = new HashMap<>();
        try {
            if (database.containsKey(key)) {
                database.remove(key);
                returnValues.put("response", "OK");
            } else {
                returnValues.put("response", "ERROR");
                returnValues.put("reason", "No such key");
            }
            writeLock.lock();
            writeJsonToFile(database);
        } finally {
            writeLock.unlock();
        }
        return returnValues;
    }

    public void writeJsonToFile(Map<String, String> map) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(pathToDBFile)) {
            gson.toJson(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


