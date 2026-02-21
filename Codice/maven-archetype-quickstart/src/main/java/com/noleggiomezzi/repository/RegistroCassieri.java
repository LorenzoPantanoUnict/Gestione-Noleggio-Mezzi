package com.noleggiomezzi.repository;

import java.util.HashMap;
import java.util.Map;
import com.noleggiomezzi.model.Cassiere;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroCassieri {

    private Map<String, Cassiere> mappaCassieri;

    public RegistroCassieri() {
        this.mappaCassieri = new HashMap<>();
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