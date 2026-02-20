package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.InterventoManutenzione;

import java.util.List;

public interface IManutenzioneRepository {
    void save(InterventoManutenzione intervento);
    List<InterventoManutenzione> findAll();
}
