package com.noleggiomezzi;

import com.noleggiomezzi.controller.NoleggioController;
import com.noleggiomezzi.controller.ClienteController;
import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.enums.StatoNoleggio;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.model.tariffe.TariffaGiornaliera;
import com.noleggiomezzi.model.tariffe.TariffaOraria;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.RegistroClienti;
import com.noleggiomezzi.repository.RegistroNoleggi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class NoleggioControllerTest {

    private NoleggioController controller;
    private RegistroClienti registroClienti;
    private RegistroNoleggi registroNoleggi;
    private CatalogoMezzi catalogoMezzi;
    private PuntoNoleggio puntoTest;
    private ITariffa tariffaStandard;
    ClienteController clienteController;

    @BeforeEach
    void setUp() {
        // 1. Inizializzazione Repository
        registroClienti = RegistroClienti.getInstance();
        registroNoleggi = new RegistroNoleggi();
        catalogoMezzi = new CatalogoMezzi();

        // 2. Inizializzazione Controller
        controller = new NoleggioController(registroClienti, registroNoleggi, catalogoMezzi);
        clienteController = new ClienteController(registroClienti);
        // 3. Dati di supporto comuni (Punto Noleggio e Tariffa)
        puntoTest = new PuntoNoleggio(1, "Stazione Centrale", "Via Roma 1", 10);
        tariffaStandard = new TariffaOraria();
    }

    // --- CASO D'USO 1: REGISTRAZIONE CLIENTE ---
    @Test
    void testRegistrazioneCliente() {
        // ACT
        clienteController.registraCliente("Mario", "Rossi", "mario@email.com");

        // ASSERT
        Cliente c = registroClienti.getCliente(100);
        assertNotNull(c, "Il cliente dovrebbe essere stato salvato nel registro");
        assertEquals("Mario", c.getNome(), "Il nome salvato non corrisponde");
    }

    // --- CASO D'USO 2: AVVIO NOLEGGIO (Scenari Positivi e Negativi) ---
    @Test
    void testAvvioNoleggioConSuccesso() {
        // ARRANGE
        preparaCliente(101, true); // Cliente abilitato
        Mezzo m = preparaMezzo(201, true); // Mezzo disponibile

        // ACT
        int idNoleggio = controller.avviaNoleggio(101, 201, tariffaStandard, puntoTest);

        // ASSERT
        assertTrue(idNoleggio > 0, "L'ID del noleggio deve essere valido");
        
        // Verifico che il mezzo ora risulti OCCUPATO/NOLEGGIATO
        assertFalse(m.isDisponibile(), "Il mezzo noleggiato non deve essere disponibile");
        
        // Verifico che il noleggio esista nel registro
        assertNotNull(registroNoleggi.getNoleggio(idNoleggio));
    }

    @Test
    void testAvvioNoleggioFallitoSeMezzoOccupato() {
        // ARRANGE
        preparaCliente(102, true);
        Mezzo m = preparaMezzo(202, true);
        
        // Simulo che il mezzo sia rotto o già noleggiato
        m.setStato(StatoMezzo.IN_MANUTENZIONE);

        // ACT & ASSERT
        Exception eccezione = assertThrows(RuntimeException.class, () -> {
            controller.avviaNoleggio(102, 202, tariffaStandard, puntoTest);
        });

        assertEquals("Noleggio non consentito", eccezione.getMessage());
    }

    @Test
    void testAvvioNoleggioFallitoSeClienteSospeso() {
        // ARRANGE
        preparaCliente(103, false); // Cliente DISABILITATO (affidabilità 0)
        preparaMezzo(203, true);

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> {
            controller.avviaNoleggio(103, 203, tariffaStandard, puntoTest);
        }, "Un cliente sospeso non dovrebbe poter noleggiare");
    }

    // --- CASO D'USO 3: CONCLUSIONE NOLEGGIO ---
    @Test
    void testConclusioneNoleggio() {
        // ARRANGE
        preparaCliente(104, true);
        preparaMezzo(204, true);
        
        // Uso Tariffa GIORNALIERA per avere un costo fisso prevedibile (20.0 euro)
        // indipendentemente dalla durata in millisecondi del test
        ITariffa tariffaFissa = new TariffaGiornaliera();

        // Avvio un noleggio reale per poi chiuderlo
        int idNoleggio = controller.avviaNoleggio(104, 204, tariffaFissa, puntoTest);
        
        // Recupero l'oggetto noleggio per controlli successivi
        Noleggio n = registroNoleggi.getNoleggio(idNoleggio);

        // ACT
        // Concludo il noleggio: km percorsi 50, carica residua 80%
        controller.concludiNoleggio(idNoleggio, 50, 80.0);

        // ASSERT
        // 1. Il mezzo deve essere tornato DISPONIBILE
        assertEquals(StatoMezzo.DISPONIBILE, n.getMezzo().isDisponibile() ? StatoMezzo.DISPONIBILE : StatoMezzo.NOLEGGIATO);
        
        // 2. Il livello di carica deve essere aggiornato
        assertEquals(80.0, n.getMezzo().getLivelloCarica());
        
        // 3. Lo stato del noleggio deve essere CONCLUSO
        assertEquals(StatoNoleggio.CONCLUSO, n.getStatoNoleggio());
    }

    // --- Metodi Helper (per non ripetere codice) ---
    
    private void preparaCliente(int id, boolean abilitato) {
        // Costruttore: id, nome, cognome, affidabilità, email
        Cliente c = new Cliente( "Test", "User",  "test@email.com");
        registroClienti.aggiungiCliente(c);
    }

    private Mezzo preparaMezzo(int id, boolean disponibile) {
        TipoMezzo tipo = new TipoMezzo("CityCar", false, true);
        DescrizioneMezzo desc = new DescrizioneMezzo("Fiat", "Panda", 2022, 1200, 4, tipo);
        
        Mezzo m = new Mezzo(id, desc);
        catalogoMezzi.aggiungiMezzo(m);
        return m;
    }
}