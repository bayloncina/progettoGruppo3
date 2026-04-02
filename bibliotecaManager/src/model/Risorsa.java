package model;

public abstract class Risorsa {
    private String titolo;
    private int annoPubblicazione;
    private String codice; // identificativo univoco

    public Risorsa(String titolo, int annoPubblicazione, String codice) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.codice = codice;
    }

    // getter
    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public String getCodice() {
        return codice;
    }

    // setter
    public void setTitolo(String titolo) {
        if (titolo == null || titolo.isEmpty()) {
            System.out.println("Titolo non valido.");
            return;
        }
        this.titolo = titolo;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        if (annoPubblicazione <= 0) {
            System.out.println("Anno non valido.");
            return;
        }
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setCodice(String codice) {
        if (codice == null || codice.isEmpty()) {
            System.out.println("Codice non valido.");
            return;
        }
        this.codice = codice;
    }

    public abstract void visualizzaDettagli();
}
