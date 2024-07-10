package game.card;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public static final String tableName = "Cards";

    public static void createTable() {
        String query = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(20) UNIQUE NOT NULL,
                        defenceAttack INT NOT NULL,
                        duration INT NOT NULL,
                        playerDamage INT NOT NULL,
                        upgradeLevel INT NOT NULL,
                        upgradeCost INT NOT NULL,
                        price INT NOT NULL,
                        imageName varchar(50)
                    );
                """, tableName);

        try {
            Database.executeUpdate(query);
            Database.commit();
        } catch (Exception ignored) {
        }
    }

    private static List<Card> mapResult(ResultSet result) {
        List<Card> cards = new ArrayList<>();
        try {
            while (result.next()) {
                Card card = new Card();
                card.id = result.getInt("id");
                card.name = result.getString("name");
                card.defenceAttack = result.getInt("defenceAttack");
                card.duration = result.getInt("duration");
                card.playerDamage = result.getInt("playerDamage");
                card.upgradeLevel = result.getInt("upgradeLevel");
                card.upgradeCost = result.getInt("upgradeCost");
                card.price = result.getInt("price");
                card.imagePath = result.getString("imageName");
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return cards;
    }

    public static Integer getCount() {
        return Database.getCount(tableName);
    }

    public static Card get(int id) {
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

    public static List<Card> getAll() {
        String query = String.format("SELECT * FROM %s", tableName);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }

    public static void insert(Card card) {
        String query = String.format("""
                    INSERT INTO %s (name, defenceAttack, duration, playerDamage, upgradeLevel, upgradeCost, price,imageName)
                    VALUES ("%s", %d, %d, %d, %d, %d, %d,"%s")
                    RETURNING *;
                """, tableName, card.name, card.defenceAttack, card.duration, card.playerDamage, card.upgradeLevel, card.upgradeCost, card.price,card.imagePath);

        System.out.println(query);
        try {
            ResultSet result = Database.executeQuery(query);
            card.id = mapResult(result).get(0).id;
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    public static void update(Card card) {
        String query = String.format("""
                    UPDATE %s
                    SET name = '%s', defenceAttack = %d,imageName = '%s', duration = %d, playerDamage = %d, upgradeLevel = %d, upgradeCost = %d, price = %d
                    WHERE id = %d
                """, tableName, card.name, card.defenceAttack,card.imagePath, card.duration, card.playerDamage, card.upgradeLevel, card.upgradeCost, card.price, card.id);
if(card.imagePath==null ){
     query = String.format("""
                    UPDATE %s
                    SET name = '%s', defenceAttack = %d, duration = %d, playerDamage = %d, upgradeLevel = %d, upgradeCost = %d, price = %d
                    WHERE id = %d
                """, tableName, card.name, card.defenceAttack, card.duration, card.playerDamage, card.upgradeLevel, card.upgradeCost, card.price, card.id);

}
        try {
            Database.executeUpdate(query);
        } catch (Exception ignored) {
        }
    }

    public static void delete(Card card) {
        String query = String.format("""
                    DELETE FROM %s
                    WHERE id = %d
                """, tableName, card.id);

        try {
            Database.executeUpdate(query);
            card.id = null;
        } catch (Exception ignored) {
        }
    }

    public static boolean buyCard(int playerId, int cardId) {
        String query= """

INSERT INTO PlayersCards(playerId,cardId) 
values (%d,%d);
""";
        try {
            query=String.format(query,playerId,cardId);
            System.out.println(query);

            Database.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
