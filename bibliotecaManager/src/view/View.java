package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.Controller;
import model.Risorsa;

public class View {
    

    private Controller controller;
    private Scanner scanner;

    public View(Controller controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    public void avvia() {
        int scelta;

        do {
            mostraMenu();
            scelta = scanner.nextInt();
            scanner.nextLine(); // pulizia buffer

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
        // TODO: Implementare la logica per aggiungere una nuova risorsa (libro, rivista, DVD) chiedendo all'utente i dettagli necessari e poi chiamando controller.aggiungiRisorsa(new Risorsa(...))
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
            System.out.println("------------------");
        }
    }

    // READ singola
    private void cercaRisorsa() {
        System.out.print("Inserisci codice: ");
        String codice = scanner.nextLine();

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
        String codice = scanner.nextLine();

        System.out.print("Nuovo titolo: ");
        String titolo = scanner.nextLine();

        System.out.print("Nuovo anno: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

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
        String codice = scanner.nextLine();

        boolean eliminato = controller.eliminaRisorsa(codice);

        if (eliminato) {
            System.out.println("Risorsa eliminata!");
        } else {
            System.out.println("Risorsa non trovata!");
        }
    }
}