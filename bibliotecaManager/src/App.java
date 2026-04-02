import controller.*;
import service.DBConn;
import service.DBSetup;
import view.*;

public class App {
    public static void main(String[] args) throws Exception {

        try {
           DBSetup.init();;         // crea DB e tabelle se non esistono
            DBConn.getConnection(); // ora si connette a biblioteca_digitale
        } catch (Exception e) {
            System.err.println("Errore connessione al database: " + e.getMessage());
            return; // blocca l'avvio se il DB non è raggiungibile
        }

        // Creo il controller
        Controller controller = new Controller();

        // Creo la view passando il controller
        View view = new View(controller);

        // Avvio il gestionale interattivo
        view.avvia();

        DBConn.closeConnection();
    }
}