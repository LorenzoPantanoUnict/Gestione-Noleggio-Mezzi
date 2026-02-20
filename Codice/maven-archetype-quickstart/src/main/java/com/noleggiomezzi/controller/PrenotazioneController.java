package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.RegistroPrenotazioni;
import com.noleggiomezzi.exceptions.NessunaDisponibilitaException;
import java.util.List;

public class PrenotazioneController {

    private CatalogoMezzi catalogoMezzi;
    private RegistroPrenotazioni registroPrenotazioni;

    public PrenotazioneController(CatalogoMezzi catalogoMezzi) {
        this.catalogoMezzi = catalogoMezzi;
        this.registroPrenotazioni = RegistroPrenotazioni.getInstance();
    }

    public List<Mezzo> cercaDisponibilita(TipoMezzo tipo, DateRange periodo, int idPuntoNoleggio) {
        
        List<Mezzo> mezziLiberi = catalogoMezzi.verificaDisponibilita(tipo, periodo, idPuntoNoleggio);
        
        if (mezziLiberi.isEmpty()) {
            throw new NessunaDisponibilitaException("Nessun veicolo disponibile per le date e la sede selezionate.");
        }
        
        return mezziLiberi; 
    }

    public String creaNuovaPrenotazione(Cliente cliente, Mezzo mezzo, DateRange periodo, PuntoNoleggio sede) {
        
        Prenotazione nuovaPrenotazione = new Prenotazione(cliente, mezzo, periodo, sede);
        
        registroPrenotazioni.aggiungiPrenotazione(nuovaPrenotazione);

        mezzo.noleggia();
        
        System.out.println("SUCCESSO: Prenotazione Confermata!");
        System.out.println("- Codice PNR: " + nuovaPrenotazione.getPnr());
        System.out.println("- Costo stimato: €" + nuovaPrenotazione.getCostoTotale());
        
        return nuovaPrenotazione.getPnr();
    }
}