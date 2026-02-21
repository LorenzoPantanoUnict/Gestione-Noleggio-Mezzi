package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.service.NoleggioService; 
import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;
import com.noleggiomezzi.repository.interfacce.ITariffaRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    @GetMapping("/avvia-noleggio")
    public String mostraFormAvvio(Model model) {
        model.addAttribute("listaClienti", registroClienti.findAll());
        model.addAttribute("mezziDisponibili", catalogoMezzi.findAll().stream()
                .filter(Mezzo::isDisponibile).toList());
        model.addAttribute("listaSedi", registroPuntiNoleggio.findAll());
        
        // PASSA GLI OGGETTI, NON LE STRINGHE
        model.addAttribute("listaTariffe", tariffaRepo.findAll()); 

        return "avvia-noleggio";
    }

    // --- ROTTA POST: RICEVE I DATI E DELEGA AL SERVICE ---
    @PostMapping("/avvia-noleggio")
    public String avviaNoleggioRequest(
            @RequestParam("clienteId") int clienteId, 
            @RequestParam("mezzoId") int mezzoId,
            @RequestParam("tariffa") String tariffa, 
            @RequestParam("puntoNoleggioId") int puntoNoleggioId){
            
        try {
            // Guarda quanto è pulito il Controller! Non c'è logica, fa solo da passacarte:
            noleggioService.avviaNoleggio(clienteId, mezzoId, tariffa, puntoNoleggioId);
            
            return "redirect:/avvia-noleggio?success=true";
            
        } catch (Exception e) {
            System.err.println("Errore avvio noleggio: " + e.getMessage());
            return "redirect:/avvia-noleggio?error=true";
        }
    }
}