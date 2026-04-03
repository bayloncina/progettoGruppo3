package model;

public class Rivista extends Risorsa {
    private int numero;

    public Rivista(String titolo, int annoPubblicazione, String codice, int numero) {
        super(titolo, annoPubblicazione, codice);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            System.out.println("Numero rivista non valido.");
            return;
        }
        this.numero = numero;
    }

    @Override
    public void visualizzaDettagli() {
        System.out.println("Rivista | Titolo: " + getTitolo() +
                " | Anno: " + getAnnoPubblicazione() +
                " | Codice: " + getCodice() +
                " | Numero: " + numero);
    }

}