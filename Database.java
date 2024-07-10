package database;

import java.sql.*;

public class Database {

    private static final String sqliteUrl = "jdbc:sqlite:database.db";

    private static boolean error = false;

    private static Connection connection;

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(sqliteUrl);
            executeUpdate("PRAGMA foreign_keys=ON");
            connection.setAutoCommit(true);
            System.out.println("Connection to SQLite database established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean execute(String query) throws SQLException {
        if (error) throw new SQLException();

        query = query.replace("'null'", "null");
        query = query.replace("\"null\"", "null");
        try {
            PreparedStatement statement = getConnection().prepareStatement(query);
            int rv= statement.executeUpdate();
            if(rv==1){
                System.out.println("Inserted successfully");
            }else{
                System.out.println(rv);
            }
            /*statement.close();
            connection.close();*/
            connection=null;
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            error = true;
            throw e;
        }
    }

    public static void closeConnection() {
        commit();

        try {
            if (connection != null && !connection.isClosed())
                connection.close();

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static Connection getConnection() {
        if (connection == null) createConnection();
        return connection;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        if (error) throw new SQLException();

        query = query.replace("'null'", "null");
        query = query.replace("\"null\"", "null");
        try {
            Connection conn = getConnection();

            Statement statement = conn.createStatement();

            return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            error = true;
            throw e;
        }
    }

    public static void executeUpdate(String query) throws SQLException {
        if (error) return;

        query = query.replace("'null'", "null");
        query = query.replace("\"null\"", "null");
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            error = true;
            //throw e;
        }
    }

    public static void commit() {
        try {
            if (error) {
                rollback();
                error = false;
                return;
            }

            getConnection().commit();

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static Integer getCount(String table) {
        String query = String.format("SELECT COUNT(*) FROM %s", table);
        try {
            var result = executeQuery(query);
            if (result != null && result.next())
                return result.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static void dropTable(String table) {
        String query = String.format("DROP TABLE IF EXISTS %s", table);
        try {
            executeUpdate(query);
            commit();

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static boolean exist(String query) throws SQLException {
        try {
            return executeQuery(query).next();

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw e;
        }
    }

}
