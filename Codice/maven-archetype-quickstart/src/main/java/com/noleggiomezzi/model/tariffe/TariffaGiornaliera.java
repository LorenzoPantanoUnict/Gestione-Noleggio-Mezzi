package com.noleggiomezzi.model.tariffe;


public class TariffaGiornaliera implements ITariffa {

    private double costoGiorno = 20.0;

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return costoGiorno;
    }

    @Override
    public String getNome() {
        return "Giornaliera";
    }
}