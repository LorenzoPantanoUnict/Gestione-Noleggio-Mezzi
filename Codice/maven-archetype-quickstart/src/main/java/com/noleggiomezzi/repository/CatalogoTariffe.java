package com.noleggiomezzi.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.model.tariffe.TariffaGiornaliera;
import com.noleggiomezzi.model.tariffe.TariffaOraria;
import com.noleggiomezzi.repository.interfacce.ITariffaRepository;

import java.util.List;
import java.util.HashMap;

@Repository
public class CatalogoTariffe implements ITariffaRepository {

    Map<String, ITariffa> tariffeDisponibili;

    public CatalogoTariffe() {
        this.tariffeDisponibili = new HashMap<String, ITariffa>();

        // Dati di prova
        this.tariffeDisponibili.put("ORARIA", new TariffaOraria());
        this.tariffeDisponibili.put("GIORNALIERA", new TariffaGiornaliera());
    }

    @Override
    public ITariffa getTariffaByName(String nome) {
        return tariffeDisponibili.get(nome);
    }

    @Override
    public List<ITariffa> findAll() {
        return tariffeDisponibili.values().stream().toList();
    }
}
