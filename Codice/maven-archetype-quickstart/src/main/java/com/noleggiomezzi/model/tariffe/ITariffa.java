package com.noleggiomezzi.model;


public interface ITariffa {

    double calcolaCosto(int durataMinuti, int km);
    String getNome();
}