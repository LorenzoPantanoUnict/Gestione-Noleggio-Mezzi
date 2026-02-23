package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.RegistroClienti;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private ClienteService clienteService;
    private RegistroClienti registroClienti;

    @BeforeEach
    void setUp() {
        // Inizializzazione Repository e Service
        registroClienti = new RegistroClienti();
        clienteService = new ClienteService(registroClienti);

        
        //Creazione di un cliente base per i test di duplicazione e sospensione.
        Cliente clienteBase = new Cliente("Mario", "Rossi", "mario.rossi@email.com", "password123");
        registroClienti.aggiungiCliente(clienteBase);
    }

    /**
     * Test dello scenario di successo per il Caso d'Uso: Registrazione Cliente.
     * Un nuovo utente inserisce tutti i dati correttamente.
     * * Il test verifica che:
     * - Il cliente venga aggiunto al registro (la dimensione aumenta a 2)
     * - I dati salvati corrispondano a quelli inseriti
     * - Il cliente appena creato sia considerato "affidabile" di default
     */
    @Test
    void testRegistraNuovoCliente_DatiValidi_ClienteAggiuntoCorrettamente() {
        
        clienteService.registraNuovoCliente("Luigi", "Verdi", "luigi.verdi@email.com", "sicura456");

        List<Cliente> tuttiClienti = clienteService.getTuttiClienti();
        assertEquals(2, tuttiClienti.size(), "Il registro dovrebbe contenere 2 clienti");

        Cliente nuovoCliente = tuttiClienti.get(1);
        assertEquals("Luigi", nuovoCliente.getNome());
        assertEquals("luigi.verdi@email.com", nuovoCliente.getEmail());
        assertTrue(nuovoCliente.isAffidabile(), "Un nuovo cliente deve essere affidabile di default");
    }

    /**
     * Test dello scenario di fallimento per il Caso d'Uso: Registrazione Cliente.
     * Un utente tenta di registrarsi con un'email già presente nel database.
     * * Il test verifica che:
     * - Il Service intercetti il duplicato e lanci un'IllegalArgumentException
     * - Il messaggio di errore specifichi che l'email esiste già
     * - Il nuovo cliente NON venga aggiunto al registro
     */
    @Test
    void testRegistraNuovoCliente_EmailGiaEsistente_LanciaEccezione() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registraNuovoCliente("Falso", "Mario", "mario.rossi@email.com", "hacker123");
        });

        assertTrue(exception.getMessage().contains("Esiste già un cliente registrato con l'email"));
        assertEquals(1, clienteService.getTuttiClienti().size(), "Il registro non deve essere stato modificato");
    }

    /**
     * Test dello scenario di fallimento (Validazione Input).
     * Si tenta di registrare un cliente passando valori nulli o password vuota.
     * * Il test verifica che:
     * - Il Service blocchi immediatamente l'operazione lanciando un'eccezione
     */
    @Test
    void testRegistraNuovoCliente_DatiMancanti_LanciaEccezione() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.registraNuovoCliente("Anna", "Neri", "anna@email.com", null);
        });

        assertTrue(exception.getMessage().contains("sono obbligatori"));
    }

    /**
     * Test dello scenario di Gestione Sicurezza: Sospensione e Riabilitazione.
     * L'amministratore sospende un account e poi lo riabilita.
     * * Il test verifica che:
     * - Dopo la sospensione, isAffidabile() restituisca false
     * - Dopo la riabilitazione, isAffidabile() torni a restituire true
     */
    @Test
    void testSospendiERiabilitaCliente_CambiaStatoAffidabilita() {

        int idMario = clienteService.getTuttiClienti().get(0).getId();

        clienteService.sospendiCliente(idMario);

        Cliente mario = registroClienti.getClienteById(idMario);
        assertFalse(mario.isAffidabile(), "Il cliente dovrebbe essere non affidabile (sospeso)");

        clienteService.riabilitaCliente(idMario);

        assertTrue(mario.isAffidabile(), "Il cliente dovrebbe essere tornato affidabile");
    }
}