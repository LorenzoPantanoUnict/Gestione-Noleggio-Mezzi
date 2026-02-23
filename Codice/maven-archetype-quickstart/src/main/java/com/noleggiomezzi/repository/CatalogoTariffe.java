package com.noleggiomezzi.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.interfacce.ITariffaRepository;

import java.util.List;
import java.util.HashMap;

@Repository
public class CatalogoTariffe implements ITariffaRepository {

    Map<String, ITariffa> mappaTariffe;

    public CatalogoTariffe() {
        this.mappaTariffe = new HashMap<String, ITariffa>();
    }

    @Override
    public ITariffa getTariffaByName(String nomeRichiesto) {
    if (nomeRichiesto == null) return null;

    return mappaTariffe.values().stream()
            .filter(t -> t.getNome().equalsIgnoreCase(nomeRichiesto))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Tariffa non trovata: " + nomeRichiesto)); 
}

    @Override
    public List<ITariffa> findAll() {
        return mappaTariffe.values().stream().toList();
    }

    @Override
    public void aggiungiTariffa(String nomeTariffa, ITariffa t){
        mappaTariffe.put(nomeTariffa, t);
    }
}
