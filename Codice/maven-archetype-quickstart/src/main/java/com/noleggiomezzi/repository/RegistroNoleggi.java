package com.noleggiomezzi.repository;

import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.repository.interfacce.INoleggioRepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class RegistroNoleggi implements INoleggioRepository {

    private HashMap<Integer, Noleggio> mappa = new HashMap<>();


    public Noleggio getNoleggioById(int id) {
        
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