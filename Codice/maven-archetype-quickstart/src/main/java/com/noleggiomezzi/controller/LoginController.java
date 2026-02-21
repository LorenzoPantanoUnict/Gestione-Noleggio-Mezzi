package com.noleggiomezzi.controller;

import com.noleggiomezzi.service.AutenticazioneService;
import com.noleggiomezzi.exceptions.CredenzialiErrateException;

import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    
    private AutenticazioneService authService;

    public LoginController(AutenticazioneService authService) {
        this.authService = authService;
    }

    public void login(String username, String password) {
        try {
            authService.autentica(username, password);
            System.out.println("SUCCESSO: Login effettuato per l'utente '" + username + "'.");
        } catch (CredenzialiErrateException e) {
            System.err.println("ERRORE LOGIN: " + e.getMessage());
        }
    }
}