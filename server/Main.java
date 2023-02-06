package server;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Database database = new Database();
        Message mess1 = new Message("hi");

        boolean flag = true;
        while(flag) {
            Scanner scanner = new Scanner(System.in);
            String methodToCall = scanner.next();
            int index;
            String textOfMessageToSet;
            if (Objects.equals(methodToCall, "exit")) {
                flag = false;
                return;
            } else {
                index = scanner.nextInt();
                textOfMessageToSet = scanner.nextLine();
                textOfMessageToSet = removeZero(textOfMessageToSet);
                mess1.setMessageText(textOfMessageToSet);
            }


            switch (methodToCall) {
                case "set":
                    database.setMessage(index,mess1.getMessageText());
                    break;
                case "get":
                    database.getMessage(index);
                    break;
                case "delete":
                    database.deleteMessege(index);
                    break;
                case "exit":
                    flag = false;
                    break;
            }
        }
    }
    public static String removeZero(String str)
    {
        // Initially setting loop counter to 0
        int i = 0;
        while (i < str.length() && str.charAt(i) == ' ')
            i++;

        // Converting string into StringBuffer object
        // as strings are immutable
        StringBuffer sb = new StringBuffer(str);

        // The StringBuffer replace function removes
        // i characters from given index (0 here)
        sb.replace(0, i, "");

        // Returning string after removing zeros
        return sb.toString();
    }
}
