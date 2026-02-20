package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Prenotazione;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroPrenotazioni {
    private static RegistroPrenotazioni instance;
    private Map<Integer, Prenotazione> mappaPrenotazioni;

    private RegistroPrenotazioni() {
        mappaPrenotazioni = new HashMap<>();
    }
    
    public static RegistroPrenotazioni getInstance() {
        if (instance == null) {
            instance = new RegistroPrenotazioni();
        }
        return instance;
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        mappaPrenotazioni.put(p.getId(), p);
    }

    public Prenotazione getPrenotazione(int id) {
        return mappaPrenotazioni.get(id);
    }
}