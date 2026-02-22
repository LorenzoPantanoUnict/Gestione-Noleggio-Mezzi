package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.interfacce.ICatalogoTipoMezzo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository 
public class CatalogoTipoMezzi implements ICatalogoTipoMezzo {

    private Map<String, TipoMezzo> mappaTipiMezzi;

    public CatalogoTipoMezzi() { 
        this.mappaTipiMezzi = new HashMap<>();
        
    }

    public TipoMezzo getTipoMezzo(String nome) {
        return mappaTipiMezzi.get(nome);
    }
}