package com.noleggiomezzi.repository.interfacce;

import java.util.List;

import com.noleggiomezzi.model.Noleggio;

public interface INoleggioRepository {

    void aggiungiNoleggio(Noleggio n);

    Noleggio getNoleggioById(int id);

    List<Noleggio> findAll();
}
