package model;

public class Libro extends Risorsa {
    private String autore;

    public Libro(String titolo, int annoPubblicazione, String codice, String autore) {
        super(titolo, annoPubblicazione, codice);
        this.autore = autore;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        if (autore == null || autore.isEmpty()) {
            System.out.println("Autore non valido.");
            return;
        }
        this.autore = autore;
    }

    @Override
    public void visualizzaDettagli() {
        System.out.println("Libro | Titolo: " + getTitolo() +
                " | Anno: " + getAnnoPubblicazione() +
                " | Codice: " + getCodice() +
                " | Autore: " + autore);
    }

}