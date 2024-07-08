package game.player;

import game.card.Card;
import game.card.CardService;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PlayerService {

    public static boolean isValid(Player player) {
        return true;
    }

    public static Player copy(Player player) {
        return player;
    }

    public static void create(Player player) {
        if (isValid(player)) PlayerRepository.insert(player);
    }

    public static Player get(int id) {
        return PlayerRepository.get(id);
    }

    public static Player getByUserId(int userId) {
        Player player = PlayerRepository.getByUserId(userId);
        if (player != null) return player;

        player = new Player(userId, 1, 100, 0, 100);
        PlayerService.create(player);

        // add random cards
        List<Card> cards = CardService.getAll();
        int firstCardsCount = Math.min(cards.size(), 20);

        Random random = new Random();
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < firstCardsCount) numbers.add(random.nextInt(cards.size()));
        for (int i : numbers) PlayerRepository.addCard(player, cards.get(i));

        return player;
    }

    public static List<Player> getAll() {
        return PlayerRepository.getAll();
    }

    public static void update(Player player) {
        if (isValid(player)) PlayerRepository.update(player);
    }

    public static void delete(Player player) {
        PlayerRepository.delete(player);
    }

}
