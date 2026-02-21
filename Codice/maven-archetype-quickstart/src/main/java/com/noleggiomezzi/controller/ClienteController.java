package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;

//Validatore
import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    private IClienteRepository registroClienti;

    public ClienteController(IClienteRepository rc) {
        this.registroClienti = rc;
    }


    @GetMapping("/registra-cliente")
    public String mostraFormaRegistrazione(Model model){
        return "registra-cliente";
    }

    @PostMapping("/registra-cliente")
    public String registraClienteRequest(@RequestParam("nome") String nome,
                                  @RequestParam("cognome") String cognome,
                                  @RequestParam("email") String email) {
        try {
            registraCliente(nome, cognome, email);
            return "redirect:/registra-cliente?success=true";
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nella registrazione: " + e.getMessage());
            return "redirect:/registra-cliente?error=true";
        }
    }


    public void registraCliente(String nome, String cognome, String email) {
        
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email non valida");
        }
        
        if(!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email non valida");
        }
        Cliente c = new Cliente( nome, cognome, email);
        
        registroClienti.aggiungiCliente(c);
    }
}
