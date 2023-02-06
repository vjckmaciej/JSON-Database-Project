package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Database {
    private final int CAPACITY = 100;
    private final List<String> database = new ArrayList<>(CAPACITY);

    public Database() {
        for (int i = 0; i < CAPACITY; i++) {
            database.add("");
        }
    }

    public List<String> getDatabase() {
        return database;
    }

    public void getMessage(int index) {
        try {
            if (!database.get(index-1).isEmpty()) {
                System.out.println(database.get(index-1));
            } else {
                System.out.println("ERROR");
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR");
        }
    }

    public void setMessage(int index, String message) {
        try {
            database.set(index-1, message);
            System.out.println("OK");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR");
        }
    }

    public void deleteMessege(int index) {
        try {
            database.set(index-1,"");
            System.out.println("OK");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR");
        }
    }

    public void printDatabaseMessages() {
        for (String s : database) {
            System.out.print(s);
        }
    }
}
