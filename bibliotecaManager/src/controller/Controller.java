package controller;

import java.util.ArrayList;

import model.Risorsa;
import service.DBConn;

public class Controller {

    private ArrayList<Risorsa> risorse;
    private DBConn conn;

    public Controller() {
        risorse = new ArrayList<>();
        conn = new DBConn();
    }

    // CREATE
    public void aggiungiRisorsa(Risorsa r) {
        risorse.add(r);
        conn.salvaRisorsa(r); // TODO Aggiungere in DBConn metodo slavaRisorsa(Risorsa r)
    }

    // READ (tutte)
    public ArrayList<Risorsa> getRisorse() {
        return risorse;
    }

    // READ (per codice)
    public Risorsa cercaPerCodice(String codice) {
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

            conn.aggiornaRisorsa(r); // TODO Aggiungere in DBConn metodo aggiornaRisorsa(Risorsa r), che dovrà aggiornare la risorsa nel database in base al codice
            return true;
        }
        return false;
    }

    // DELETE
    public boolean eliminaRisorsa(String codice) {
        Risorsa r = cercaPerCodice(codice);

        if (r != null) {
            risorse.remove(r);
            conn.eliminaRisorsa(codice); // TODO Aggiungere in DBConn metodo eliminaRisorsa(String codice), che dovrà eliminare la risorsa dal database in base al codice
            return true;
        }
        return false;
    }
}