package com.noleggiomezzi.repository;

import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.repository.interfacce.INoleggioRepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Repository
public class RegistroNoleggi implements INoleggioRepository {

    private HashMap<Integer, Noleggio> mappa;

    public RegistroNoleggi(){
        this.mappa = new HashMap<>();

    }

    @Override
    public Noleggio getNoleggioById(int id) {
        
        Noleggio n = mappa.get(id);
        
        if (n == null) {
            throw new EnitaNonTrovataException("Noleggio con id " + id + " non trovato");
        }

        return n;
    }

    @Override
    public void aggiungiNoleggio(Noleggio n) {
        mappa.put(n.getId(), n);
    }

    @Override
    public List<Noleggio> findAll() {
        return new ArrayList<>(mappa.values());
    }   
}