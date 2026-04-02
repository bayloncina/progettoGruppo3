import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
)

public class DBConn {
 
  
    private static final String HOST     = "localhost";
    private static final int    PORT     = 3306;
    private static final String DB_NAME  = "biblioteca_digitale";
    private static final String USER     = "root";
    private static final String PASSWORD = ""; 
 
    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
 

    private static Connection connection = null;
 
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[DBConn] Connessione a '" + DB_NAME + "' riuscita.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL non trovato. Aggiungi mysql-connector-j al classpath.", e);
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
                System.err.println("[DBConn] Errore durante la chiusura: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}