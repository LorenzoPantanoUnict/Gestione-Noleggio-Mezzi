package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

public class StatoNoleggiato implements IStatoMezzo {
    @Override
    public void noleggia(Mezzo mezzo) {
        throw new StatoNonValidoException("Il mezzo è già noleggiato.");
    }

    @Override
    public void restituisci(Mezzo mezzo) {
        mezzo.setStato(new StatoDisponibile());
    }

    @Override
    public void inviaInManutenzione(Mezzo mezzo) {
        throw new StatoNonValidoException("Impossibile mandare in riparazione un mezzo attualmente in uso.");
    }

    @Override public void impostaFuoriServizio(Mezzo m) { 
        throw new StatoNonValidoException("Impossibile mettere fuori servizio un mezzo attualmente noleggiato."); 
    }

    @Override
    public void segnaComeRubato(Mezzo mezzo) {
        mezzo.setStato(new StatoRubato());
    }

    @Override
    public boolean isDisponibile() { return false; }

    @Override
    public String getNomeStato() { return "NOLEGGIATO"; }
}