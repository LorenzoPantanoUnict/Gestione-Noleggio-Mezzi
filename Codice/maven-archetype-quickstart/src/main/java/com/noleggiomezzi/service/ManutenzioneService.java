package com.noleggiomezzi.service;

import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.repository.interfacce.IManutenzioneRepository;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class ManutenzioneService {

    private final IMezzoRepository catalogoMezzi;
    private final IManutenzioneRepository manutenzioneRepo; 

    public ManutenzioneService(IMezzoRepository catalogoMezzi, IManutenzioneRepository manutenzioneRepo) {
        this.catalogoMezzi = catalogoMezzi;
        this.manutenzioneRepo = manutenzioneRepo;
    }

    public void inviaInRiparazione(int idMezzo) {
        Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
        
        // Non servono più IF qui. Se il mezzo è NOLEGGIATO o RUBATO,
        // m.inviaInManutenzione() delegherà allo stato che lancerà l'eccezione corretta.
        m.inviaInManutenzione(); 
    }

    public void registraInterventoERipristina(int idMezzo, String descrizione, double costo, String statoFinale) {
        Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
        
        // Creazione e salvataggio dell'intervento
        InterventoManutenzione intervento = new InterventoManutenzione(LocalDateTime.now(), descrizione, costo);
        m.aggiungiIntervento(intervento);
        manutenzioneRepo.save(intervento);

        // Gestione del ripristino tramite metodi del Pattern State
        if ("DISPONIBILE".equalsIgnoreCase(statoFinale)) {
            m.rendiDisponibile(); 
        } else {
            // Se avevi previsto uno stato FUORI_SERVIZIO, chiamiamo il metodo dedicato.
            // Altrimenti, se non ripristinato, rimane implicitamente in manutenzione o fuori servizio.
            m.impostaFuoriServizio(); 
        }
    }
}