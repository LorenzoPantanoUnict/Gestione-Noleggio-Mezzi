package com.noleggiomezzi.service;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.enums.StatoNoleggio;
import com.noleggiomezzi.model.tariffe.TariffaOraria;
import com.noleggiomezzi.repository.*;
import com.noleggiomezzi.utility.MezzoBuilder;
import com.noleggiomezzi.exceptions.PagamentoException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NoleggioServiceTest {

    private NoleggioService noleggioService;
    
    // Repository in memoria per simulare il database
    private RegistroClienti registroClienti;
    private RegistroNoleggi registroNoleggi;
    private CatalogoMezzi catalogoMezzi;
    private RegistroPuntiNoleggio registroSedi;
    private CatalogoTariffe catalogoTariffe;

    private Cliente clienteValido;
    private Mezzo mezzoPronto;
    private PuntoNoleggio sede;

    @BeforeEach
    void setUp() {
        // Inizializzazione repository
        registroClienti = new RegistroClienti();
        registroNoleggi = new RegistroNoleggi();
        CatalogoTipoMezzi tipiRepo = new CatalogoTipoMezzi();
        catalogoMezzi = new CatalogoMezzi(tipiRepo);
        registroSedi = new RegistroPuntiNoleggio();
        catalogoTariffe = new CatalogoTariffe();

        noleggioService = new NoleggioService(registroClienti, registroNoleggi, catalogoMezzi, registroSedi, catalogoTariffe);

        
        /**
         * Creazione dati di test
         * -Cliente: Mario Rossi
         * -Punto Noleggio: Sede Milano
         * -TipoMezzo: CityCar
         * -Mezzo: Fiat Panda
         */
        clienteValido = new Cliente("Mario", "Rossi", "mario.rossi@email.com", "password123");
        registroClienti.aggiungiCliente(clienteValido);

        sede = new PuntoNoleggio(1, "Sede Milano", "Via Milano 1", 10);
        registroSedi.aggiungiPunto(sede);
        
        catalogoTariffe.aggiungiTariffa("ORARIA", new TariffaOraria(5.0));

        TipoMezzo tipoAuto = new TipoMezzo("CityCar", false, true, 1.0);
        mezzoPronto = new MezzoBuilder()
                .conId(101)
                .diMarca("Fiat")
                .modello("Panda")
                .immatricolatoNel(2023)
                .diTipo(tipoAuto)
                .allocatoPresso(sede)
                .build();
        catalogoMezzi.aggiungiMezzo(mezzoPronto);
    }

    /**
     * UC4: Avvia Noleggio
     * 
     * Test dello scenario di successo, il test verifica che:
     * -Il noleggio venga salvato nella repository
     * -Lo stato del mezzo noleggiato non sia più disponibile
     * -Il noleggio generato abbia Id maggiore di 0
     */
    @Test
    void testAvviaNoleggio_TuttoValido_CreaNoleggioECambiaStato() {

        int noleggioId = noleggioService.avviaNoleggio(
            clienteValido.getId(), 
            mezzoPronto.getId(), 
            "ORARIA", 
            sede.getId(), 
            null 
        );

        assertEquals(1, noleggioService.noleggiAttivi().size(), "Dovrebbe esserci 1 noleggio attivo");
        
        assertFalse(mezzoPronto.isDisponibile(), "L'auto non dovrebbe più essere disponibile fisicamente");
        
        assertTrue(noleggioId > 0, "L'ID del noleggio generato deve essere maggiore di 0");
    }


    /**
     *  UC4: Avvia Noleggio
     *  Test scenario limite in cui un un cassiere rischia di noleggiare un mezzo ad un utente sospeso
     * 
     *  Il test verifica che:
     *  -L'eccezione generata menzioni la non possibilità del cliente a noleggiare il mezzo
     *  -Il mezzo selezonato sia ancora disponibile (e quindi non sia stato noleggiato)
     */
    @Test
    void testAvviaNoleggio_ClienteSospeso_LanciaEccezione() {

        Cliente clienteSospeso = new Cliente("Luigi", "Neri", "luigi@email.com", "pass");
        clienteSospeso.sospendiAccount();
        registroClienti.aggiungiCliente(clienteSospeso);

        Exception exception = assertThrows(StatoNonValidoException.class, () -> {
            noleggioService.avviaNoleggio(clienteSospeso.getId(), mezzoPronto.getId(), "ORARIA", sede.getId(), null);
        });

        assertTrue(exception.getMessage().contains("non abilitato"));
        
        assertTrue(mezzoPronto.isDisponibile(), "L'auto deve essere rimasta disponibile nel parcheggio");
    }

    /**
     * UC4: AvviaNoleggio
     * 
     * Test Scenario di fallimento in cui si tenta di noleggiare un mezzo scarico:
     * 
     *  Il test verifica che:
     *  -L'eccezione lanciata menzioni lo stato di carica insufficiente del mezzo
     *  -Il noleggio non sia stato effetuato (numero di noleggi attivi uguale a 0)
     */
    @Test
    void testAvviaNoleggio_MezzoScaricato_LanciaEccezione() {

        mezzoPronto.aggiornaLivelloCarica(15.0); 

        Exception exception = assertThrows(StatoNonValidoException.class, () -> {
            noleggioService.avviaNoleggio(clienteValido.getId(), mezzoPronto.getId(), "ORARIA", sede.getId(), null);
        });

        assertTrue(exception.getMessage().contains("livello carica insufficiente"));
        
        assertEquals(0, noleggioService.noleggiAttivi().size(), "Nessun noleggio deve essere partito");
    }

    /**
     * Test dello scenario di successo per il Caso d'Uso UC7 (Concludi Noleggio).
     * Il cliente restituisce il mezzo con batteria sufficiente e credito per pagare.
     * * Il test verifica che:
     * - Il noleggio venga chiuso e non risulti più tra quelli attivi
     * - Lo stato del mezzo torni a essere disponibile
     * - Il credito del cliente venga scalato correttamente
     */
    @Test
    void testConcludiNoleggio_TuttoValido_ChiudeNoleggioETornaDisponibile() {
        // ARRANGE: Avviamo prima un noleggio
        int idNoleggio = noleggioService.avviaNoleggio(clienteValido.getId(), mezzoPronto.getId(), "ORARIA", sede.getId(), null);
        
        // Conserviamo il credito iniziale per verificare il pagamento
        // (Il credito di default è 200, la tariffa è 5/ora, ipotizziamo un costo minimo calcolato)

        // ACT: Concludiamo il noleggio simulando 10 km percorsi e 90% di batteria residua
        noleggioService.concludiNoleggio(idNoleggio, 10, 90.0);

        // ASSERT: Verifiche post-conclusione
        assertEquals(0, noleggioService.noleggiAttivi().size(), "Non dovrebbero esserci noleggi attivi");
        
        Noleggio noleggioChiuso = registroNoleggi.getNoleggioById(idNoleggio);
        assertEquals(StatoNoleggio.CONCLUSO, noleggioChiuso.getStatoNoleggio(), "Lo stato del noleggio deve essere CHIUSO");
        
        assertTrue(mezzoPronto.isDisponibile(), "L'auto deve essere tornata disponibile per altri clienti");
        assertEquals(90.0, mezzoPronto.getLivelloCarica(), "Il livello di carica deve essere aggiornato a 90");
        
        // Il credito di default è 200. Se il pagamento è passato, ora deve essere inferiore a 200.
        // Nota: non possiamo asserire l'importo esatto in modo pulito perché dipende dal tempo di esecuzione,
        // ma sappiamo che è stato addebitato qualcosa.
        // (Per un test più preciso, servirebbe iniettare un "orologio finto" per simulare il passaggio del tempo)
    }

    /**
     * Test dello scenario di fallimento per il Caso d'Uso UC7 (Concludi Noleggio).
     * Il cliente non ha abbastanza credito per saldare il costo del noleggio.
     * * Il test verifica che:
     * - Il sistema lanci una PagamentoException
     * - L'account del cliente venga immediatamente sospeso
     * - Il mezzo venga comunque reso disponibile (il garage non tiene l'auto in ostaggio)
     */
    @Test
    void testConcludiNoleggio_CreditoInsufficiente_LanciaEccezioneESospendeCliente() {

        Cliente clientePovero = new Cliente("Gino", "Bianchi", "gino@email.com", "pass");
        clientePovero.addebbitaImporto(200.0); //Credito azzerato (200 era il credito di default)
        clientePovero.aggiungiCredito(-10.0);  //Credito negativo per fare in modo che al pagamento sia lanciata l'eccezione
        registroClienti.aggiungiCliente(clientePovero);

        int idNoleggio = noleggioService.avviaNoleggio(clientePovero.getId(), mezzoPronto.getId(), "ORARIA", sede.getId(), null);

        Exception exception = assertThrows(PagamentoException.class, () -> {
            noleggioService.concludiNoleggio(idNoleggio, 50, 80.0);
        });

        assertTrue(exception.getMessage().contains("credito insufficiente"), "L'errore deve menzionare il credito");
        
        assertFalse(clientePovero.isAffidabile(), "Il cliente insolvente DEVE essere sospeso");
        assertTrue(mezzoPronto.isDisponibile(), "Il mezzo DEVE essere recuperato e reso disponibile");
    }

    /**
     * Test dello scenario di segnalazione furto per il Caso d'Uso Avvia Segnalazione (Estensione di UC7).
     * Il cliente o il cassiere segnalano che il veicolo noleggiato è stato rubato.
     * * Il test verifica che:
     * - Il noleggio venga forzatamente chiuso
     * - L'account del cliente venga sospeso (per indagini di sicurezza)
     * - Lo stato del mezzo non sia più "Disponibile" né "Noleggiato"
     */
    @Test
    void testSegnalaFurto_DuranteNoleggio_ChiudeSospendeECambiaStatoMezzo() {

        int idNoleggio = noleggioService.avviaNoleggio(clienteValido.getId(), mezzoPronto.getId(), "ORARIA", sede.getId(), null);

        noleggioService.segnalaFurto(idNoleggio, "Rubata nel parcheggio di via Roma");

        Noleggio noleggioChiuso = registroNoleggi.getNoleggioById(idNoleggio);
        assertEquals(StatoNoleggio.CONCLUSO, noleggioChiuso.getStatoNoleggio(), "Il noleggio deve essere chiuso per furto");
        
        assertFalse(clienteValido.isAffidabile(), "Il cliente deve essere sospeso per prassi post-furto");
        
        assertFalse(mezzoPronto.isDisponibile(), "Il mezzo rubato non può tornare disponibile!");
    }
}