package com.noleggiomezzi.model;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

public class Cliente {

    private static int counter = 1;
    private int id;
    private String nome;
    private String cognome;
    private int affidabilita;
    private String email;
    private double credito;
    private String password;

    private static final int AFFIDABILITA_DEFAULT = 1;
    private static final double CREDITO_DEFAULT = 200.0;

    //Costruttore
    public Cliente( String nome, String cognome, String email, String password) {

        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email non valida");
        }
        
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email non valida");
        }

        this.id = counter++;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;

        this.affidabilita = AFFIDABILITA_DEFAULT;
        this.credito = CREDITO_DEFAULT;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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

    public boolean checkPassword(String password){
        return BCrypt.checkpw(password, this.password);
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

    public String getPasswordHash(){
        return this.password;
    }

    //Setters
    
}