package com.noleggiomezzi;

import com.noleggiomezzi.controller.PrenotazioneController;
import com.noleggiomezzi.exceptions.NessunaDisponibilitaException;
import com.noleggiomezzi.model.*;
import com.noleggiomezzi.repository.CatalogoMezzi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrenotazioneControllerTest {

    private PrenotazioneController controller;
    private CatalogoMezzi catalogoMezzi;
    
    private Cliente cliente;
    private Mezzo mezzoDisponibile;
    private PuntoNoleggio sede;
    private TipoMezzo tipoRichiesto;
    private DateRange periodoRichiesto;
    PuntoNoleggio puntoTest;

    @BeforeEach
    void setUp() {
        // 1. Inizializzo il catalogo e il controller
        catalogoMezzi = new CatalogoMezzi();
        controller = new PrenotazioneController(catalogoMezzi);
        puntoTest = new PuntoNoleggio(1, "Stazione Centrale", "Via Roma 1", 10);
        
        // 2. Preparo i dati finti per il test (Sede, Tipo e Cliente)
        sede = new PuntoNoleggio(1, "Milano Centrale", "Piazza Duca D'Aosta", 50);
        tipoRichiesto = new TipoMezzo("SUV", false, true);
        DescrizioneMezzo desc = new DescrizioneMezzo("Jeep", "Renegade", 2023, 1600, 5, tipoRichiesto);
        
        // 3. Creo un mezzo e lo metto nel catalogo come DISPONIBILE
        mezzoDisponibile = new Mezzo(100, desc, puntoTest);
        mezzoDisponibile.setPuntoNoleggio(sede);
        mezzoDisponibile.setStato(StatoMezzo.DISPONIBILE);
        catalogoMezzi.aggiungiMezzo(mezzoDisponibile);

        // 4. Preparo il Cliente
        cliente = new Cliente("Mario", "Rossi", "mario@email.com");
        
        // 5. Preparo le date (Prenotazione da domani a dopodomani)
        LocalDateTime inizio = LocalDateTime.now().plusDays(1);
        LocalDateTime fine = inizio.plusDays(2);
        periodoRichiesto = new DateRange(inizio, fine);
    }

    // --- SCENARIO PRINCIPALE: Prenotazione completata con successo ---
    @Test
    void testRicercaEConfermaPrenotazioneConSuccesso() {
        // ACT - FASE 1: Il cliente cerca disponibilità
        List<Mezzo> disponibili = controller.cercaDisponibilita(tipoRichiesto, periodoRichiesto, sede.getId());
        
        // ASSERT: Verifico che il sistema mi proponga l'auto che ho inserito
        assertFalse(disponibili.isEmpty(), "Il sistema deve trovare almeno un mezzo disponibile.");
        assertTrue(disponibili.contains(mezzoDisponibile));

        // ACT - FASE 2: Il cliente conferma la prenotazione
        String pnr = controller.creaNuovaPrenotazione(cliente, mezzoDisponibile, periodoRichiesto, sede);
        
        // ASSERT: Verifiche finali post-condizione
        // 1. Il codice PNR deve essere stato generato ed essere lungo 6 caratteri
        assertNotNull(pnr, "Il PNR non deve essere nullo.");
        assertEquals(6, pnr.length(), "Il PNR deve essere composto da 6 caratteri.");
        
        // 2. Lo stato del mezzo deve essere cambiato per evitare doppie prenotazioni
        assertEquals(StatoMezzo.NOLEGGIATO, mezzoDisponibile.getStato(), "Il mezzo deve risultare occupato dopo la conferma della prenotazione.");
    }

    // --- SCENARIO ALTERNATIVO 4a: Tutto esaurito ---
    @Test
    void testRicercaFallitaNessunaDisponibilita() {
        // ARRANGE: Simulo che qualcuno abbia appena noleggiato l'unica auto disponibile
        mezzoDisponibile.setStato(StatoMezzo.NOLEGGIATO);

        // ACT & ASSERT: Provo a cercare un'auto in quella sede e mi aspetto l'eccezione
        NessunaDisponibilitaException eccezione = assertThrows(NessunaDisponibilitaException.class, () -> {
            controller.cercaDisponibilita(tipoRichiesto, periodoRichiesto, sede.getId());
        });

        // Verifico che il messaggio di errore sia quello corretto
        assertEquals("Nessun veicolo disponibile per le date e la sede selezionate.", eccezione.getMessage());
    }
}
