package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.IClienteRepository;

//Validatore
import org.apache.commons.validator.routines.EmailValidator;

public class ClienteController {

    private IClienteRepository registroClienti;

    public ClienteController(IClienteRepository rc) {
        this.registroClienti = rc;
    }


    public void registraCliente(String nome, String cognome, String email) {
        
        Cliente c = new Cliente( nome, cognome, email);

        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email non valida");
        }

        if(!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email non valida");
        }
        
        registroClienti.aggiungiCliente(c);
    }
}
