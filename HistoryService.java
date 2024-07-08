package game.history;

import java.util.List;

public class HistoryService {

    public static boolean isValid(History history) {
        return true;
    }

    public static History copy(History history) {
        return null;
    }

    public static void create(History history) {
        if (isValid(history)) HistoryRepository.insert(history);
    }

    public static List<History> getAll() {
        return HistoryRepository.getAll();
    }

    public static List<History> getAllSorted() {
        return HistoryRepository.getAllSorted("DateTime", "ASC");
    }

}
