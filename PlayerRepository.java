package game.player;

import database.Database;
import game.card.Card;
import game.card.CardRepository;
import user.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerRepository {

    public static final String tableName = "Players";
    public static final String relationTableName = "PlayersCards";

    public static void createTable() {
        String query = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        userId INT UNIQUE NOT NULL,
                        level INT NOT NULL,
                        maxHP INT NOT NULL,
                        XP INT NOT NULL,
                        coins INT NOT NULL,
                        
                        FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
                    );
                    
                    CREATE TABLE IF NOT EXISTS %s (
                        playerId INT NOT NULL,
                        cardId INT NOT NULL,
                        
                        UNIQUE (playerId, cardId),
                        
                        FOREIGN KEY (playerId) REFERENCES Players(id) ON DELETE CASCADE,
                        FOREIGN KEY (cardId) REFERENCES Cards(id) ON DELETE CASCADE
                    );
                """, tableName, relationTableName);

        try {
            Database.executeUpdate(query);
            Database.commit();
        } catch (Exception ignored) {
        }
    }

    public static List<Player> mapResult(ResultSet result) {
        List<Player> players = new ArrayList<>();
        try {
            while (result.next()) {
                Player player = new Player();
                player.id = result.getInt("id");
                player.userId = result.getInt("userId");
                player.level = result.getInt("level");
                player.maxHP = result.getInt("maxHP");
                player.XP = result.getInt("XP");
                player.coins = result.getInt("coins");
                player.user = UserRepository.get(player.userId);
                player.cards = getCards(player.id);
                players.add(player);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return players;
    }

    public static void map(Player player, Player toPlayer) {
        toPlayer.id = player.id;
        toPlayer.user = player.user;
    }

    public static Integer getCount() {
        return Database.getCount(tableName);
    }

    public static Player get(int id) {
        String query = String.format("""
                    SELECT * FROM %s
                    WHERE id = %d
                """, tableName, id);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result).get(0);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Player getByUserId(int userId) {
        String query = String.format("""
                    SELECT * FROM %s
                    WHERE userId = %d
                """, tableName, userId);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result).get(0);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Player> getAll() {
        String query = String.format("SELECT * FROM %s", tableName);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }

    public static void insert(Player player) {
        String query = String.format("""
                    INSERT INTO %s (userId, level, maxHP, XP, coins)
                    VALUES (%d, %d, %d, %d, %d)
                    RETURNING *;
                """, tableName, player.userId, player.level, player.maxHP, player.XP, player.coins);

        try {
            ResultSet result = Database.executeQuery(query);
            map(mapResult(result).get(0), player);
        } catch (Exception ignored) {
        }
    }

    public static void update(Player player) {
        String query = String.format("""
                    UPDATE %s
                    SET level = %d, maxHP = %d, XP = %d, coins = %d
                    WHERE id = %d
                """, tableName, player.level, player.maxHP, player.XP, player.coins, player.id);

        try {
            Database.executeUpdate(query);
        } catch (Exception ignored) {
        }
    }

    public static void delete(Player player) {
        String query = String.format("""
                    DELETE FROM %s
                    WHERE id = %d
                """, tableName, player.id);

        try {
            Database.executeUpdate(query);
            player.id = null;
        } catch (Exception ignored) {
        }
    }

    public static void addCard(Player player, Card card) {
        String query = String.format("""
                    INSERT INTO %s (playerId, cardId)
                    VALUES (%d, %d)
                """, relationTableName, player.id, card.id);

        try {
            Database.executeUpdate(query);
            player.cards.add(card);
        } catch (Exception ignored) {
        }
    }

    public static void removeCard(Player player, Card card) {
        String query = String.format("""
                    DELETE FROM %s
                    WHERE playersId = %d, cardId = %d
                """, relationTableName, player.id, card.id);

        try {
            Database.executeUpdate(query);
            player.cards.removeIf(x -> x.id.equals(card.id));
        } catch (Exception ignored) {
        }
    }

    public static List<Card> getCards(int id) {
        String query = String.format("""
                    SELECT cardId FROM %s
                    WHERE playerId = %d
                """, relationTableName, id);

        try {
            ResultSet result = Database.executeQuery(query);
            List<Integer> cardsIds = new ArrayList<>();

            while (result.next()) {
                int cardId = result.getInt("cardId");
                cardsIds.add(cardId);
            }

            return cardsIds.stream().map(CardRepository::get).collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return new ArrayList<>();
        }
    }

}
