package com.noleggiomezzi.controller;

import com.noleggiomezzi.service.AutenticazioneService;
import com.noleggiomezzi.exceptions.CredenzialiErrateException;
import com.noleggiomezzi.model.Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;


/*
* Controller per gestire le operazioni di login e logout dei cassieri.
*  - Autentica Cassiere
*/
@Controller
public class LoginController {
    
    private final AutenticazioneService authService;

    public LoginController(AutenticazioneService authService) {
        this.authService = authService;
    }

    @GetMapping({"/", "/index"})
    public String mostraHome() {
        return "index";
    }

    @GetMapping("/login")
    public String mostraLogin() {
        return "login"; 
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            authService.autentica(username, password);

            session.setAttribute("utenteLoggato", username);

            return "redirect:/dashboard";
        } catch (CredenzialiErrateException e) {
            model.addAttribute("errore", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String mostraDashboard(Model model) {
    
        return "dashboard"; 
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login?logout=true";
    }

    @GetMapping("/login-cliente")
    public String mostraLoginCliente() {
        return "login-cliente";
    }

    @PostMapping("/login-cliente")
    public String loginCliente(@RequestParam String email, 
                            @RequestParam String password, 
                            HttpSession session, 
                            Model model) {
        try {
            Cliente cliente = authService.autenticaCliente(email, password);

            session.setAttribute("clienteLoggato", cliente);

            return "redirect:/prenota/ricerca"; 
            
        } catch (CredenzialiErrateException e) {
            model.addAttribute("errore", e.getMessage());
            return "login-cliente";
        }
    }
}