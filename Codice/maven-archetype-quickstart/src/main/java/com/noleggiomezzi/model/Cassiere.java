package com.noleggiomezzi.model;

public class Cassiere {
    private int id;
    private String username;
    private String password;
    private String nome;
    private String cognome;

    public Cassiere(int id, String username, String password, String nome, String cognome) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public boolean checkPassword(String passwordInserita) {
        System.out.println("[CASSIERE] Controllo la password per l'utente " + this.username + "...");
        return this.password.equals(passwordInserita);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
}