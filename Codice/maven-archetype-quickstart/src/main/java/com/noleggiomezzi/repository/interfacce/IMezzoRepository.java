package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.Mezzo;

import java.util.List;

public interface IMezzoRepository {

    public Mezzo getMezzoSeValido(int id) throws IllegalArgumentException;

    public Mezzo getMezzoById(int id) throws IllegalArgumentException;

    void aggiungiMezzo(Mezzo m);

    boolean esisteMezzo(int id);

    List<Mezzo> findMezziPerSedeETipo(int sedeId, String nomeTipo);

    List<Mezzo> findMezziDisponibiliFisicamente(int sedeId, String nomeTipo);

    List<Mezzo> findAll();
}
