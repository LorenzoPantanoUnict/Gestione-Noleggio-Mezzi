package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.TipoMezzo;

import java.util.Map;


public class CatalogoTipoMezzi {

    private Map<String, TipoMezzo> mappaTipiMezzi;

    public CatalogoTipoMezzi() { }

    public TipoMezzo getTipoMezzo(String nome) {
        return mappaTipiMezzi.get(nome);
    }
}
