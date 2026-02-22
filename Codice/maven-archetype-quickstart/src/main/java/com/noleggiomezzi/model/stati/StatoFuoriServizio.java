package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

public class StatoFuoriServizio implements IStatoMezzo {
    @Override public void noleggia(Mezzo m) { throw new StatoNonValidoException("Mezzo fuori servizio."); }
    @Override public void restituisci(Mezzo m) { m.setStato(new StatoDisponibile()); }
    @Override public void inviaInManutenzione(Mezzo m) { m.setStato(new StatoInManutenzione()); }
    @Override public void segnaComeRubato(Mezzo m) { m.setStato(new StatoRubato()); }
    @Override public boolean isDisponibile() { return false; }
    @Override public void impostaFuoriServizio(Mezzo m) {}
    @Override public void prenota(Mezzo mezzo) {throw new StatoNonValidoException("Mezzo fuori servizio: impossibile prenotare.");}
    @Override public String getNomeStato() { return "FUORI_SERVIZIO"; }
}