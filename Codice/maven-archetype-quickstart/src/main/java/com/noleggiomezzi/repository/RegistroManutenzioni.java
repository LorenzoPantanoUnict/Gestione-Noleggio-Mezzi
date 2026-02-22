package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.repository.interfacce.IManutenzioneRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroManutenzioni implements IManutenzioneRepository {

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