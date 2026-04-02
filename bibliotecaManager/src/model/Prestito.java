package model;

import java.time.LocalDate;

public class Prestito {
    private Utente utente;
    private Risorsa risorsa;
    private LocalDate dataPrestito;
    private LocalDate dataRestituzione;

    public Prestito(Utente utente, Risorsa risorsa) {
        this.utente = utente;
        this.risorsa = risorsa;
        this.dataPrestito = LocalDate.now();
        this.dataRestituzione = null;
    }

    public Utente getUtente() { return utente; }
    public Risorsa getRisorsa() { return risorsa; }
    public LocalDate getDataPrestito() { return dataPrestito; }
    public LocalDate getDataRestituzione() { return dataRestituzione; }

    public void restituisci() {
        this.dataRestituzione = LocalDate.now();
    }

    public boolean eAttivo() {
        return dataRestituzione == null;
    }
}