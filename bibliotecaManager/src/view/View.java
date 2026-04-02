package view;

import java.util.ArrayList;

import controller.Controller;
import model.*;
import utility.Utility;

public class View {

    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void avvia() {
        int scelta;

        do {
            mostraMenu();
            scelta = Utility.askInt();

            switch (scelta) {
                case 1:
                    aggiungiRisorsa();
                    break;
                case 2:
                    visualizzaRisorse();
                    break;
                case 3:
                    cercaRisorsa();
                    break;
                case 4:
                    aggiornaRisorsa();
                    break;
                case 5:
                    eliminaRisorsa();
                    break;
                case 0:
                    System.out.println("Uscita dal programma...");
                    break;
                default:
                    System.out.println("Scelta non valida!");
            }

        } while (scelta != 0);
    }

    private void mostraMenu() {
        System.out.println("\n=== GESTIONALE BIBLIOTECA ===");
        System.out.println("1) Aggiungi Risorsa");
        System.out.println("2) Visualizza Risorse");
        System.out.println("3) Cerca Risorsa per Codice");
        System.out.println("4) Aggiorna Risorsa");
        System.out.println("5) Elimina Risorsa");
        System.out.println("0) Esci");
        System.out.println("==============================");
        System.out.print("Scelta: ");
    }

    // CREATE
    private void aggiungiRisorsa() {
        System.out.println("\n=== GESTIONALE BIBLIOTECA ===");
        System.out.println("Scegli tipo di risorsa:");
        System.out.println("1) Libro");
        System.out.println("2) Rivista");
        System.out.println("3) Ebook");
        System.out.println("==============================");
        System.out.print("Scelta: ");
    
        int tipo = Utility.askInt();
    
        System.out.print("Titolo: ");
        String titolo = Utility.askString();
    
        System.out.print("Anno: ");
        int anno = Utility.askInt();
    
        System.out.print("Codice: ");
        String codice = Utility.askString();
    
        Risorsa r = null;
    
        switch (tipo) {
            case 1:
                System.out.print("Autore: ");
                String autore = Utility.askString();
                r = new model.Libro(titolo, anno, codice, autore);
                break;
    
            case 2:
                System.out.print("Numero rivista: ");
                int numero = Utility.askInt();
                r = new model.Rivista(titolo, anno, codice, numero);
                break;
    
            case 3:
                System.out.print("Formato (es. PDF): ");
                String formato = Utility.askString();
                r = new model.Ebook(titolo, anno, codice, formato);
                break;
    
            default:
                System.out.println("Tipo non valido!");
                return;
        }
    
        controller.aggiungiRisorsa(r);
        System.out.println("Risorsa aggiunta con successo!");
    }

    // READ
    private void visualizzaRisorse() {
        ArrayList<Risorsa> lista = controller.getRisorse();

        if (lista.isEmpty()) {
            System.out.println("Nessuna risorsa presente.");
            return;
        }

        for (Risorsa r : lista) {
            r.visualizzaDettagli();
            System.out.println("===================");
        }
    }

    // READ singola
    private void cercaRisorsa() {
        System.out.print("Inserisci codice: ");
        String codice = Utility.askString();

        Risorsa r = controller.cercaPerCodice(codice);

        if (r != null) {
            r.visualizzaDettagli();
        } else {
            System.out.println("Risorsa non trovata!");
        }
    }

    // UPDATE
    private void aggiornaRisorsa() {
        System.out.print("Codice risorsa da aggiornare: ");
        String codice = Utility.askString();

        System.out.print("Nuovo titolo: ");
        String titolo = Utility.askString();

        System.out.print("Nuovo anno: ");
        int anno = Utility.askInt();

        boolean aggiornato = controller.aggiornaRisorsa(codice, titolo, anno);

        if (aggiornato) {
            System.out.println("Risorsa aggiornata!");
        } else {
            System.out.println("Risorsa non trovata!");
        }
    }

    // DELETE
    private void eliminaRisorsa() {
        System.out.print("Codice risorsa da eliminare: ");
        String codice = Utility.askString();

        boolean eliminato = controller.eliminaRisorsa(codice);

        if (eliminato) {
            System.out.println("Risorsa eliminata!");
        } else {
            System.out.println("Risorsa non trovata!");
        }
    }
}