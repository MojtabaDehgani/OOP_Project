package main.consoleUI;

import game.history.History;
import game.history.HistoryService;

import java.util.List;

public class HistoryConsoleUI {

    public static void listHistory() {
        List<History> histories = HistoryService.getAllSorted();
        for (History history : histories)
            System.out.println(history);
    }

}
