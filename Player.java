package game.player;

import game.card.Card;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public Integer id;
    public int userId;
    public int level;
    public int maxHP;
    public int XP;
    public int coins;
    public User user;
    public List<Card> cards = new ArrayList<>();

    public Player() {
    }

    public Player(int userId, int level, int maxHP, int XP, int coins) {
        this.userId = userId;
        this.level = level;
        this.maxHP = maxHP;
        this.XP = XP;
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", userId: " + userId +
                ", level: " + level +
                ", maxHP: " + maxHP +
                ", XP: " + XP +
                ", coins: " + coins
                ;
    }
}
