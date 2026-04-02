package model;

import java.util.ArrayList;

public class Utente {

    private String nome;
    private String idUtente;
    private ArrayList<Risorsa> risorseInPrestito;

    public Utente(String nome, String idUtente) {
        this.nome = nome;
        this.idUtente = idUtente;
        this.risorseInPrestito = new ArrayList<>();
    }

    // getter
    public String getNome() { return nome; }
    public String getIdUtente() { return idUtente; }
    public ArrayList<Risorsa> getRisorseInPrestito() { return risorseInPrestito; }

    // setter
    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Nome non valido.");
            return;
        }
        this.nome = nome;
    }

    public void setIdUtente(String idUtente) {
        if (idUtente == null || idUtente.isEmpty()) {
            System.out.println("ID non valido.");
            return;
        }
        this.idUtente = idUtente;
    }

    // metodi prestito
    public void prendiInPrestito(Risorsa r) {
        risorseInPrestito.add(r);
        System.out.println("Risorsa '" + r.getTitolo() + "' aggiunta al prestito di " + nome + ".");
    }

    public void restituisci(Risorsa r) {
        if (risorseInPrestito.remove(r)) {
            System.out.println("Risorsa '" + r.getTitolo() + "' restituita da " + nome + ".");
        } else {
            System.out.println("Risorsa non trovata nel prestito di " + nome + ".");
        }
    }

    public void stampaRisorse() {
        if (risorseInPrestito.isEmpty()) {
            System.out.println(nome + " non ha risorse in prestito.");
            return;
        }
        System.out.println("Risorse in prestito di " + nome + ":");
        for (Risorsa r : risorseInPrestito) {
            r.visualizzaDettagli();
        }
    }
}