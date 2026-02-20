package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Mezzo;

public interface IMezzoRepository {

    public Mezzo getMezzoSeValido(int id) throws IllegalArgumentException;

    public Mezzo getMezzoById(int id) throws IllegalArgumentException;

    void aggiungiMezzo(Mezzo m);
}
