package com.noleggiomezzi;

import com.noleggiomezzi.model.Cassiere;
import com.noleggiomezzi.repository.RegistroCassieri;
import com.noleggiomezzi.service.AutenticazioneService;
import com.noleggiomezzi.exceptions.CredenzialiErrateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutenticazioneCassiereTest {

    private AutenticazioneService authService;
    private RegistroCassieri registroCassieri;

    @BeforeEach
    void setUp() {
        // 1. Inizializzazione Repository (Singleton)
        registroCassieri = RegistroCassieri.getInstance();

        // 2. Prepariamo i dati di test
        // Assicuriamoci che nel database ci sia sempre il nostro utente di prova 
        // prima di ogni singolo test, nel caso un test precedente lo abbia rimosso.
        if (!registroCassieri.esiste("mario.rossi")) {
            Cassiere admin = new Cassiere(1, "mario.rossi", "pass123", "Mario", "Rossi");
            registroCassieri.aggiungiCassiere(admin);
        }

        // 3. Inizializzazione Service (Il "motore" da testare)
        authService = new AutenticazioneService();
    }

    // --- SCENARIO PRINCIPALE: LOGIN DI SUCCESSO ---
    @Test
    void testAutenticazioneConSuccesso() {
        // ACT & ASSERT
        // Usiamo assertDoesNotThrow: se la logica è corretta, non deve "esplodere" nessuna eccezione
        assertDoesNotThrow(() -> {
            authService.autentica("mario.rossi", "pass123");
        }, "L'autenticazione dovrebbe avere successo con credenziali corrette");
    }

    // --- SCENARIO ALTERNATIVO 4a: PASSWORD ERRATA ---
    @Test
    void testAutenticazioneFallitaPasswordErrata() {
        // ACT & ASSERT
        // Mi aspetto che venga lanciata esattamente la nostra eccezione personalizzata
        CredenzialiErrateException eccezione = assertThrows(CredenzialiErrateException.class, () -> {
            authService.autentica("mario.rossi", "password_sbagliata");
        }, "Il sistema deve bloccare l'accesso se la password è sbagliata");

        // Verifico che il messaggio di errore corrisponda a quello previsto dalla logica
        assertEquals("Credenziali errate. Password non valida.", eccezione.getMessage());
    }

    // --- SCENARIO ALTERNATIVO 4a: UTENTE NON ESISTE ---
    @Test
    void testAutenticazioneFallitaUtenteInesistente() {
        // ACT & ASSERT
        CredenzialiErrateException eccezione = assertThrows(CredenzialiErrateException.class, () -> {
            authService.autentica("utente.fantasma", "pass123");
        }, "Il sistema deve bloccare l'accesso se l'username non esiste nel registro");

        // Verifico il messaggio di errore specifico
        assertEquals("Credenziali errate. Utente non trovato.", eccezione.getMessage());
    }
}
