package console;

import console.enumerations.TextColorEnum;

import java.util.Scanner;

public class Console {

    public static Scanner scanner = new Scanner(System.in);

    public static void clearConsole() {
        final String os = System.getProperty("os.name").toLowerCase();
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ignored) {
        }
    }

    public static String setStringColor(String text, TextColorEnum color) {
        return color.getColorANSI() + text + TextColorEnum.ANSI_RESET.getColorANSI();
    }

}
