package server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final int CAPACITY = 1000;
    private final List<String> database = new ArrayList<>(CAPACITY);

    public Database() {
        for (int i = 0; i < CAPACITY; i++) {
            database.add("");
        }
    }

    public String getMessage(int index) {
        try {
            if ("".equals(database.get(index-1))) {
              return "ERROR";
            } else {
                return database.get(index-1);
            }

        } catch (IndexOutOfBoundsException e) {
            return "blad tutaj";
        }
    }

    public String setMessage(int index, String message) {
        try {
            database.set(index-1, message);
            return "OK";

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public String deleteMessege(int index) {
        try {
            database.set(index-1,"");
            return "OK";
        } catch (IndexOutOfBoundsException e) {
            return "ERROR";
        }
    }
}
