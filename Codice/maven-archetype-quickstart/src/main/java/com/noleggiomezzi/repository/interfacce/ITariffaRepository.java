package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.tariffe.ITariffa;

import java.util.List;

public interface ITariffaRepository {
    public ITariffa getTariffaByName(String nome);

    public List<ITariffa> findAll();  
}
