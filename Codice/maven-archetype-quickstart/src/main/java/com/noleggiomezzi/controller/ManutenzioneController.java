package com.noleggiomezzi.controller;

import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;
import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.repository.IMezzoRepository;

import java.time.LocalDateTime;

public class ManutenzioneController {

    private IMezzoRepository catalogoMezzi;


    public ManutenzioneController( IMezzoRepository catalogoMezzi) {
        this.catalogoMezzi = catalogoMezzi;
    }

    public void registraIntervento(int idMezzo, String descrizione, double costo, String nuovoStato) {
        try {
            // 1. Usiamo getMezzo() standard, perché il veicolo potrebbe essere già rotto/in manutenzione!
            Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
            
            InterventoManutenzione intervento = new InterventoManutenzione(LocalDateTime.now(), descrizione, costo);
            m.aggiungiIntervento(intervento);
            
            // 2. Yoda Conditions: previene NullPointerException se nuovoStato è null
            if ("IN_MANUTENZIONE".equals(nuovoStato)) {
                m.inviaInManutenzione();
            } else if ("DISPONIBILE".equals(nuovoStato)) {
                m.rendiDisponibile();
            }
            
            System.out.println("SUCCESSO: Manutenzione registrata. Il mezzo " + idMezzo + " è ora " + nuovoStato + ".");
            
        // 3. Catturiamo tutte le eccezioni specifiche del tuo dominio
        } catch (EnitaNonTrovataException | StatoNonValidoException | IllegalArgumentException e) {
            System.err.println("ERRORE MANUTENZIONE: " + e.getMessage());
        }
    }
}
