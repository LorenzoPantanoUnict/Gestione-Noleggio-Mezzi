package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.CatalogoTipoMezzi;
import com.noleggiomezzi.repository.RegistroPrenotazioni;
import com.noleggiomezzi.repository.RegistroPuntiNoleggio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MezzoServiceTest {

    private MezzoService mezzoService;
    private CatalogoMezzi catalogoMezzi;
    private RegistroPuntiNoleggio registroSedi;
    private CatalogoTipoMezzi tipiRepo;

    @BeforeEach
    void setUp() {
        // Inizializzazione Repository
        tipiRepo = new CatalogoTipoMezzi();
        catalogoMezzi = new CatalogoMezzi(tipiRepo);
        RegistroPrenotazioni registroPrenotazioni = new RegistroPrenotazioni();
        registroSedi = new RegistroPuntiNoleggio();

        // Inizializzazione Service
        mezzoService = new MezzoService(catalogoMezzi, registroPrenotazioni, registroSedi, tipiRepo);

        /**
         * Creazione dati di test base necessari per aggiungere un mezzo
         * - Punto Noleggio: Aeroporto (ID: 2)
         * - TipoMezzo: Furgone
         */
        PuntoNoleggio sede = new PuntoNoleggio(2, "Aeroporto", "Terminal 1", 20);
        registroSedi.aggiungiPunto(sede);

        TipoMezzo furgone = new TipoMezzo("Furgone", true, false, 1.5);
        tipiRepo.aggiungiTipoMezzo(furgone);
    }

    /**
     * Test dello scenario di successo per il Caso d'Uso: Aggiungi Nuovo Mezzo (Gestione Parco Auto).
     * Il cassiere/admin inserisce correttamente tutti i dati di un nuovo veicolo.
     * * Il test verifica che:
     * - Il mezzo venga effettivamente aggiunto al catalogo (size() == 1)
     * - I dati inseriti tramite il Builder corrispondano esattamente a quelli salvati
     * - Lo stato iniziale del nuovo mezzo sia automaticamente impostato su "Disponibile"
     */
    @Test
    void testAggiungiNuovoMezzo_DatiValidi_MezzoAggiuntoCorrettamente() {

        mezzoService.aggiungiNuovoMezzo(
                105, 
                "Fiat", 
                "Ducato", 
                2022, 
                2300, 
                3, 
                "Furgone", 
                2 // ID della sede Aeroporto
        );

        List<Mezzo> mezziSalvati = mezzoService.getTuttiMezzi();
        assertEquals(1, mezziSalvati.size(), "Dovrebbe esserci esattamente 1 mezzo nel catalogo");

        Mezzo ducato = mezziSalvati.get(0);
        assertEquals(105, ducato.getId(), "L'ID del mezzo deve essere 105");
        assertEquals("Fiat", ducato.getDescrizione().getMarca(), "La marca deve essere Fiat");
        assertEquals("Furgone", ducato.getTipo().getNome(), "Il tipo deve essere Furgone");
        
        assertTrue(ducato.isDisponibile(), "Il nuovo mezzo deve essere immediatamente disponibile per il noleggio");
    }

    /**
     * Test dello scenario di fallimento per il Caso d'Uso: Aggiungi Nuovo Mezzo.
     * Il cassiere tenta di inserire un mezzo con un anno di immatricolazione non valido (es. prima del 1900).
     * * Il test verifica che:
     * - Il MezzoBuilder intercetti l'errore logico e lanci un'IllegalStateException
     * - Il catalogo mezzi rimanga vuoto (l'operazione viene abortita prima del salvataggio)
     */
    @Test
    void testAggiungiNuovoMezzo_AnnoNonValido_LanciaEccezioneBuilder() {

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            mezzoService.aggiungiNuovoMezzo(
                    106, 
                    "Ford", 
                    "Model T", 
                    1850, // Anno non valido!
                    1000, 
                    2, 
                    "Furgone", 
                    2
            );
        });

        assertTrue(exception.getMessage().contains("Anno di immatricolazione non valido"));

        assertTrue(mezzoService.getTuttiMezzi().isEmpty(), "Il catalogo deve rimanere vuoto se l'inserimento fallisce");
    }

    /**
     * Test dello scenario di fallimento (Assenza Dati Obbligatori).
     * Il cassiere dimentica di inserire la marca del veicolo.
     * * Il test verifica che:
     * - Il MezzoBuilder si rifiuti di costruire l'oggetto e lanci un'eccezione
     * - Il messaggio specifichi che Marca e Modello sono obbligatori
     */
    @Test
    void testAggiungiNuovoMezzo_MarcaMancante_LanciaEccezioneBuilder() {

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            mezzoService.aggiungiNuovoMezzo(
                    107, 
                    null, //Errore che causa l'eccezzione
                    "Panda", 
                    2023, 
                    1200, 
                    4, 
                    "Furgone", 
                    2
            );
        });

        assertTrue(exception.getMessage().contains("Marca e modello sono obbligatori"));
        assertTrue(mezzoService.getTuttiMezzi().isEmpty());
    }
}