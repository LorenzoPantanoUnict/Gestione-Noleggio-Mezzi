package com.noleggiomezzi.model.tariffe;

public abstract class TariffaDecorator implements ITariffa {
    protected ITariffa tariffaBase;

    public TariffaDecorator(ITariffa tariffaBase) {
        this.tariffaBase = tariffaBase;
    }

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return tariffaBase.calcolaCosto(durataMinuti, km);
    }
    
    @Override
    public String getNome() {
        return tariffaBase.getNome();
    }
}