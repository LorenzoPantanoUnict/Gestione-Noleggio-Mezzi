package com.noleggiomezzi.model;

import org.mindrot.jbcrypt.BCrypt;

public class Cassiere {
    private static int counter = 1;

    private int id;
    private String username;
    private String password; 
    private String nome;
    private String cognome;

    public Cassiere(String username, String password, String nome, String cognome) {
        this.id = counter++;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash della password
        this.nome = nome;
        this.cognome = cognome;
    }

    public boolean checkPassword(String passwordDaVerificare) {
        return BCrypt.checkpw(passwordDaVerificare, this.password);
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
}