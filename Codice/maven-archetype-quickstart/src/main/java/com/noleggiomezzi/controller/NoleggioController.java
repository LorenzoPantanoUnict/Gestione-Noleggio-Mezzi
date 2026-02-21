package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.service.NoleggioService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
* NoleggioController si occupa di gestire le richieste relative ai casi d'uso:
* - Avvia Noleggio
* - Concludi Noleggio
* - Segnala Furto
*
* Comunica esclusivamente con il NoleggioService per ogni operazione.
*/
@Controller
public class NoleggioController {

    private final NoleggioService noleggioService;

    public NoleggioController(NoleggioService noleggioService) { 
        this.noleggioService = noleggioService;
    }

    // Avvia Noleggio

    @GetMapping("/avvia-noleggio")
    public String mostraFormAvvio(Model model) {
        // Il controller chiede i dati al Service invece di andare direttamente sui Repo
        model.addAttribute("listaClienti", noleggioService.getTuttiIClienti());
        model.addAttribute("mezziDisponibili", noleggioService.getMezziDisponibili());
        model.addAttribute("listaSedi", noleggioService.getTutteLeSedi());
        model.addAttribute("listaTariffe", noleggioService.getTutteLeTariffe()); 

        return "avvia-noleggio";
    }

    @PostMapping("/avvia-noleggio")
    public String avviaNoleggioRequest(
            @RequestParam("clienteId") int clienteId, 
            @RequestParam("mezzoId") int mezzoId,
            @RequestParam("tariffa") String tariffa, 
            @RequestParam("puntoNoleggioId") int puntoNoleggioId) {
            
        try {
            noleggioService.avviaNoleggio(clienteId, mezzoId, tariffa, puntoNoleggioId);
            return "redirect:/avvia-noleggio?success=true";
        } catch (Exception e) {
            return "redirect:/avvia-noleggio?error=true";
        }
    }

    // Concludi Noleggio

    @GetMapping("/concludi-noleggio")
    public String mostraNoleggiAttivi(Model model) {
        List<Noleggio> attivi = noleggioService.noleggiAttivi();
        model.addAttribute("noleggiAttivi", attivi);
        return "concludi-noleggio";
    }

    @PostMapping("/concludi-noleggio")
    public String concludiNoleggioRequest(
            @RequestParam("noleggioId") int idNoleggio,
            @RequestParam("kmFinali") int kmFinali,
            @RequestParam("livelloCarica") double livelloCarica) {
        
        try {
            noleggioService.concludiNoleggio(idNoleggio, kmFinali, livelloCarica);
            return "redirect:/concludi-noleggio?success=true";
        } catch (Exception e) {
            return "redirect:/concludi-noleggio?error=true";
        }
    }

    // Segnala Furto

    @GetMapping("/segnala-furto")
    public String mostraFormFurto(Model model) {
        model.addAttribute("noleggiAttivi", noleggioService.noleggiAttivi());
        return "segnala-furto";
    }

    @PostMapping("/segnala-furto")
    public String segnalaFurtoRequest(
            @RequestParam("noleggioId") int idNoleggio,
            @RequestParam("descrizione") String descrizione) {
        
        try {
            noleggioService.segnalaFurto(idNoleggio, descrizione);
            return "redirect:/catalogo?furtoSegnalato=true";
        } catch (Exception e) {
            return "redirect:/segnala-furto?error=true";
        }
    }
}