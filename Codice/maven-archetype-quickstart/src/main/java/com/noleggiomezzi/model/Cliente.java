package com.noleggiomezzi.model;
public class Cliente {

    private static int counter = 1;
    private int id;
    private String nome;
    private String cognome;
    private int affidabilita;
    private String email;
    private double credito;

    private static final int AFFIDABILITA_DEFAULT = 1;
    private static final double CREDITO_DEFAULT = 0.0;

    //Costruttori 

    public Cliente( String nome, String cognome, String email) {
        this.id = counter++;
        this.nome = nome;
        this.cognome = cognome;
        this.email= email;
        this.affidabilita = AFFIDABILITA_DEFAULT;
        this.credito = CREDITO_DEFAULT;
    }

    // Metodi
    
    public boolean addebbitaImporto(double importo) {
        if (credito >= importo) {
            credito -= importo;
            return true;
        }
        return false;
    }

    public void aggiungiCredito(double importo) {
        credito += importo;
    }

    public boolean isAffidabile() {
        return affidabilita > 0;
    }

    public void sospendiAccount() {
        affidabilita = 0;
    }

    // Getters

    public String getDati() {
        return nome + " " + cognome + " (" + email + ")";
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }
    
    public void riattivaAccount() {
        affidabilita = 1;
    }

    //Setters
    
}