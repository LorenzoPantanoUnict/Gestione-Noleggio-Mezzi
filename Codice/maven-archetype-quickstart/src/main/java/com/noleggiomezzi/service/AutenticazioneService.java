package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Cassiere;
import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.exceptions.CredenzialiErrateException;
import com.noleggiomezzi.repository.interfacce.ICassiereRepository;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;

import org.springframework.stereotype.Service;

@Service
public class AutenticazioneService {
    
    private final ICassiereRepository registroCassieri;
    private final IClienteRepository registroClienti;

    public AutenticazioneService(ICassiereRepository registroCassieri,  IClienteRepository registroClienti) {
        this.registroCassieri = registroCassieri;
        this.registroClienti = registroClienti;
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

    public Cliente autenticaCliente(String email, String password) {

        Cliente cliente = registroClienti.findByEmail(email);

        if (cliente == null) {
            throw new CredenzialiErrateException("Account non trovato per l'email: " + email);
        }

        if (!cliente.checkPassword(password)) {
            throw new CredenzialiErrateException("Password errata.");
        }

        return cliente;
    }
}