package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {

    public static void init() throws SQLException {
        Connection conn = DBConn.getConnection();

        try (Statement stmt = conn.createStatement()) {

            // Crea e seleziona il database
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS biblioteca_digitale");
            stmt.executeUpdate("USE biblioteca_digitale");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Risorsa (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "titolo VARCHAR(255) NOT NULL," +
                    "annoPubblicazione INT NOT NULL," +
                    "codice VARCHAR(100) UNIQUE NOT NULL," +
                    "tipo VARCHAR(50) NOT NULL," +
                    "autore VARCHAR(255)," +
                    "numero INT," +
                    "formato VARCHAR(50)" +
                    ");");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Utente (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "idUtente VARCHAR(100) UNIQUE NOT NULL" +
                    ");");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Prestito (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "idUtente INT NOT NULL," +
                    "idRisorsa INT NOT NULL," +
                    "FOREIGN KEY (idUtente) REFERENCES Utente(id)," +
                    "FOREIGN KEY (idRisorsa) REFERENCES Risorsa(id)" +
                    ");");

            System.out.println("[DBSetup] Database e tabelle creati con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}