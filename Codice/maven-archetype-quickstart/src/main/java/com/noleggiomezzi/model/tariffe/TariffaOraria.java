package com.noleggiomezzi.model.tariffe;


public class TariffaOraria implements ITariffa {

    private double costoOra;

    public TariffaOraria(double costo){
        this.costoOra = costo;
    }

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return (durataMinuti / 60.0) * costoOra;
    }

    @Override
    public String getNome() {
        return "Oraria";
    }

    public String getDescrizione(){
        return "Tariffa basata sulla durata del noleggio " + costoOra + " euro all'ora";
    }
}