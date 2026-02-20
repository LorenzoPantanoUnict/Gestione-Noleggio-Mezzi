package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Cassiere;
import com.noleggiomezzi.repository.RegistroCassieri;
import com.noleggiomezzi.exceptions.CredenzialiErrateException;

import org.springframework.stereotype.Service;

@Service
public class AutenticazioneService {
    
    private RegistroCassieri registroCassieri;

    public AutenticazioneService() {
        this.registroCassieri = RegistroCassieri.getInstance();
    }

    public void autentica(String username, String password) {
        Cassiere cassiere = registroCassieri.findByUsername(username);

        if (cassiere == null) {
            throw new CredenzialiErrateException("Credenziali errate. Utente non trovato.");
        }

        if (!cassiere.checkPassword(password)) {
            throw new CredenzialiErrateException("Credenziali errate. Password non valida.");
        }
        
        System.out.println("[SERVICE] Verifica superata! Credenziali corrette.");
    }
}