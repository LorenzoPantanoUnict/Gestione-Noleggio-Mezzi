package com.noleggiomezzi.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.noleggiomezzi.model.Cassiere;
import com.noleggiomezzi.repository.interfacce.ICassiereRepository;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroCassieri implements ICassiereRepository {

    private Map<String, Cassiere> mappaCassieri;

    public RegistroCassieri() {
        this.mappaCassieri = new HashMap<>();
    }

    @Override
    public void aggiungiCassiere(Cassiere c) {
        mappaCassieri.put(c.getUsername(), c);
    }

    @Override
    public Cassiere findByUsername(String username) {
        return mappaCassieri.get(username);
    }

    @Override
    public boolean esiste(String username) {
        return mappaCassieri.containsKey(username);
    }

    @Override
    public void rimuoviCassiere(String username) {
        mappaCassieri.remove(username);
    }

    @Override
    public List<Cassiere> findAll() {
        return new ArrayList<>(mappaCassieri.values());
    }
}