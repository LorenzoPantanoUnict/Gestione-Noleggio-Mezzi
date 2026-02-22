package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.TipoMezzo;

import java.util.List;

public interface ICatalogoTipoMezzo {

    public TipoMezzo getTipoMezzo(String nome);
    public List<TipoMezzo> findAll();
    public void aggiungiTipoMezzo(TipoMezzo TipoMezzo);
}
