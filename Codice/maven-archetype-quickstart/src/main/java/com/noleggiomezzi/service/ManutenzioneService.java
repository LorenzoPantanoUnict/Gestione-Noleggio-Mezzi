package com.noleggiomezzi.service;

import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class ManutenzioneService {

    private final IMezzoRepository catalogoMezzi;

    public ManutenzioneService(IMezzoRepository catalogoMezzi) {
        this.catalogoMezzi = catalogoMezzi;
    }

    public void inviaInRiparazione(int idMezzo) {
        Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
        m.inviaInManutenzione(); // Cambia lo stato in IN_MANUTENZIONE
    }

    public void registraInterventoERipristina(int idMezzo, String descrizione, double costo, String statoFinale) {
        Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
        
        InterventoManutenzione intervento = new InterventoManutenzione(LocalDateTime.now(), descrizione, costo);
        m.aggiungiIntervento(intervento);

        if ("DISPONIBILE".equalsIgnoreCase(statoFinale)) {
            m.rendiDisponibile(); 
        } else {
            m.impostaFuoriServizio(); 
        }
    }
}