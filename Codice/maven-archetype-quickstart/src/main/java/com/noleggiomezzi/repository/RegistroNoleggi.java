package com.noleggiomezzi.repository;

import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.model.Noleggio;

import java.util.HashMap;

public class RegistroNoleggi {

    private HashMap<Integer, Noleggio> mappa = new HashMap<>();


    public Noleggio getNoleggio(int id) {
        
        Noleggio n = mappa.get(id);
        
        if (n == null) {
            throw new EnitaNonTrovataException("Noleggio con id " + id + " non trovato");
        }

        return n;
    }

    public void aggiungiNoleggio(Noleggio n) {
        mappa.put(n.getId(), n);
    }
}