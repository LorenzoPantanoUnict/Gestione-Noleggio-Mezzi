package com.noleggiomezzi.service;

import com.noleggiomezzi.model.InterventoManutenzione;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.repository.interfacce.IManutenzioneRepository; // Import aggiuntivo
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
        m.inviaInManutenzione(); 
    }

    public void registraInterventoERipristina(int idMezzo, String descrizione, double costo, String statoFinale) {
        Mezzo m = catalogoMezzi.getMezzoById(idMezzo);
        
        InterventoManutenzione intervento = new InterventoManutenzione(LocalDateTime.now(), descrizione, costo);
        
        m.aggiungiIntervento(intervento);

        manutenzioneRepo.save(intervento);

        if ("DISPONIBILE".equalsIgnoreCase(statoFinale)) {
            m.rendiDisponibile(); 
        } else {
            m.impostaFuoriServizio(); 
        }
    }
}