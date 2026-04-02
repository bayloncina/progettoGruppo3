package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {

    public static void setup() {
        try {
            Connection conn = DBConn.getConnection();
            creaTabelle(conn);
        } catch (SQLException e) {
            System.err.println("[DBSetup] Errore setup: " + e.getMessage());
        }
    }

    private static void creaTabelle(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            // tabella risorse — colonne opzionali NULL in base al tipo
            String createRisorse = "CREATE TABLE IF NOT EXISTS Risorsa (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "titolo VARCHAR(255) NOT NULL," +
                    "annoPubblicazione INT NOT NULL," +
                    "codice VARCHAR(100) UNIQUE NOT NULL," +
                    "tipo VARCHAR(50) NOT NULL," +   // Libro, Rivista, Ebook
                    "autore VARCHAR(255)," +          // solo per Libro
                    "numero INT," +                   // solo per Rivista
                    "formato VARCHAR(50)" +           // solo per Ebook
                    ");";
            stmt.executeUpdate(createRisorse);
            System.out.println("[DBSetup] Tabella 'Risorsa' pronta!");

            // tabella utenti
            String createUtenti = "CREATE TABLE IF NOT EXISTS Utente (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "idUtente VARCHAR(100) UNIQUE NOT NULL" +
                    ");";
            stmt.executeUpdate(createUtenti);
            System.out.println("[DBSetup] Tabella 'Utente' pronta!");

            // tabella prestiti — collega utenti e risorse
            String createPrestiti = "CREATE TABLE IF NOT EXISTS Prestito (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "idUtente INT NOT NULL," +
                    "idRisorsa INT NOT NULL," +
                    "FOREIGN KEY (idUtente) REFERENCES Utente(id)," +
                    "FOREIGN KEY (idRisorsa) REFERENCES Risorsa(id)" +
                    ");";
            stmt.executeUpdate(createPrestiti);
            System.out.println("[DBSetup] Tabella 'Prestito' pronta!");

        } catch (SQLException e) {
            System.err.println("[DBSetup] Errore creazione tabelle: " + e.getMessage());
        }
    }
}