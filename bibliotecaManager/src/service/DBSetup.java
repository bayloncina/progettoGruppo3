package service;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {

    public static void setupDatabase() {

        try {
            Connection conn = DBConn.getConnection();

            try (Statement stmt = conn.createStatement()) {

                // Creazione database
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS biblioteca_digitale");
                stmt.executeUpdate("USE biblioteca_digitale");

                // Classe base: Risorsa
                String risorsa = "CREATE TABLE IF NOT EXISTS Risorsa (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "titolo VARCHAR(255) NOT NULL," +
                        "annoPubblicazione INT NOT NULL," +
                        "codice VARCHAR(100) UNIQUE NOT NULL" +
                        ");";
                stmt.executeUpdate(risorsa);

                // Sottoclasse: Libro
                String libro = "CREATE TABLE IF NOT EXISTS Libro (" +
                        "id INT PRIMARY KEY," +
                        "autore VARCHAR(255) NOT NULL," +
                        "FOREIGN KEY (id) REFERENCES Risorsa(id) ON DELETE CASCADE" +
                        ");";
                stmt.executeUpdate(libro);

                // Sottoclasse: Rivista
                String rivista = "CREATE TABLE IF NOT EXISTS Rivista (" +
                        "id INT PRIMARY KEY," +
                        "numero INT NOT NULL," +
                        "FOREIGN KEY (id) REFERENCES Risorsa(id) ON DELETE CASCADE" +
                        ");";
                stmt.executeUpdate(rivista);

                // Sottoclasse: Ebook
                String ebook = "CREATE TABLE IF NOT EXISTS Ebook (" +
                        "id INT PRIMARY KEY," +
                        "formato VARCHAR(50) NOT NULL," +
                        "FOREIGN KEY (id) REFERENCES Risorsa(id) ON DELETE CASCADE" +
                        ");";
                stmt.executeUpdate(ebook);

                // Utente
                String utente = "CREATE TABLE IF NOT EXISTS Utente (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "nome VARCHAR(255) NOT NULL," +
                        "idUtente VARCHAR(100) UNIQUE NOT NULL" +
                        ");";
                stmt.executeUpdate(utente);

                // Prestito
                String prestito = "CREATE TABLE IF NOT EXISTS Prestito (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "idUtente INT NOT NULL," +
                        "idRisorsa INT NOT NULL," +
                        "FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (idRisorsa) REFERENCES Risorsa(id) ON DELETE CASCADE" +
                        ");";
                stmt.executeUpdate(prestito);

                System.out.println("[DBSetup] Database e tabelle creati correttamente.");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConn.closeConnection();
        }
    }
}