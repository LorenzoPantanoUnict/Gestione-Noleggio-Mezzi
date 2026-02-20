package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.TipoMezzo;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository 
public class CatalogoTipoMezzi {

    private Map<String, TipoMezzo> mappaTipiMezzi;

    public CatalogoTipoMezzi() { }

    public TipoMezzo getTipoMezzo(String nome) {
        return mappaTipiMezzi.get(nome);
    }
}
