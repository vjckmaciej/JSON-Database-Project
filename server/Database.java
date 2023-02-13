package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Map<String, String> database = new HashMap<>();

    public Map<String, String> getMessage(String key) {
        Map<String, String> returnValues = new HashMap<>();
        if (database.containsKey(key) && !database.get(key).isEmpty()) {
                returnValues.put("response", "OK");
                returnValues.put("value", database.get(key));
        } else {
                returnValues.put("response", "ERROR");
                returnValues.put("reason", "No such key");
        }
        return returnValues;
    }

    public Map<String, String> setMessage(String key, String message) {
        Map<String, String> returnValues = new HashMap<>();
        database.put(key, message);
        returnValues.put("response", "OK");
        return returnValues;
    }

    public Map<String, String> deleteMessege(String key) {
        Map<String, String> returnValues = new HashMap<>();
        if (database.containsKey(key)) {
            database.remove(key);
            returnValues.put("response", "OK");
        } else {
            returnValues.put("response", "ERROR");
            returnValues.put("reason", "No such key");
        }
        return returnValues;
    }
}
