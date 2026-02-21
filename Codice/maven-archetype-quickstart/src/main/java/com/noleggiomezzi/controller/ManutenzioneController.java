package com.noleggiomezzi.controller;

import com.noleggiomezzi.service.ManutenzioneService;
import com.noleggiomezzi.service.MezzoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

    // Questo controller si occupa di gestire le richieste relative al caso d'uso
    // - Effettua Manutenzione
    // Per la logica di business delega al ManutenzioneService
    
@Controller
public class ManutenzioneController {

    private final ManutenzioneService manutenzioneService;
    private final MezzoService mezzoService;

    public ManutenzioneController(ManutenzioneService manutenzioneService, MezzoService mezzoService) {
        this.manutenzioneService = manutenzioneService;
        this.mezzoService = mezzoService;
    }

    @GetMapping("/manutenzione")
    public String gestioneManutenzione(Model model) {
        model.addAttribute("mezziInFlotta", mezzoService.getTuttiMezzi());
        return "manutenzione";
    }

    @PostMapping("/manutenzione/invia")
    public String inviaInOfficina(@RequestParam int idMezzo) {
        manutenzioneService.inviaInRiparazione(idMezzo);
        return "redirect:/manutenzione?success_invio=true";
    }

    @PostMapping("/manutenzione/chiudi")
    public String chiudiManutenzione(@RequestParam int idMezzo, 
                                     @RequestParam String descrizione, 
                                     @RequestParam double costo, 
                                     @RequestParam String statoFinale) {
        manutenzioneService.registraInterventoERipristina(idMezzo, descrizione, costo, statoFinale);
        return "redirect:/manutenzione?success_riparazione=true";
    }
}