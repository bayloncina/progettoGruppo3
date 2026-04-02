package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.*;

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

    // salva una risorsa nel database ──
    public void salvaRisorsa(Risorsa r) {
        String sql = "INSERT INTO Risorsa (titolo, annoPubblicazione, codice, tipo, autore, numero, formato) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, r.getTitolo());
            ps.setInt(2, r.getAnnoPubblicazione());
            ps.setString(3, r.getCodice());
            ps.setString(4, r.getClass().getSimpleName());

            // setta il campo specifico — gli altri a stringa vuota
            if (r instanceof Libro l) {
                ps.setString(5, l.getAutore());
                ps.setString(6, "");
                ps.setString(7, "");
            } else if (r instanceof Rivista rv) {
                ps.setString(5, "");
                ps.setString(6, String.valueOf(rv.getNumero()));
                ps.setString(7, "");
            } else if (r instanceof Ebook e) {
                ps.setString(5, "");
                ps.setString(6, "");
                ps.setString(7, e.getFormato());
            }

            ps.executeUpdate();
            System.out.println("[DBConn] Risorsa salvata: " + r.getTitolo());
        } catch (SQLException e) {
            System.err.println("[DBConn] Errore salvataggio: " + e.getMessage());
        }
    }

    // aggiorna titolo e anno cercando per codice
    public void aggiornaRisorsa(Risorsa r) {
        String sql = "UPDATE Risorsa SET titolo = ?, annoPubblicazione = ? WHERE codice = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, r.getTitolo());
            ps.setInt(2, r.getAnnoPubblicazione());
            ps.setString(3, r.getCodice());
            int righe = ps.executeUpdate();
            if (righe > 0) {
                System.out.println("[DBConn] Risorsa aggiornata: " + r.getCodice());
            } else {
                System.out.println("[DBConn] Risorsa non trovata: " + r.getCodice());
            }
        } catch (SQLException e) {
            System.err.println("[DBConn] Errore aggiornamento: " + e.getMessage());
        }
    }

    // elimina una risorsa per codice
    public void eliminaRisorsa(String codice) {
        String sql = "DELETE FROM Risorsa WHERE codice = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, codice);
            int righe = ps.executeUpdate();
            if (righe > 0) {
                System.out.println("[DBConn] Risorsa eliminata: " + codice);
            } else {
                System.out.println("[DBConn] Risorsa non trovata: " + codice);
            }
        } catch (SQLException e) {
            System.err.println("[DBConn] Errore eliminazione: " + e.getMessage());
        }
    }
}