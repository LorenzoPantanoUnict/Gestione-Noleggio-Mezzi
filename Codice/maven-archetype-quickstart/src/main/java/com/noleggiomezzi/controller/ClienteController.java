package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.service.ClienteService;

//Validatore

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService rc) {
        this.clienteService = rc;
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
            clienteService.registraNuovoCliente(nome, cognome, email);
            return "redirect:/registra-cliente?success=true";
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nella registrazione: " + e.getMessage());
            return "redirect:/registra-cliente?error=true";
        }
    }

}
