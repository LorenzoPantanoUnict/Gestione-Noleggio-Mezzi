package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.service.NoleggioService; 
import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;
import com.noleggiomezzi.repository.interfacce.ITariffaRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
* NoleggioController si occupa di gestire le richieste relatiec ai casi d'uso:
*
* - Avvia Noleggio
* - Concludi Noleggio
* - Segnala Furto
*
*  Per ogni caso d'uso delega la logica di business al NoleggioSerivice
*/
@Controller
public class NoleggioController {

    private final NoleggioService noleggioService;
    private final IClienteRepository registroClienti;
    private final IMezzoRepository catalogoMezzi;
    private final IPuntoNoleggioRepository registroPuntiNoleggio;
    private final ITariffaRepository tariffaRepo;

    // Spring inietta in automatico il Service e i due Repository necessari per le tendine
    public NoleggioController(NoleggioService noleggioService, IClienteRepository registroClienti,
                         IMezzoRepository catalogoMezzi, IPuntoNoleggioRepository registroPuntiNoleggio,
                         ITariffaRepository tariffaRepo) { 
        this.noleggioService = noleggioService;
        this.registroClienti = registroClienti;
        this.catalogoMezzi = catalogoMezzi;
        this.registroPuntiNoleggio = registroPuntiNoleggio;
        this.tariffaRepo = tariffaRepo;
    }



    // Avvia Noleggio

    @GetMapping("/avvia-noleggio")
    public String mostraFormAvvio(Model model) {
        model.addAttribute("listaClienti", registroClienti.findAll());
        model.addAttribute("mezziDisponibili", catalogoMezzi.findAll().stream()
                .filter(Mezzo::isDisponibile).toList());
        model.addAttribute("listaSedi", registroPuntiNoleggio.findAll());
        
        model.addAttribute("listaTariffe", tariffaRepo.findAll()); 

        return "avvia-noleggio";
    }

    @PostMapping("/avvia-noleggio")
    public String avviaNoleggioRequest(
            @RequestParam("clienteId") int clienteId, 
            @RequestParam("mezzoId") int mezzoId,
            @RequestParam("tariffa") String tariffa, 
            @RequestParam("puntoNoleggioId") int puntoNoleggioId){
            
        try {
            noleggioService.avviaNoleggio(clienteId, mezzoId, tariffa, puntoNoleggioId);
            
            return "redirect:/avvia-noleggio?success=true";
            
        } catch (Exception e) {
            System.err.println("Errore avvio noleggio: " + e.getMessage());
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
            System.err.println("Errore chiusura noleggio: " + e.getMessage());
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
            System.err.println("Errore segnalazione furto: " + e.getMessage());
            return "redirect:/segnala-furto?error=true";
        }
    }
}