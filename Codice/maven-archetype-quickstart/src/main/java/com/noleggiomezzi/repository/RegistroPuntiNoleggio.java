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

    @Override
    public void aggiungiPunto(PuntoNoleggio punto) {
        sedi.add(punto);
    }
}