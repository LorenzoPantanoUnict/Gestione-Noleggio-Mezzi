package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.*;
import com.noleggiomezzi.service.MezzoService; 
import com.noleggiomezzi.utility.DateRange;
import com.noleggiomezzi.exceptions.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/prenota")
public class PrenotazioneController {

    private final MezzoService mezzoService; 
    private final CatalogoMezzi catalogoMezzi; 
    private final RegistroPrenotazioni registroPrenotazioni;
    private final RegistroPuntiNoleggio registroSedi;
    private final CatalogoTariffe catalogoTariffe;
    private final CatalogoTipoMezzi catalogoTipiMezzo;

    public PrenotazioneController(MezzoService mezzoService,
                                  CatalogoMezzi catalogoMezzi,
                                  RegistroPrenotazioni registroPrenotazioni, 
                                  RegistroPuntiNoleggio registroSedi, 
                                  CatalogoTariffe catalogoTariffe,
                                  CatalogoTipoMezzi catalogoTipiMezzo) {
        this.mezzoService = mezzoService;
        this.catalogoMezzi = catalogoMezzi;
        this.registroPrenotazioni = registroPrenotazioni;
        this.registroSedi = registroSedi;
        this.catalogoTariffe = catalogoTariffe;
        this.catalogoTipiMezzo = catalogoTipiMezzo;
    }

    @GetMapping("/ricerca")
    public String mostraRicerca(Model model) {
        model.addAttribute("sedi", registroSedi.findAll());
        model.addAttribute("tipi", catalogoTipiMezzo.findAll()); 
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

        TipoMezzo tipo = catalogoTipiMezzo.getTipoMezzo(tipoNome);

        // Chiamata corretta al Service
        List<Mezzo> disponibili = mezzoService.verificaDisponibilitaCompleta(tipo, periodo, sedeId);
        
        model.addAttribute("mezzi", disponibili);
        model.addAttribute("tariffe", catalogoTariffe.findAll());
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

        Mezzo m = catalogoMezzi.getMezzoById(mezzoId);
        PuntoNoleggio sede = registroSedi.getPuntoById(sedeId);
        ITariffa tariffa = catalogoTariffe.getTariffaByName(tariffaNome);

        if (tariffa == null) {
            throw new IllegalArgumentException("Errore critico: la tariffa selezionata non esiste nel catalogo.");
        }
        
        String pnr = creaNuovaPrenotazione(cliente, m, periodo, sede, tariffa);
        
        session.removeAttribute("periodoPrenotazione");
        
        return "redirect:/prenota/successo?pnr=" + pnr;
    }

    public String creaNuovaPrenotazione(Cliente cliente, Mezzo mezzo, DateRange periodo, PuntoNoleggio sede, ITariffa tariffa) {
        if (!cliente.isAffidabile()) {
            throw new StatoNonValidoException("Cliente non affidabile.");
        }

        Prenotazione nuova = new Prenotazione(cliente, mezzo, periodo, sede, tariffa);
        registroPrenotazioni.aggiungiPrenotazione(nuova);
        mezzo.prenota(); 
        
        return nuova.getPnr();
    }

    @GetMapping("/successo")
    public String mostraSuccesso(@RequestParam String pnr, Model model) {
        model.addAttribute("pnr", pnr);
        return "successo";
    }
}