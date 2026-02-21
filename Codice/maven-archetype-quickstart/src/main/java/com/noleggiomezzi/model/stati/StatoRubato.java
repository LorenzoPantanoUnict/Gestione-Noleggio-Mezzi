package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

public class StatoRubato implements IStatoMezzo {
    @Override public void noleggia(Mezzo m) { throw new StatoNonValidoException("Mezzo rubato!"); }
    @Override public void restituisci(Mezzo m) { throw new StatoNonValidoException("Mezzo rubato!"); }
    @Override public void inviaInManutenzione(Mezzo m) { throw new StatoNonValidoException("Mezzo rubato!"); }
    @Override public void segnaComeRubato(Mezzo m) {}
    @Override public boolean isDisponibile() { return false; }
    @Override public void impostaFuoriServizio(Mezzo m) { throw new StatoNonValidoException("Mezzo rubato"); }
    @Override public String getNomeStato() { return "RUBATO"; }
}