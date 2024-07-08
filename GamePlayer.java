package game;

import game.card.Card;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlayer {
    static Random random = new Random();

    public Player player;
    public int HP;
    public List<Card> currentCards = new ArrayList<>();

    public GamePlayer(Player player) {
        this.player = player;
        this.HP = player.maxHP * player.level;

        for (int i = 0; i < 5; i++) {
            int n = random.nextInt(player.cards.size());
            currentCards.add(player.cards.get(n));
        }
    }

    @Override
    public String toString() {
        return String.format("%s HP: %d", player.user.nickname, HP);
    }
}
