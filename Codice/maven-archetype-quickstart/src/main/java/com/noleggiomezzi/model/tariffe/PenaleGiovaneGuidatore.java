package com.noleggiomezzi.model.tariffe;

public class PenaleGiovaneGuidatore extends TariffaDecorator {
    private final double sovrapprezzoGiovane = 15.0;

    public PenaleGiovaneGuidatore(ITariffa tariffaBase) {
        super(tariffaBase);
    }

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return super.calcolaCosto(durataMinuti, km) + sovrapprezzoGiovane;
    }

    @Override
    public String getNome() {
        return super.getNome() + " + Penale Giovane Guidatore";
    }
}