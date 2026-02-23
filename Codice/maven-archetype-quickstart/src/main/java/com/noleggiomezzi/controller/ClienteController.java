package com.noleggiomezzi.controller;

import com.noleggiomezzi.service.ClienteService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClienteController si occupa di gestire le richieste relative ai casi d'uso
 * 
 * -Registra Nuovo Cliente
 * -Gestisci BlackList
 * 
 * Delega al Service per la logica 
 */

@Controller
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService rc) {
        this.clienteService = rc;
    }

    /**
     * Registra Nuovo Cliente
     * 
     */

    @GetMapping("/registra-cliente")
    public String mostraFormaRegistrazione(Model model){
        return "registra-cliente";
    }

    @PostMapping("/registra-cliente")
    public String registraClienteRequest(@RequestParam("nome") String nome,
                                        @RequestParam("cognome") String cognome,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password) { // <--- Aggiunto parametro
        try {
            clienteService.registraNuovoCliente(nome, cognome, email, password);
            return "redirect:/registra-cliente?success=true";
        } catch (IllegalArgumentException e) {
            System.err.println("Errore nella registrazione: " + e.getMessage());
            return "redirect:/registra-cliente?error=true";
        }
    }


    /**
     * Gestione BlackList
     */
    @GetMapping("/gestione-clienti")
    public String mostraGestioneClienti(Model model) {
        model.addAttribute("listaClienti", clienteService.getTuttiClienti());
        return "gestione-clienti";
    }

    @PostMapping("/clienti/sospendi")
    public String sospendi(@RequestParam int idCliente) {
        clienteService.sospendiCliente(idCliente);
        return "redirect:/gestione-clienti?success=sospeso";
    }

    @PostMapping("/clienti/riabilita")
    public String riabilita(@RequestParam int idCliente) {
        clienteService.riabilitaCliente(idCliente);
        return "redirect:/gestione-clienti?success=riabilitato";
    }



}
