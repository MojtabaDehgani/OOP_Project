package main;

import console.Console;
import console.menu.MenuManager;
import database.Database;
import game.history.History;
import game.history.HistoryRepository;
import game.history.HistoryService;
import main.consoleUI.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = Console.scanner;

    public static void main(String[] args) {
//        test();

        InitialData.initialize();
        Database.commit();

        menuLoop();
        close();
    }

    public static void test() {
        List<History> histories = HistoryRepository.getAllSortedPaged("playerId", "DESC", 1, 2);
        for (History history : histories)
            System.out.println(history);
    }

    public static void close() {
        Database.closeConnection();
        scanner.close();
    }

    public static void menuLoop() {
        MenuManager.menu = Menus.MainMenu;
        boolean loop = true;
        while (loop) {
            loop = menuAction();
            Database.commit();
        }
    }

    public static boolean menuAction() {

        if (MenuManager.menu == Menus.MainMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.Login -> UserConsoleUI.login();
                case Menus.SignUp -> UserConsoleUI.create();
                case Menus.ForgetPassword -> UserConsoleUI.forgetPassword();
                case Menus.Exit -> {
                    return false;
                }
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.UserMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.StartGame -> GameTwoPlayerConsoleUI.start();
                case Menus.MyCardGames -> CardConsoleUI.listUserCards();
                case Menus.GamesHistory -> MenuManager.menu = Menus.GameHistoryMenu;
                case Menus.Store -> MenuManager.menu = Menus.StoreMenu;
                case Menus.Profile -> MenuManager.menu = Menus.ProfileMenu;
                case Menus.Logout -> UserConsoleUI.logout();
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.ProfileMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.UserShowInfo -> UserConsoleUI.showCurrentUserInfo();
                case Menus.UserChangeUserName -> UserConsoleUI.changeUsername();
                case Menus.UserChangeNickname -> UserConsoleUI.changeNickname();
                case Menus.UserChangeEmail -> UserConsoleUI.changeEmail();
                case Menus.UserChangePassword -> UserConsoleUI.changePassword();
                case Menus.ForgetPassword -> UserConsoleUI.forgetPassword();
                case Menus.Back -> MenuManager.menu = Menus.UserMenu;
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.GameMode) {
            switch (MenuManager.selectItem()) {
                case Menus.TwoPlayer -> GameTwoPlayerConsoleUI.start();
                case Menus.Betting -> System.out.println("Not Implemented...");
                case Menus.OnePlayer -> System.out.println("Not Implemented...");
                case Menus.Clan -> System.out.println("Not Implemented...");
                case Menus.Back -> MenuManager.menu = Menus.UserMenu;
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.StoreMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.AvailableCards -> CardConsoleUI.listCards();
                case Menus.BuyCard -> System.out.println("Not Implemented...");
                case Menus.Back -> MenuManager.menu = Menus.UserMenu;
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.AdminMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.SeePlayers -> PlayerConsoleUI.listPlayers();
                case Menus.SeeCards -> CardConsoleUI.listCards();
                case Menus.CardAdd -> CardConsoleUI.create();
                case Menus.CardEdit -> CardConsoleUI.update();
                case Menus.CardRemove -> CardConsoleUI.delete();
                case Menus.Logout -> UserConsoleUI.logout();
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.GameHistoryMenu) {
            switch (MenuManager.selectItem()) {
                case Menus.Sort -> HistoryConsoleUI.listHistory();
                case Menus.NextPage -> System.out.println("Not Implemented...");
                case Menus.PrevPage -> System.out.println("Not Implemented...");
                case Menus.PrevNo -> System.out.println("Not Implemented...");
                case Menus.Back -> MenuManager.menu = Menus.UserMenu;
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        if (MenuManager.menu == Menus.GamePlay) {
            switch (MenuManager.selectItem()) {
                case Menus.PlayCard -> GameTwoPlayerConsoleUI.playCard();
                case Menus.ShowCard -> GameTwoPlayerConsoleUI.ShowCard();
                case Menus.Exit -> MenuManager.menu = Menus.UserMenu;
                default -> System.out.println("Invalid menu item.");
            }
            return true;
        }

        return false;
    }

}
