package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.StatoMezzo;
import java.time.LocalDateTime;

public class RegistroManutenzioni {

    private CatalogoMezzi catalogoMezzi;

    public RegistroManutenzioni(CatalogoMezzi catalogoMezzi) {
        this.catalogoMezzi = catalogoMezzi;
    }

    public void aggiungiIntervento(int idMezzo, String descrizione, double costo, String nuovoStato) {
        
        Mezzo m = catalogoMezzi.getMezzo(idMezzo);
        
        if (m == null) {
            throw new IllegalArgumentException("Errore: Mezzo non trovato nel catalogo.");
        }

        InterventoManutenzione intervento = new InterventoManutenzione(LocalDateTime.now(), descrizione, costo);

        m.aggiungiIntervento(intervento);

        StatoMezzo statoEnum = StatoMezzo.valueOf(nuovoStato.toUpperCase());
        m.setStato(statoEnum);
    }
}