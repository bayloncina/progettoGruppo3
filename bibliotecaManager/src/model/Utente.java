package model;

public class Utente {
    private String nome;
    private String idUtente;

    public Utente(String nome, String idUtente) {
        this.nome = nome;
        this.idUtente = idUtente;
    }

    public String getNome() { return nome; }
    public String getIdUtente() { return idUtente; }

    public void setNome(String nome) { this.nome = nome; }
}