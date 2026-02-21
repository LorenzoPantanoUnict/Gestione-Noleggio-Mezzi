package com.noleggiomezzi.repository;

import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;

import com.noleggiomezzi.model.PuntoNoleggio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroPuntiNoleggio implements IPuntoNoleggioRepository {
    private List<PuntoNoleggio> sedi;

    public RegistroPuntiNoleggio() {
        this.sedi = new ArrayList<>();
        sedi.add(new PuntoNoleggio(1, "Sede Centrale", "Via Roma 1, Milano", 50));
        sedi.add(new PuntoNoleggio(2, "Aeroporto", "Terminal 1, Malpensa", 100));
    }

    @Override
    public PuntoNoleggio getPuntoById(int id) {
        return sedi.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Punto Noleggio non trovato"));
    }

    @Override
    public List<PuntoNoleggio> findAll() {
        return sedi;
    }
}