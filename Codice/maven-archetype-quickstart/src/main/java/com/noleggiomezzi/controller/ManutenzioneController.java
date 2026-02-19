package com.noleggiomezzi.controller;

import com.noleggiomezzi.repository.RegistroManutenzioni;

public class ManutenzioneController {

    private RegistroManutenzioni registroManutenzioni;

    public ManutenzioneController(RegistroManutenzioni registroManutenzioni) {
        this.registroManutenzioni = registroManutenzioni;
    }

    public void registraIntervento(int idMezzo, String descrizione, double costo, String nuovoStato) {
        try {
            registroManutenzioni.aggiungiIntervento(idMezzo, descrizione, costo, nuovoStato);
            
            System.out.println("SUCCESSO: Manutenzione registrata. Il mezzo " + idMezzo + " è ora " + nuovoStato + ".");
            
        } catch (IllegalArgumentException e) {
            System.err.println("ERRORE MANUTENZIONE: " + e.getMessage());
        }
    }
}
