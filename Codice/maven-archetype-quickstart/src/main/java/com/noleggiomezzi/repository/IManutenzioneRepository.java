package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.InterventoManutenzione;

import java.util.List;

public interface IManutenzioneRepository {
    void save(InterventoManutenzione intervento);
    List<InterventoManutenzione> findAll();
}
