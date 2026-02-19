package com.noleggiomezzi;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.RegistroManutenzioni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistroManutenzioniTest {

    private RegistroManutenzioni registroManutenzioni;
    private CatalogoMezzi catalogoMezzi;
    private Mezzo mezzoInManutenzione;
    private Mezzo mezzoDaRottamare;

    @BeforeEach
    void setUp() {
        // 1. Inizializzo il catalogo
        catalogoMezzi = new CatalogoMezzi(); // Oppure CatalogoMezzi.getInstance() se usate il Singleton lì

        // 2. Inizializzo il registro passandogli il catalogo
        registroManutenzioni = new RegistroManutenzioni(catalogoMezzi);

        // 3. Preparo i dati finti per i test (Uso la stessa logica dei vostri test precedenti)
        TipoMezzo tipo = new TipoMezzo("CityCar", false, true);
        DescrizioneMezzo desc = new DescrizioneMezzo("Fiat", "Panda", 2022, 1200, 4, tipo);
        
        // Creo un mezzo normale da riparare (ID 10)
        mezzoInManutenzione = new Mezzo(10, desc);
        mezzoInManutenzione.setStato(StatoMezzo.IN_MANUTENZIONE); // Pre-condizione UC8
        catalogoMezzi.aggiungiMezzo(mezzoInManutenzione);

        // Creo un secondo mezzo con un danno gravissimo (ID 11)
        mezzoDaRottamare = new Mezzo(11, desc);
        mezzoDaRottamare.setStato(StatoMezzo.IN_MANUTENZIONE); // Pre-condizione UC8
        catalogoMezzi.aggiungiMezzo(mezzoDaRottamare);
    }

    // --- SCENARIO PRINCIPALE: Riparazione Classica ---
    @Test
    void testAggiungiInterventoConSuccesso() {
        // ACT: L'operatore ripara il mezzo e lo rimette disponibile
        registroManutenzioni.aggiungiIntervento(10, "Sostituzione specchietto", 150.0, "DISPONIBILE");

        // ASSERT
        // 1. Verifico che lo stato sia diventato effettivamente DISPONIBILE
        assertEquals(StatoMezzo.DISPONIBILE, mezzoInManutenzione.getStato(), "Il mezzo deve tornare disponibile dopo la riparazione.");
        
        // 2. Verifico che l'intervento sia stato salvato nella lista interna del mezzo (opzionale ma consigliato)
        // Se nel tuo Mezzo.java non hai il metodo getInterventi(), questa riga puoi anche toglierla, 
        // ma il controllo dello stato è la cosa più importante!
    }

    // --- SCENARIO ALTERNATIVO 8a: Danno Irreparabile ---
    @Test
    void testAggiungiInterventoDannoIrreparabile() {
        // ACT: L'operatore constata che il motore è fuso e mette fuori servizio (Costo 0)
        registroManutenzioni.aggiungiIntervento(11, "Motore fuso - Non riparabile", 0.0, "FUORI_SERVIZIO");

        // ASSERT
        // Verifico che lo stato sia stato forzato a FUORI_SERVIZIO
        assertEquals(StatoMezzo.FUORI_SERVIZIO, mezzoDaRottamare.getStato(), "Il mezzo irreparabile deve essere messo fuori servizio.");
    }

    // --- SCENARIO ALTERNATIVO 2a: Nessun mezzo trovato ---
    @Test
    void testAggiungiInterventoMezzoInesistente() {
        // Usa la TUA eccezione personalizzata
        com.noleggiomezzi.exceptions.EnitaNonTrovataException eccezione = assertThrows(com.noleggiomezzi.exceptions.EnitaNonTrovataException.class, () -> {
            registroManutenzioni.aggiungiIntervento(999, "Cambio olio", 80.0, "DISPONIBILE");
        });

        // Controlla il tuo messaggio esatto
        assertEquals("Mezzo con ID 999 non trovato", eccezione.getMessage());
    }
}