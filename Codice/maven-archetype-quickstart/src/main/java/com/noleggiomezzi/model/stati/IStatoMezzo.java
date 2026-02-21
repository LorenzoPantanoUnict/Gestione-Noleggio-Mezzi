package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;

public interface IStatoMezzo {
    void noleggia(Mezzo mezzo);
    void restituisci(Mezzo mezzo);
    void inviaInManutenzione(Mezzo mezzo);
    void segnaComeRubato(Mezzo mezzo);
    boolean isDisponibile();
    String getNomeStato();
    void impostaFuoriServizio(Mezzo mezzo);
}