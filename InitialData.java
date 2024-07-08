package main;

import game.card.Card;
import game.card.CardRepository;
import game.card.CardService;
import game.history.HistoryRepository;
import game.player.PlayerRepository;
import user.User;
import user.UserRepository;
import user.UserService;

public class InitialData {

    public static void initialize() {
        UserRepository.createTable();
        PlayerRepository.createTable();
        CardRepository.createTable();
        HistoryRepository.createTable();

        createInitialUsers();
        createInitialCards();
    }

    public static void createInitialUsers() {
        Integer count = UserRepository.getCount();
        if (count != null && count == 0) {
            User admin = new User("admin", "admin", "admin@domain.com", "admin", "What is your favourite color?", "black");
            User user1 = new User("hamed", "1234", "hamed@domain.com", "hamed", "What is your favourite color?", "blue");
            User user2 = new User("sara", "1234", "sara@domain.com", "sara", "What is your favourite color?", "red");

            UserService.create(admin);
            UserService.create(user1);
            UserService.create(user2);
        }
    }

    public static void createInitialCards() {
        Integer count = CardRepository.getCount();
        if (count != null && count == 0) {
            CardService.create(new Card("Shield", true, 20, 2, 10, 1, 1, 10));
            CardService.create(new Card("Healing", true, 30, 4, 12, 1, 1, 10));
            CardService.create(new Card("Power Booster", true, 25, 3, 12, 1, 1, 10));
            CardService.create(new Card("Hole Changer", true, 28, 3, 9, 1, 1, 10));
            CardService.create(new Card("Round Reducer", true, 32, 4, 8, 1, 1, 10));
            CardService.create(new Card("Opponent's Card Remover", true, 34, 2, 4, 1, 1, 10));
            CardService.create(new Card("Opponent's Card Weakening", true, 22, 3, 9, 1, 1, 10));
            CardService.create(new Card("Copier", true, 26, 3, 6, 1, 1, 10));
        }
    }
}
