package service;

import java.sql.*;
import java.util.ArrayList;

import model.*;

public class DBConn {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "biblioteca_digitale";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?createDatabaseIfNotExist=true";

    private static Connection connection = null;

    // METODI PER GESTIRE LA CONNESSIONE AL DB

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


    
    // METODI PER GESTIRE LE RISORSE NEL DB

    // CREATE
    public void salvaRisorsa(Risorsa r) {
        String sql = "INSERT INTO Risorsa (codice, titolo, annoPubblicazione, tipo, autore, numero, formato) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCodice());
            ps.setString(2, r.getTitolo());
            ps.setInt(3, r.getAnnoPubblicazione());
            ps.setString(4, r.getClass().getSimpleName());

            // valori specifici per sottoclasse
            if (r instanceof Libro) {
                ps.setString(5, ((Libro) r).getAutore());
                //i valori che non servono vengono settati a null
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else if (r instanceof Rivista) {
                ps.setNull(5, java.sql.Types.VARCHAR);
                ps.setInt(6, ((Rivista) r).getNumero());
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else if (r instanceof Ebook) {
                ps.setNull(5, java.sql.Types.VARCHAR);
                ps.setNull(6, java.sql.Types.INTEGER);
                ps.setString(7, ((Ebook) r).getFormato());
            }

            ps.executeUpdate();
            System.out.println("[DBConn] Risorsa '" + r.getCodice() + "' salvata.");

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante il salvataggio: " + e.getMessage());
        }
    }

    // READ tutte le risorse dal DB
    public ArrayList<Risorsa> ottieniTutteRisorse() {
        ArrayList<Risorsa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Risorsa";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codice = rs.getString("codice");
                String titolo = rs.getString("titolo");
                int anno = rs.getInt("annoPubblicazione");
                String tipo = rs.getString("tipo");

                Risorsa r;
                switch (tipo) {
                    case "Libro" -> r = new Libro(titolo, anno, codice, rs.getString("autore"));
                    case "Rivista" -> r = new Rivista(titolo, anno, codice, rs.getInt("numero"));
                    case "Ebook" -> r = new Ebook(titolo, anno, codice, rs.getString("formato"));
                    default -> r = null;
                }
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore lettura DB: " + e.getMessage());
        }
        return lista;
    }

    // READ singola risorsa dal DB
    /* Metodo relativamente inutile, messo per completezza
        La ricerca per codice avviene nel controller: Si aggiorna la lista completa → Si cerca in memoria, senza dover fare una query al DB ogni volta
    */
   // TODO Capire se è meglio fare una query al DB ogni volta o se conviene cercare in memoria (la prima è più "pulita", la seconda è più efficiente) ← Dennis
   // Volendo si potrebbero fare entrambe le cose, cercando prima in memoria e, se non si trova, facendo una query al DB (ma è un po' più complesso da implementare)
   // Bisognerebbe creare un metodo che aggiorni ogni volta la lista, non leggendola completamente, ma aggiornando solo le parti mancanti
   // N.B. Ciò credo sia necessario solo se si deve lavorare su diverse macchine contemporaneamente, altrimenti si può considerare che la lista in memoria sia sempre aggiornata (a meno di errori di programmazione)
    public Risorsa ottieniRisorsaPerCodice(String codiceRicerca) {
        String sql = "SELECT * FROM Risorsa WHERE codice=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codiceRicerca);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String codice = rs.getString("codice");
                    String titolo = rs.getString("titolo");
                    int anno = rs.getInt("annoPubblicazione");
                    String tipo = rs.getString("tipo");

                    return switch (tipo) {
                        case "Libro" -> new Libro(titolo, anno, codice, rs.getString("autore"));
                        case "Rivista" -> new Rivista(titolo, anno, codice, rs.getInt("numero"));
                        case "Ebook" -> new Ebook(titolo, anno, codice, rs.getString("formato"));
                        default -> null;
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("[DBConn] Errore ricerca: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public void aggiornaRisorsa(Risorsa r) {
        String sql = "UPDATE risorse SET titolo=?, anno_pubblicazione=? WHERE codice=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getTitolo());
            ps.setInt(2, r.getAnnoPubblicazione());
            ps.setString(3, r.getCodice());

            ps.executeUpdate();
            System.out.println("[DBConn] Risorsa '" + r.getCodice() + "' aggiornata con successo.");

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante l'aggiornamento: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminaRisorsa(String codice) {
        String sql = "DELETE FROM risorse WHERE codice=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codice);
            ps.executeUpdate();
            System.out.println("[DBConn] Risorsa '" + codice + "' eliminata con successo.");

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante l'eliminazione: " + e.getMessage());
        }
    }


    // METODI PER GESTIRE GLI UTENTI NEL DB

    // CREATE
    public void salvaUtente(Utente u) {
        String sql = "INSERT INTO Utente (nome, idUtente) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getIdUtente());
            ps.executeUpdate();
            System.out.println("[DBConn] Utente salvato: " + u.getNome());
        } catch(SQLException e) {
            System.err.println("[DBConn] Errore salvataggio utente: " + e.getMessage());
        }
    }
    
    // READ
    public Utente ottieniUtente(String idUtente) {
        String sql = "SELECT * FROM Utente WHERE idUtente=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utente(rs.getString("nome"), rs.getString("idUtente"));
                }
            }
        } catch(SQLException e) {
            System.err.println("[DBConn] Errore lettura utente: " + e.getMessage());
        }
        return null;
    }

    // METODI PER GESTIRE I PRESTITI NEL DB
    
    // CREATE
    public void registraPrestito(Prestito p) {
        String sql = "INSERT INTO Prestito (idUtente, idRisorsa) " +
                     "VALUES ((SELECT id FROM Utente WHERE idUtente=?), " +
                     "(SELECT id FROM Risorsa WHERE codice=?))";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getUtente().getIdUtente());
            ps.setString(2, p.getRisorsa().getCodice());
            ps.executeUpdate();
            System.out.println("[DBConn] Prestito registrato per " + p.getUtente().getNome());
        } catch(SQLException e) {
            System.err.println("[DBConn] Errore registrazione prestito: " + e.getMessage());
        }
    }
    
    // UPDATE (per chiudere un prestito, ovvero aggiornare la data di restituzione)
    public void chiudiPrestito(Prestito p) {
        String sql = "UPDATE Prestito SET dataRestituzione=CURDATE() " +
                     "WHERE idUtente=(SELECT id FROM Utente WHERE idUtente=?) " +
                     "AND idRisorsa=(SELECT id FROM Risorsa WHERE codice=?) " +
                     "AND dataRestituzione IS NULL";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getUtente().getIdUtente());
            ps.setString(2, p.getRisorsa().getCodice());
            ps.executeUpdate();
            System.out.println("[DBConn] Prestito chiuso per " + p.getUtente().getNome());
        } catch(SQLException e) {
            System.err.println("[DBConn] Errore chiusura prestito: " + e.getMessage());
        }
    }

}