package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Mezzo;
import java.util.HashMap;

public class CatalogoMezzi {

    private HashMap<Integer, Mezzo> mappa = new HashMap<>();

    public Mezzo getMezzo(int id) {
        return mappa.get(id);
    }

    public void aggiungiMezzo(Mezzo m) {
        mappa.put(m.getId(), m);
    }
}