package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Noleggio;

public interface INoleggioRepository {

    void aggiungiNoleggio(Noleggio n);

    Noleggio getNoleggioById(int id);
}
