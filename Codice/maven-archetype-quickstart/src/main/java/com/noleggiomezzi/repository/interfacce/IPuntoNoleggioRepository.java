package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.PuntoNoleggio;
import java.util.List;

public interface IPuntoNoleggioRepository {
    PuntoNoleggio getPuntoById(int id);
    List<PuntoNoleggio> findAll();
}