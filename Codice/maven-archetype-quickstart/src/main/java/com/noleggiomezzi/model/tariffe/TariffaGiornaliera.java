package com.noleggiomezzi.model.tariffe;


public class TariffaGiornaliera implements ITariffa {

    private double costoGiorno;

    public TariffaGiornaliera(double costoGiorno) {
        this.costoGiorno = costoGiorno;
    }

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return costoGiorno;
    }

    @Override
    public String getNome() {
        return "Giornaliera";
    }
}