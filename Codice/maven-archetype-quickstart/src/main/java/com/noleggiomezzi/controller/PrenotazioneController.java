package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.service.PrenotazioneService; 
import com.noleggiomezzi.utility.DateRange;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/prenota")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService; 

    // Iniezione di una sola dipendenza pulita!
    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @GetMapping("/ricerca")
    public String mostraRicerca(Model model) {
        model.addAttribute("sedi", prenotazioneService.getSediDisponibili());
        model.addAttribute("tipi", prenotazioneService.getTipiMezzo()); 
        return "ricerca-prenotazione";
    }

    @PostMapping("/risultati")
    public String cercaMezzi(@RequestParam String inizio, 
                             @RequestParam String fine, 
                             @RequestParam int sedeId, 
                             @RequestParam String tipoNome, 
                             Model model, 
                             HttpSession session) {
        
        LocalDateTime dataI = LocalDateTime.parse(inizio);
        LocalDateTime dataF = LocalDateTime.parse(fine);
        DateRange periodo = new DateRange(dataI, dataF);
        
        session.setAttribute("periodoPrenotazione", periodo);

        List<Mezzo> disponibili = prenotazioneService.cercaMezziDisponibili(tipoNome, periodo, sedeId);
        
        model.addAttribute("mezzi", disponibili);
        model.addAttribute("tariffe", prenotazioneService.getTariffeDisponibili());
        model.addAttribute("sedeSceltaId", sedeId);

        return "risultati-prenotazione";
    }

    @PostMapping("/conferma")
    public String conferma(@RequestParam int mezzoId, 
                           @RequestParam int sedeId, 
                           @RequestParam String tariffaNome, 
                           HttpSession session) {
        
        Cliente cliente = (Cliente) session.getAttribute("clienteLoggato");
        if (cliente == null) return "redirect:/login-cliente";

        DateRange periodo = (DateRange) session.getAttribute("periodoPrenotazione");
        if (periodo == null) return "redirect:/prenota/ricerca";

        String pnr = prenotazioneService.elaboraPrenotazione(cliente, mezzoId, sedeId, tariffaNome, periodo);
        
        session.removeAttribute("periodoPrenotazione");
        
        return "redirect:/prenota/successo?pnr=" + pnr;
    }

    @GetMapping("/successo")
    public String mostraSuccesso(@RequestParam String pnr, Model model) {
        model.addAttribute("pnr", pnr);
        return "successo";
    }
}