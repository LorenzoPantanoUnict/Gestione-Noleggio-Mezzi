package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Prenotazione;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RegistroPrenotazioni {
    private Map<Integer, Prenotazione> mappaPrenotazioni;
    
    public RegistroPrenotazioni() {
        this.mappaPrenotazioni = new HashMap<>();
    }
    
    public void aggiungiPrenotazione(Prenotazione p) {
        mappaPrenotazioni.put(p.getId(), p);
    }

    public Prenotazione getPrenotazione(int id) {
        return mappaPrenotazioni.get(id);
    }
}