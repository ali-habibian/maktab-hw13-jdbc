package ir.maktab.jdbc.utilities;

public class Printer {
    public static String formatter(Object str) {
        return String.format("%-5s", str + "");
    }

    public static String formatter(Object str, int spaces) {
        return String.format("%-" + spaces + "s", str + "");
    }

    public static void printErrorMessage(String msg) {
        System.out.println();
        System.out.println("| Error: " + msg + " |");
        printWaitingMessage();
    }

    public static void printMessage(String msg) {
        System.out.print(Color.CYAN);
        System.out.println(msg);
        System.out.print(Color.RESET);

//        printWaitingMessage();
    }

    public static void printWaitingMessage() {
        System.out.println("_____________ press Enter to continue...");
        Input.getOptionalStringInputValue("");
    }
}
