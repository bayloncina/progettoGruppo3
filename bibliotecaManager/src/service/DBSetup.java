package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {

    public static void main(String[] args) throws SQLException {
        Connection conn = DBConn.getConnection();

        try (Statement stmt = conn.createStatement()) {

            String createRisorse = "CREATE TABLE IF NOT EXISTS Risorsa (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "titolo VARCHAR(255) NOT NULL," +
                    "annoPubblicazione INT NOT NULL," +
                    "codice VARCHAR(100) UNIQUE NOT NULL," +
                    "tipo VARCHAR(50) NOT NULL," +
                    "autore VARCHAR(255)," +
                    "numero INT," +
                    "formato VARCHAR(50)" +
                    ");";
            stmt.executeUpdate(createRisorse);

            String createUtenti = "CREATE TABLE IF NOT EXISTS Utente (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "idUtente VARCHAR(100) UNIQUE NOT NULL" +
                    ");";
            stmt.executeUpdate(createUtenti);

            String createPrestiti = "CREATE TABLE IF NOT EXISTS Prestito (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "idUtente INT NOT NULL," +
                    "idRisorsa INT NOT NULL," +
                    "FOREIGN KEY (idUtente) REFERENCES Utente(id)," +
                    "FOREIGN KEY (idRisorsa) REFERENCES Risorsa(id)" +
                    ");";
            stmt.executeUpdate(createPrestiti);

            System.out.println("Tabelle create con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConn.closeConnection();
        }
    }
}