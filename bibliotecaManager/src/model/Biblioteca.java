package model;

import java.util.ArrayList;

public class Biblioteca {
    private String nomeBiblioteca;
    private ArrayList<Risorsa> risorseDisponibili;
    private ArrayList<Utente> utenti;

    public Biblioteca(String nomeBiblioteca) {
        this.nomeBiblioteca = nomeBiblioteca;
        this.risorseDisponibili = new ArrayList<>();
        this.utenti = new ArrayList<>();
    }

    // getter
    public String getNomeBiblioteca() { return nomeBiblioteca; }
    public ArrayList<Risorsa> getRisorseDisponibili() { return risorseDisponibili; }
    public ArrayList<Utente> getUtenti() { return utenti; }

    // setter
    public void setNomeBiblioteca(String nomeBiblioteca) {
        if (nomeBiblioteca == null || nomeBiblioteca.isEmpty()) {
            System.out.println("Nome non valido.");
            return;
        }
        this.nomeBiblioteca = nomeBiblioteca;
    }
}