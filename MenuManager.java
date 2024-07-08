package console.menu;

import console.Console;

import java.util.List;
import java.util.Scanner;

public class MenuManager {

    public static Scanner scanner = Console.scanner;
    public static List<MenuItem> menu;

    private static int scanNumber() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (Exception ignored) {
            return -1;
        }
    }

    public static void printMenu(List<MenuItem> menu) {
        System.out.println();
        for (int i = 0; i < menu.size(); i++)
            System.out.printf("[%d] %s\n", i + 1, menu.get(i).text);
    }

    public static String selectItem(List<MenuItem> menu) {
        int selection;

        while (true) {
            printMenu(menu);
            selection = scanNumber();

            if (0 < selection && selection <= menu.size()) return menu.get(selection - 1).text;
            else System.out.println("Invalid selection");
        }
    }

    public static String selectItem() {
        return selectItem(menu);
    }

}
