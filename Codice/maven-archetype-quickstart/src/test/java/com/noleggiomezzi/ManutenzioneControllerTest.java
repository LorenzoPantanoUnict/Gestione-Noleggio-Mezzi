package com.noleggiomezzi;

import com.noleggiomezzi.controller.ManutenzioneController;
import com.noleggiomezzi.model.*;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManutenzioneControllerTest {

    private ManutenzioneController manutenzioneController;
    private CatalogoMezzi catalogoMezzi;
    private PuntoNoleggio puntoTest;
    private Mezzo mezzoInManutenzione;
    private Mezzo mezzoDaRottamare;

    @BeforeEach
    void setUp() {
        // 1. Inizializzo il catalogo (il nostro repository in memoria)
        catalogoMezzi = new CatalogoMezzi();

        // 2. Inizializzo il Controller, iniettando la dipendenza (Soluzione 1 che hai scelto)
        manutenzioneController = new ManutenzioneController(catalogoMezzi);

        // 3. Preparo i dati finti per i test
        TipoMezzo tipo = new TipoMezzo("CityCar", false, true);
        DescrizioneMezzo desc = new DescrizioneMezzo("Fiat", "Panda", 2022, 1200, 4, tipo);
        puntoTest = new PuntoNoleggio(1, "Stazione Centrale", "Via Roma 1", 10);
        
        // Creo un mezzo normale in manutenzione (ID 10)
        mezzoInManutenzione = new Mezzo(10, desc, puntoTest);
        mezzoInManutenzione.inviaInManutenzione();
        catalogoMezzi.aggiungiMezzo(mezzoInManutenzione);

        // Creo un secondo mezzo da rottamare (ID 11)
        mezzoDaRottamare = new Mezzo(11, desc, puntoTest);
        mezzoDaRottamare.inviaInManutenzione();
        catalogoMezzi.aggiungiMezzo(mezzoDaRottamare);
    }

    // --- SCENARIO PRINCIPALE: Riparazione Classica ---
    @Test
    void testRegistraInterventoConSuccesso() {
        // ACT: Il Controller orchestra la riparazione del mezzo
        manutenzioneController.registraIntervento(10, "Sostituzione specchietto", 150.0, "DISPONIBILE");

        // ASSERT
        // 1. Verifico che lo stato sia diventato effettivamente DISPONIBILE
        assertEquals(StatoMezzo.DISPONIBILE, mezzoInManutenzione.getStato(), "Il mezzo deve tornare disponibile dopo la riparazione.");
        
        // 2. Verifico che l'intervento sia stato salvato nella lista interna dell'Aggregate Root (Mezzo)
        assertEquals(1, mezzoInManutenzione.getInterventi().size(), "L'intervento deve essere stato salvato nella lista del mezzo.");
    }

    // --- SCENARIO ALTERNATIVO 8a: Danno Irreparabile ---
    @Test
    void testRegistraInterventoDannoIrreparabile() {
        // ACT: Il controller processa il mezzo irreparabile
        manutenzioneController.registraIntervento(11, "Motore fuso - Non riparabile", 0.0, "FUORI_SERVIZIO");

        // ASSERT
        assertEquals(StatoMezzo.FUORI_SERVIZIO, mezzoDaRottamare.getStato(), "Il mezzo irreparabile deve essere messo fuori servizio.");
    }

    // --- SCENARIO ALTERNATIVO 2a: Nessun mezzo trovato ---
    @Test
    void testAggiungiInterventoMezzoInesistente() {
        // Poiché il Controller al momento cattura le eccezioni col try-catch bloccandole, 
        // per testare la generazione dell'eccezione invochiamo direttamente il Repository.
        // (Se in futuro togli il try-catch dal controller, potrai testare direttamente manutenzioneController qui)
        
        EnitaNonTrovataException eccezione = assertThrows(EnitaNonTrovataException.class, () -> {
            catalogoMezzi.getMezzoById(999);
        });

        assertEquals("Mezzo con ID 999 non trovato", eccezione.getMessage());
    }
}