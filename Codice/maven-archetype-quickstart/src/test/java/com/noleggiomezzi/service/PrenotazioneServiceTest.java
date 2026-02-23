package com.noleggiomezzi.service;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.tariffe.TariffaOraria;
import com.noleggiomezzi.repository.*;
import com.noleggiomezzi.utility.DateRange;
import com.noleggiomezzi.utility.MezzoBuilder;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class PrenotazioneServiceTest {

    private PrenotazioneService prenotazioneService;
    private CatalogoMezzi catalogoMezzi;
    private RegistroPrenotazioni registroPrenotazioni;
    private RegistroPuntiNoleggio registroSedi;
    private CatalogoTariffe catalogoTariffe;

    @BeforeEach
    void setUp() {
        // Inizializzazione repository con dati di test
        CatalogoTipoMezzi tipiRepo = new CatalogoTipoMezzi();
        catalogoMezzi = new CatalogoMezzi(tipiRepo);
        registroPrenotazioni = new RegistroPrenotazioni();
        registroSedi = new RegistroPuntiNoleggio();
        catalogoTariffe = new CatalogoTariffe();

        MezzoService mezzoService = new MezzoService(catalogoMezzi, registroPrenotazioni, registroSedi, tipiRepo);
        prenotazioneService = new PrenotazioneService(mezzoService, catalogoMezzi, registroPrenotazioni, registroSedi, catalogoTariffe, tipiRepo);

        PuntoNoleggio sede = new PuntoNoleggio(1, "Sede Roma", "Via Roma", 10);
        registroSedi.aggiungiPunto(sede);

        TipoMezzo auto = new TipoMezzo("CityCar", false, true, 1.0);
        tipiRepo.aggiungiTipoMezzo(auto);

        catalogoTariffe.aggiungiTariffa("ORARIA", new TariffaOraria(5.0));

        Mezzo panda = new MezzoBuilder()
                .conId(10)
                .diMarca("Fiat")
                .modello("Panda")
                .immatricolatoNel(2023)
                .conCilindrata(1200)
                .conNumeroPosti(4)
                .diTipo(auto)
                .allocatoPresso(sede)
                .build();
        
        catalogoMezzi.aggiungiMezzo(panda);
    }

    @Test
    void testRicercaMezzi_NessunaPrenotazione_TrovaMezzo() {
        //Simulazione di una richiesta di prenotazione 
        DateRange richiesta = new DateRange(
            LocalDateTime.of(2024, 1, 10, 10, 0), 
            LocalDateTime.of(2024, 1, 15, 10, 0)
        );
        List<Mezzo> risultati = prenotazioneService.cercaMezziDisponibili("CityCar", richiesta, 1);

        assertEquals(1, risultati.size(), "Dovrebbe trovare 1 mezzo disponibile");
        assertEquals(10, risultati.get(0).getId(), "Il mezzo trovato deve essere la Panda (ID 10)");
    }

    @Test
    void testRicercaMezzi_ConSovrapposizione_NascondeIlMezzo() {
        

        Cliente cliente = new Cliente("Mario", "Rossi", "mario.rossi@email.com", "password123");
        DateRange occupato = new DateRange(
            LocalDateTime.of(2024, 1, 10, 10, 0),
            LocalDateTime.of(2024, 1, 15, 10, 0)
        );
        Prenotazione pEsistente = new Prenotazione(cliente, catalogoMezzi.getMezzoById(10), occupato, registroSedi.getPuntoById(1), catalogoTariffe.getTariffaByName("ORARIA"));
        registroPrenotazioni.aggiungiPrenotazione(pEsistente);

        // Richiesta in un range che si sovrappone al precedente
        DateRange richiesta = new DateRange(
            LocalDateTime.of(2024, 1, 14, 10, 0), 
            LocalDateTime.of(2024, 1, 16, 10, 0)
        );
        List<Mezzo> risultati = prenotazioneService.cercaMezziDisponibili("CityCar", richiesta, 1);

        // La lista deve risultare vuota 
        assertTrue(risultati.isEmpty(), "Il mezzo NON deve comparire perché è già prenotato in quelle date");
    }

    @Test
    void testElaboraPrenotazione_ClienteNonAffidabile_LanciaEccezione() {
        //creazione di un cliente sospeso
        Cliente clienteSospeso = new Cliente("Luigi", "Neri", "luigi.neri@email.com", "password123");
        clienteSospeso.sospendiAccount();
        
        DateRange richiesta = new DateRange(
            LocalDateTime.of(2024, 2, 1, 10, 0), 
            LocalDateTime.of(2024, 2, 5, 10, 0)
        );

        // Il cliente sospeso non dovrebbe essere in grado di effettuare la prenotazione
        Exception exception = assertThrows(StatoNonValidoException.class, () -> {
            prenotazioneService.elaboraPrenotazione(clienteSospeso, 10, 1, "ORARIA", richiesta);
        });

        // Verifichiamo del messaggio
        assertTrue(exception.getMessage().contains("Cliente non affidabile"));
        
        // Verifica della non prenotazione
        assertTrue(registroPrenotazioni.findAll().isEmpty(), "Non deve essere stata salvata alcuna prenotazione");
    }
}