package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

public class StatoInManutenzione implements IStatoMezzo {
    @Override
    public void noleggia(Mezzo mezzo) {
        throw new StatoNonValidoException("Mezzo in officina: noleggio non consentito.");
    }

    @Override
    public void restituisci(Mezzo mezzo) {
        mezzo.setStato(new StatoDisponibile());
    }

    @Override
    public void inviaInManutenzione(Mezzo mezzo) {}

    @Override
    public void segnaComeRubato(Mezzo mezzo) {
        mezzo.setStato(new StatoRubato());
    }

    @Override public void impostaFuoriServizio(Mezzo m) { m.setStato(new StatoFuoriServizio()); }

    @Override
    public boolean isDisponibile() { return false; }

    @Override
    public void prenota(Mezzo mezzo) {
        throw new StatoNonValidoException("Mezzo in manutenzione: prenotazione non consentita finché non torna disponibile.");
    }

    @Override
    public String getNomeStato() { return "IN_MANUTENZIONE"; }
}