package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.CatalogoTipoMezzi;
import com.noleggiomezzi.repository.RegistroManutenzioni;
import com.noleggiomezzi.utility.MezzoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManutenzioneServiceTest {

    private ManutenzioneService manutenzioneService;
    private CatalogoMezzi catalogoMezzi;
    private RegistroManutenzioni registroManutenzioni;
    
    private Mezzo mezzoTest;

    @BeforeEach
    void setUp() {
        // Inizializzazione Repository
        CatalogoTipoMezzi tipiRepo = new CatalogoTipoMezzi();
        catalogoMezzi = new CatalogoMezzi(tipiRepo);
        registroManutenzioni = new RegistroManutenzioni();

        // Inizializzazione Service
        manutenzioneService = new ManutenzioneService(catalogoMezzi, registroManutenzioni);

        /**
         * Creazione del mezzo di test per le riparazioni
         * - Tipo: Furgone
         * - Modello: Ford Transit (ID: 50)
         */
        PuntoNoleggio sede = new PuntoNoleggio(1, "Officina Centrale", "Via Motori 1", 10);
        TipoMezzo furgone = new TipoMezzo("Furgone", true, false, 1.5);
        tipiRepo.aggiungiTipoMezzo(furgone);

        mezzoTest = new MezzoBuilder()
                .conId(50)
                .diMarca("Ford")
                .modello("Transit")
                .immatricolatoNel(2021)
                .diTipo(furgone)
                .allocatoPresso(sede)
                .build();

        catalogoMezzi.aggiungiMezzo(mezzoTest);
    }

    /**
     * Test dello scenario di successo per il Caso d'Uso UC8 (Invia in Riparazione).
     * Il cassiere segnala un guasto e manda il veicolo in officina.
     * * Il test verifica che:
     * - Il metodo inviaInManutenzione() del Pattern State venga richiamato correttamente
     * - Il veicolo non risulti più disponibile per il noleggio
     */
    @Test
    void testInviaInRiparazione_MezzoDisponibile_CambiaStatoInManutenzione() {
        manutenzioneService.inviaInRiparazione(50);

        assertFalse(mezzoTest.isDisponibile(), "Il mezzo in riparazione NON deve essere disponibile");
    }

    /**
     * Test dello scenario di ripristino per il Caso d'Uso UC8 (Registra Intervento).
     * Il veicolo viene riparato con successo e torna in flotta.
     * * Il test verifica che:
     * - Venga creato e salvato lo storico dell'intervento (costo e descrizione)
     * - Lo stato del mezzo torni a essere "Disponibile"
     */
    @Test
    void testRegistraIntervento_StatoDisponibile_SalvaInterventoERipristinaMezzo() {
        
        manutenzioneService.inviaInRiparazione(50);

        manutenzioneService.registraInterventoERipristina(50, "Cambio olio e filtri", 150.0, "DISPONIBILE");

        assertTrue(mezzoTest.isDisponibile(), "Il mezzo riparato deve tornare disponibile");
        assertEquals(1, mezzoTest.getInterventi().size(), "Deve essere registrato 1 intervento nello storico del veicolo");
        assertEquals("Cambio olio e filtri", mezzoTest.getInterventi().get(0).getDescrizione(), "La descrizione deve combaciare");
    }

    /**
     * Test dello scenario di dismissione per il Caso d'Uso UC8 (Registra Intervento).
     * Il veicolo ha subito un guasto irreparabile e viene radiato dalla flotta.
     * * Il test verifica che:
     * - L'intervento (es. perizia) venga comunque salvato nello storico
     * - Lo stato finale del veicolo diventi "Fuori Servizio" (non disponibile)
     */
    @Test
    void testRegistraIntervento_StatoFuoriServizio_DismetteDefinitivamenteIlMezzo() {

        manutenzioneService.inviaInRiparazione(50);

        manutenzioneService.registraInterventoERipristina(50, "Motore fuso, riparazione antieconomica", 0.0, "FUORI_SERVIZIO");

        assertFalse(mezzoTest.isDisponibile(), "Un mezzo fuori servizio NON deve mai essere disponibile");
        assertEquals(1, mezzoTest.getInterventi().size(), "La perizia finale deve essere registrata");

    }
}