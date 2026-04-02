package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Ebook;
import model.Libro;
import model.Risorsa;
import model.Rivista;

public class DBConn {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DB_NAME = "biblioteca_digitale";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?createDatabaseIfNotExist=true";

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

    // TODO Inserire gli altri attrubuti per libro, ebook e rivista
    public void salvaRisorsa(Risorsa r) {
        String sql = "INSERT INTO Risorsa (codice, titolo, annoPubblicazione, tipo, autore, numero, formato) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConn.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCodice());
            ps.setString(2, r.getTitolo());
            ps.setInt(3, r.getAnnoPubblicazione());
            ps.setString(4, r.getClass().getSimpleName());

            // valori specifici per sottoclasse
            if (r instanceof Libro) {

                /*
                 * Il ((Libro) r) si chiama cast serve perché r è dichiarato come Risorsa
                 * (classe base), quindi Java non sa che ha il metodo getAutore().
                 * Il cast dice a Java "fidati, questo oggetto è un Libro" e ti permette di
                 * chiamare i metodi specifici della sottoclasse.
                 */
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

    public ArrayList<Risorsa> caricaRisorse() {
        ArrayList<Risorsa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Risorsa";

        try (Connection conn = DBConn.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String titolo = rs.getString("titolo");
                int anno = rs.getInt("annoPubblicazione");
                String codice = rs.getString("codice");
                String tipo = rs.getString("tipo");

                Risorsa r = null;

                switch (tipo) {
                    case "Libro":
                        r = new Libro(titolo, anno, codice, rs.getString("autore"));
                        break;
                    case "Rivista":
                        r = new Rivista(titolo, anno, codice, rs.getInt("numero"));
                        break;
                    case "Ebook":
                        r = new Ebook(titolo, anno, codice, rs.getString("formato"));
                        break;
                    default:
                        System.out.println("[DBConn] Tipo sconosciuto: " + tipo);
                }

                if (r != null) {
                    lista.add(r);
                }
            }

            System.out.println("[DBConn] " + lista.size() + " risorse caricate dal database.");

        } catch (SQLException e) {
            System.err.println("[DBConn] Errore durante il caricamento: " + e.getMessage());
        }

        return lista;
    }
}