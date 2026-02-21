package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;

public class StatoDisponibile implements IStatoMezzo {
    @Override
    public void noleggia(Mezzo mezzo) {
        mezzo.setStato(new StatoNoleggiato());
    }

    @Override
    public void restituisci(Mezzo mezzo) {} // Già disponibile

    @Override
    public void inviaInManutenzione(Mezzo mezzo) {
        mezzo.setStato(new StatoInManutenzione());
    }

    @Override
    public void segnaComeRubato(Mezzo mezzo) {
        mezzo.setStato(new StatoRubato());
    }

    @Override
    public boolean isDisponibile() { return true; }
    
    @Override public void impostaFuoriServizio(Mezzo m) { m.setStato(new StatoFuoriServizio()); }

    @Override
    public String getNomeStato() { return "DISPONIBILE"; }

}