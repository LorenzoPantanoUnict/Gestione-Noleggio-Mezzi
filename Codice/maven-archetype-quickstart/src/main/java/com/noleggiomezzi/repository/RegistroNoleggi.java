package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.tariffe.ITariffa;


import java.util.HashMap;


public class RegistroNoleggi {

    private HashMap<Integer, Noleggio> mappa = new HashMap<>();

    public Noleggio creaNoleggio( Cliente c, Mezzo m, ITariffa t, PuntoNoleggio p) {

        if(c == null || m == null || t == null || p == null) {
            throw new RuntimeException("Dati mancanti per creare noleggio");
        }

        Noleggio n = new Noleggio(c, m, t, p);

        mappa.put(n.getId(), n);

        return n;
    }

    public Noleggio getNoleggio(int id) {
        return mappa.get(id);
    }
}