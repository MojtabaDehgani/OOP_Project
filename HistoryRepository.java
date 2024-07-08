package game.history;

import database.Database;
import user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

    public static final String tableName = "Histories";

    public static void createTable() {
        String query = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        dateTime TIMESTAMP NOT NULL,
                        playerId INT NOT NULL,
                        opponentId INT NOT NULL,
                        result VARCHAR(20) NOT NULL,
                        score INT NOT NULL
                    );
                """, tableName);

        try {
            Database.executeUpdate(query);
            Database.commit();
        } catch (Exception ignored) {
        }
    }

    private static List<History> mapResult(ResultSet result) {
        List<History> histories = new ArrayList<>();
        try {
            while (result.next()) {
                History history = new History();
                history.id = result.getInt("id");
                history.dateTime = Instant.ofEpochSecond(result.getLong("dateTime"));
                history.playerId = result.getInt("playerId");
                history.opponentId = result.getInt("opponentId");
                history.result = result.getString("result");
                history.score = result.getInt("score");
                histories.add(history);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return histories;
    }

    public static Integer getCount() {
        return Database.getCount(tableName);
    }

    public static History get(int id) {
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

    public static List<History> getAll() {
        String query = String.format("SELECT * FROM %s", tableName);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }

    public static List<History> getAllSorted(String columnName, String sortOrder) {
        String query = String.format("""
                            SELECT * FROM %s
                            ORDER BY %s %s;
                        """
                , tableName, columnName, sortOrder);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }

    public static List<History> getAllSortedPaged(String columnName, String sortOrder, int page, int pageSize) {
        // Sort order ASC || DESC

        String query = String.format("""
                            SELECT * FROM (
                                SELECT *, ROW_NUMBER() OVER (
                                        ORDER BY %s %s
                                ) AS row_num
                                FROM %s
                            )
                            WHERE row_num BETWEEN %d AND %d
                        """
                , columnName, sortOrder, tableName, (page - 1) * pageSize + 1, page * pageSize);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }

    public static void insert(History history) {
        System.out.println(history.dateTime);
        String query = String.format("""
                    INSERT INTO %s (dateTime, playerId, opponentId, result, score)
                    VALUES ('%s', %d, %d, '%s', %d)
                    RETURNING *;
                """, tableName, Instant.now().getEpochSecond(), history.playerId, history.opponentId, history.result, history.score);

        try {
            ResultSet result = Database.executeQuery(query);
            history.id = mapResult(result).get(0).id;
        } catch (Exception ignored) {
        }
    }

}
