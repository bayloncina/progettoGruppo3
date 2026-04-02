package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Risorsa;

public class DBConn {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "biblioteca_digitale";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;

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
//TODO Inserire gli altri attrubuti per libro, ebook e rivista
    public void salvaRisorsa(Risorsa r) {
        String sql = "INSERT INTO risorse (codice, titolo, anno_pubblicazione) VALUES (?, ?, ?)";

        try (Connection conn = DBConn.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCodice());
            ps.setString(2, r.getTitolo());
            ps.setInt(3, r.getAnnoPubblicazione());

            int righeInserite = ps.executeUpdate();

            if (righeInserite > 0) {
                System.out.println("[DBConn] Risorsa '" + r.getCodice() + "' salvata con successo.");
            }

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante il salvataggio della risorsa: " + e.getMessage());
        }
    }

    public void salvaRisorsa(String codice) {
        String sql = "INSERT INTO risorse (codice) VALUES (?)";

        try (Connection conn = DBConn.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codice);

            int righeInserite = ps.executeUpdate();

            if (righeInserite > 0) {
                System.out.println("[DBConn] Risorsa con codice '" + codice + "' salvata con successo.");
            }

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante il salvataggio: " + e.getMessage());
        }
    }

}