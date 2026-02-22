package com.noleggiomezzi.model.stati;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

public class StatoPrenotato implements IStatoMezzo {
    
    @Override
    public void noleggia(Mezzo mezzo) {
        mezzo.setStato(new StatoNoleggiato());
    }

    @Override
    public void restituisci(Mezzo mezzo) {
        throw new StatoNonValidoException("Un mezzo prenotato non può essere restituito senza noleggio.");
    }

    @Override
    public void inviaInManutenzione(Mezzo mezzo) {
        throw new StatoNonValidoException("Impossibile inviare in manutenzione: il mezzo è riservato per un cliente.");
    }

    @Override
    public void segnaComeRubato(Mezzo mezzo) {
        mezzo.setStato(new StatoRubato());
    }

    @Override
    public void impostaFuoriServizio(Mezzo mezzo) {
        mezzo.setStato(new StatoFuoriServizio());
    }

    @Override
    public boolean isDisponibile() {
        return false; 
    }

    @Override
    public void prenota(Mezzo mezzo) {
        throw new StatoNonValidoException("Il mezzo è già stato prenotato da un altro utente.");
    }

    @Override
    public String getNomeStato() {
        return "PRENOTATO";
    }
}