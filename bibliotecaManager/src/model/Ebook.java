package model;

public class Ebook extends Risorsa {
    private String formato; // es. PDF, EPUB

    public Ebook(String titolo, int annoPubblicazione, String codice, String formato) {
        super(titolo, annoPubblicazione, codice);
        this.formato = formato;
    }

    public String getFormato() { return formato; }
    public void setFormato(String formato) {
        if (formato == null || formato.isEmpty()) {
            System.out.println("Formato non valido.");
            return;
        }
        this.formato = formato;
    }

    @Override
    public void visualizzaDettagli() {
        System.out.println("Ebook | Titolo: " + getTitolo() +
                           " | Anno: " + getAnnoPubblicazione() +
                           " | Codice: " + getCodice() +
                           " | Formato: " + formato);
    }

}
