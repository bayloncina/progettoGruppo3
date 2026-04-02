package controller;

import java.util.ArrayList;

import model.*;
import service.*;

public class Controller {

    private ArrayList<Risorsa> risorse;
    private ArrayList<Utente> utenti;
    private ArrayList<Prestito> prestiti;
    private DBConn conn;

    public Controller() {
        risorse = conn.ottieniTutteRisorse();
        conn = new DBConn();
        utenti = new ArrayList<>(); 
        prestiti = new ArrayList<>();
    }

    // METODI PER GESTIRE LE RISORSE

    // CREATE
    public void aggiungiRisorsa(Risorsa r) {
        risorse.add(r);
        conn.salvaRisorsa(r);
    }

    // READ (tutte)
    public ArrayList<Risorsa> getRisorse() {
        risorse = conn.ottieniTutteRisorse();
        return risorse;
    }

    // READ (per codice)
    public Risorsa cercaPerCodice(String codice) {
        risorse = conn.ottieniTutteRisorse();
        for (Risorsa r : risorse) {
            if (r.getCodice().equalsIgnoreCase(codice)) {
                return r;
            }
        }
        return null;
    }

    // UPDATE
    public boolean aggiornaRisorsa(String codice, String nuovoTitolo, int nuovoAnno) {
        Risorsa r = cercaPerCodice(codice);

        if (r != null) {
            r.setTitolo(nuovoTitolo);
            r.setAnnoPubblicazione(nuovoAnno);

            conn.aggiornaRisorsa(r);
            return true;
        }
        return false;
    }

    // DELETE
    public boolean eliminaRisorsa(String codice) {
        Risorsa r = cercaPerCodice(codice);

        if (r != null) {
            risorse.remove(r);
            conn.eliminaRisorsa(codice);
            return true;
        }
        return false;
    }

    //TODO manca la logica di utente e biblioteca 
    // Dennis: "Noi abbiamo utente e prestito da fare. Era richiesta anche Biblioteca?"

    public void aggiungiUtente(Utente u) {
        utenti.add(u);
        conn.salvaUtente(u);
    }

    public Utente cercaUtente(String idUtente) {
        for (Utente u : utenti) {
            if (u.getIdUtente().equalsIgnoreCase(idUtente)) return u;
        }
        return null;
    }

    public boolean prestaRisorsa(String idUtente, String codiceRisorsa) {
        Utente u = cercaUtente(idUtente);
        Risorsa r = cercaPerCodice(codiceRisorsa);
        if (u != null && r != null) {
            Prestito p = new Prestito(u, r);
            prestiti.add(p);
            conn.registraPrestito(p);
            return true;
        }
        return false;
    }

    public boolean restituisciRisorsa(String idUtente, String codiceRisorsa) {
        for (Prestito p : prestiti) {
            if (p.getUtente().getIdUtente().equalsIgnoreCase(idUtente) &&
                p.getRisorsa().getCodice().equalsIgnoreCase(codiceRisorsa) &&
                p.eAttivo()) {

                p.restituisci();
                conn.chiudiPrestito(p);
                return true;
            }
        }
        return false;
    }

}