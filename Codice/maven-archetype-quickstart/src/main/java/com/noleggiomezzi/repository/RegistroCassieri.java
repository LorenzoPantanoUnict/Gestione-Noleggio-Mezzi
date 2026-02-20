package com.noleggiomezzi.repository;

import java.util.HashMap;
import java.util.Map;
import com.noleggiomezzi.model.Cassiere;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroCassieri {

    private static RegistroCassieri instance;

    private Map<String, Cassiere> mappaCassieri;

    private RegistroCassieri() {
        mappaCassieri = new HashMap<>();
        
        Cassiere admin = new Cassiere(1, "mario.rossi", "pass123", "Mario", "Rossi");
        aggiungiCassiere(admin);
    }

    public static RegistroCassieri getInstance() {
        if (instance == null) {
            instance = new RegistroCassieri();
        }
        return instance;
    }

    public void aggiungiCassiere(Cassiere c) {
        mappaCassieri.put(c.getUsername(), c);
    }

    public Cassiere findByUsername(String username) {
        return mappaCassieri.get(username);
    }

    public boolean esiste(String username) {
        return mappaCassieri.containsKey(username);
    }

    public void rimuoviCassiere(String username) {
        mappaCassieri.remove(username);
    }
}