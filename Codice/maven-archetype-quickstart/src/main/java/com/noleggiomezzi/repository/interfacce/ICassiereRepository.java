package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.Cassiere;
import java.util.List;

public interface ICassiereRepository {
    void aggiungiCassiere(Cassiere c);
    Cassiere findByUsername(String username);
    boolean esiste(String username);
    void rimuoviCassiere(String username);
    List<Cassiere> findAll();
}