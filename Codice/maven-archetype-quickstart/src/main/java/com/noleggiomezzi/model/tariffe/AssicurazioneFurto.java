package com.noleggiomezzi.model.tariffe;

public class AssicurazioneFurto extends TariffaDecorator {
    private final double costoAssicurazione = 20.0;

    public AssicurazioneFurto(ITariffa tariffaBase) {
        super(tariffaBase);
    }

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        // Aggiunge il costo dell'assicurazione al costo della tariffa base
        return super.calcolaCosto(durataMinuti, km) + costoAssicurazione;
    }

    @Override
    public String getNome() {
        return super.getNome() + " + Assicurazione Furto";
    }
}