package com.noleggiomezzi.controller;

import com.noleggiomezzi.service.MezzoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MezzoController {

    private MezzoService mezzoService;
    
    public MezzoController(MezzoService mezzoService) {
        this.mezzoService = mezzoService;
    }

    @GetMapping("/nuovo-mezzo")
    public String mostraFormNuovoMezzo(Model model) {
        model.addAttribute("listaSedi", mezzoService.getTutteSedi());
        return "nuovo-mezzo"; 
    }

    @PostMapping("/nuovo-mezzo")
    public String salvaNuovoMezzo(@RequestParam("id") int id,
                                @RequestParam("marca") String marca,
                                @RequestParam("modello") String modello,
                                @RequestParam("anno") int anno,
                                @RequestParam("cilindrata") int cilindrata,
                                @RequestParam("posti") int posti,
                                @RequestParam("tipo") String tipo,
                                @RequestParam("puntoNoleggioId") int puntoNoleggioId) {
        try {

            mezzoService.aggiungiNuovoMezzo(id, marca, modello, anno, cilindrata, posti, tipo, puntoNoleggioId);

            return "redirect:/catalogo";

        } catch (Exception e) {
            System.err.println("Errore nell'inserimento: " + e.getMessage());
            return "redirect:/nuovo-mezzo?errore=true";
        }
    }
    
    @GetMapping("/catalogo")
    public String mostraCatalogo(Model model) {
  
        model.addAttribute("listaMezzi", mezzoService.getTuttiMezzi()); 
        
        return "catalogo"; 
    }

    
}
