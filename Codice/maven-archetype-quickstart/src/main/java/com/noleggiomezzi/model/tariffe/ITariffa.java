package com.noleggiomezzi.model.tariffe;


public interface ITariffa {

    double calcolaCosto(int durataMinuti, int km);
    String getNome();
}