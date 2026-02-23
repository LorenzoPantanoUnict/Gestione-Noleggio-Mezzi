package com.noleggiomezzi.service;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.*;
import com.noleggiomezzi.utility.DateRange;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    private final MezzoService mezzoService; 
    private final CatalogoMezzi catalogoMezzi;
    private final RegistroPrenotazioni registroPrenotazioni;
    private final RegistroPuntiNoleggio registroSedi;
    private final CatalogoTariffe catalogoTariffe;
    private final CatalogoTipoMezzi catalogoTipiMezzo;

    public PrenotazioneService(MezzoService mezzoService,
                               CatalogoMezzi catalogoMezzi,
                               RegistroPrenotazioni registroPrenotazioni, 
                               RegistroPuntiNoleggio registroSedi, 
                               CatalogoTariffe catalogoTariffe,
                               CatalogoTipoMezzi catalogoTipiMezzo) {
        this.mezzoService = mezzoService;
        this.catalogoMezzi = catalogoMezzi;
        this.registroPrenotazioni = registroPrenotazioni;
        this.registroSedi = registroSedi;
        this.catalogoTariffe = catalogoTariffe;
        this.catalogoTipiMezzo = catalogoTipiMezzo;
    }

    // --- Metodi per fornire dati all'interfaccia ---
    public List<PuntoNoleggio> getSediDisponibili() {
        return registroSedi.findAll();
    }

    public List<TipoMezzo> getTipiMezzo() {
        return catalogoTipiMezzo.findAll();
    }

    public List<ITariffa> getTariffeDisponibili() {
        return catalogoTariffe.findAll();
    }

    // --- Metodi di logica di business ---
    public List<Mezzo> cercaMezziDisponibili(String tipoNome, DateRange periodo, int sedeId) {
        TipoMezzo tipo = catalogoTipiMezzo.getTipoMezzo(tipoNome);
        return mezzoService.verificaDisponibilitaCompleta(tipo, periodo, sedeId);
    }

    public String elaboraPrenotazione(Cliente cliente, int mezzoId, int sedeId, String tariffaNome, DateRange periodo) {
        if (!cliente.isAffidabile()) {
            throw new StatoNonValidoException("Cliente non affidabile. Prenotazione rifiutata.");
        }

        // Recupero le entità necessarie
        Mezzo m = catalogoMezzi.getMezzoById(mezzoId);
        PuntoNoleggio sede = registroSedi.getPuntoById(sedeId);
        ITariffa tariffa = catalogoTariffe.getTariffaByName(tariffaNome);

        if (tariffa == null) {
            throw new IllegalArgumentException("Errore critico: la tariffa selezionata non esiste nel catalogo.");
        }

        // Creazione e salvataggio
        Prenotazione nuova = new Prenotazione(cliente, m, periodo, sede, tariffa);
        registroPrenotazioni.aggiungiPrenotazione(nuova);
        
        // Cambio di stato
        m.prenota(); 
        
        return nuova.getPnr();
    }
}