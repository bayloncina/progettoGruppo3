package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConn {

<<<<<<< HEAD
=======
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false&serverTimezone=UTC";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DBConn] Connessione al server MySQL riuscita.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL non trovato.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("[DBConn] Connessione chiusa.");
                }
            } catch (SQLException e) {
                System.err.println("[DBConn] Errore chiusura: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
>>>>>>> 30d7d23e33350f4514ed5513ac369d47240fb4bc
}