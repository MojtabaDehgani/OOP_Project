package user;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository {

  private static final  Logger logger = Logger.getLogger(UserRepository.class.getName());
    public static final String tableName = "Users";

    public static void createTable() {
        String query = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username VARCHAR(20) UNIQUE NOT NULL,
                        password VARCHAR(20) NOT NULL,
                        email VARCHAR(20) UNIQUE NOT NULL,
                        nickname VARCHAR(20) NOT NULL,
                        recoveryQuestion VARCHAR(50) NOT NULL,
                        answer VARCHAR(20) NOT NULL
                    );
                """, tableName);

        try {
            Database.executeUpdate(query);
            Database.commit();
        } catch (Exception ignored) {
        }
    }

    public static List<User> mapResult(ResultSet result) {
        List<User> users = new ArrayList<>();
        try {
            while (result.next()) {
                User user = new User();
                user.id = result.getInt("id");
                user.username = result.getString("username");
                user.password = result.getString("password");
                user.email = result.getString("email");
                user.nickname = result.getString("nickname");
                user.recoveryQuestion = result.getString("recoveryQuestion");
                user.answer = result.getString("answer");
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return users;
    }

    public static void map(User user, User toUser) {
        toUser.id = user.id;
    }

    public static Integer getCount() {
        return Database.getCount(tableName);
    }

    public static User get(int id) {
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

    public static User getByUsername(String username) {
        String query = String.format("""
                    SELECT * FROM %s
                    WHERE username = '%s'
                """, tableName, username);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result).get(0);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<User> getAll() {
        String query = String.format("SELECT * FROM %s", tableName);

        try {
            ResultSet result = Database.executeQuery(query);
            return mapResult(result);
        } catch (Exception ignored) {
            System.exit(1);
            return null;
        }
    }
    public static void insert(User user) {
        user.recoveryQuestion= user.recoveryQuestion.replace("'","''");
       String query = String.format("""
                    INSERT INTO %s (username, password, email, nickname, recoveryQuestion, answer)
                    VALUES ('%s', '%s', '%s', '%s', '%s', '%s')
                """, tableName, user.username, user.password, user.email, user.nickname, user.recoveryQuestion, user.answer);

        System.out.println(query);

        try {
          //  ResultSet resultSet = Database.executeQuery(query);
            Database.execute(query);
            int id = Database.executeQuery("SELECT last_insert_rowid()").getInt(1);
            user.id = id;
            //map(mapResult(resultSet).get(0), user);
            logger.info(String.format("User added successfully: %s",user.toString()));
        } catch (Exception ignored) {
            logger.log(Level.SEVERE,ignored.getMessage());
            ignored.printStackTrace();
        }
    }

    public static void update(User user) {
        String query = String.format("""
                    UPDATE %s
                    SET username = '%s', password = '%s', email = '%s', nickname = '%s'
                    WHERE id = %d
                """, tableName, user.username, user.password, user.email, user.nickname, user.id);

        try {
            Database.executeUpdate(query);
        } catch (Exception ignored) {
        }
    }

    public static void delete(User user) {
        String query = String.format("""
                    DELETE FROM %s
                    WHERE id = %d
                """, tableName, user.id);

        try {
            Database.executeUpdate(query);
            user.id = null;
        } catch (Exception ignored) {
        }
    }

}
