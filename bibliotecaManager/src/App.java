import controller.*;
import view.*;

public class App {
    public static void main(String[] args) throws Exception {

        // Creo il controller
        Controller controller = new Controller();

        // Creo la view passando il controller
        View view = new View(controller);

        // Avvio il gestionale interattivo
        view.avvia();
    }
}