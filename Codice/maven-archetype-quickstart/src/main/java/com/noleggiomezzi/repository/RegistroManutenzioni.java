package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.InterventoManutenzione;

import java.util.ArrayList;
import java.util.List;

public class RegistroManutenzioni implements IManutenzioneRepository {
    
    // Il tuo "database" in memoria
    private List<InterventoManutenzione> tabellaManutenzioni = new ArrayList<>();

    @Override
    public void save(InterventoManutenzione intervento) {
        tabellaManutenzioni.add(intervento);
    }

    @Override
    public List<InterventoManutenzione> findAll() {
        return tabellaManutenzioni;
    }

}