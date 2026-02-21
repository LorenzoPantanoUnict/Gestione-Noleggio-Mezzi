package com.noleggiomezzi.controller;

import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.service.ManutenzioneService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

    // Questo controller si occupa di gestire le richieste relative al caso d'uso
    // - Effettua Manutenzione
    // Per la logica di business delega al ManutenzioneService
    
@Controller
public class ManutenzioneController {

    private final ManutenzioneService manutenzioneService;
    private final IMezzoRepository catalogoMezzi;

    public ManutenzioneController(ManutenzioneService ms, IMezzoRepository cm) {
        this.manutenzioneService = ms;
        this.catalogoMezzi = cm;
    }

    @GetMapping("/manutenzione")
    public String gestioneManutenzione(Model model) {
        model.addAttribute("mezziInFlotta", catalogoMezzi.findAll());
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