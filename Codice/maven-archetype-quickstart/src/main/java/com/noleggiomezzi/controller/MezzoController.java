package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.utility.MezzoBuilder;
import com.noleggiomezzi.repository.interfacce.ICatalogoTipoMezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MezzoController {

    private IMezzoRepository catalogoMezzi;
    private IPuntoNoleggioRepository puntoRepo;
    private ICatalogoTipoMezzo catalogoTipoMezzo;

    public MezzoController(IMezzoRepository catalogoMezzi, IPuntoNoleggioRepository puntoRepo, ICatalogoTipoMezzo catalogoTipoMezzo) {
        this.catalogoMezzi = catalogoMezzi;
        this.puntoRepo = puntoRepo;
        this.catalogoTipoMezzo = catalogoTipoMezzo;
    }

    @GetMapping("/nuovo-mezzo")
    public String mostraFormNuovoMezzo(Model model) {
        model.addAttribute("listaSedi", puntoRepo.findAll());
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
            

            PuntoNoleggio puntoNoleggio = puntoRepo.getPuntoById(puntoNoleggioId);

            aggiungiNuovoMezzo(id, marca, modello, anno, cilindrata, posti, tipo, puntoNoleggio);

            return "redirect:/catalogo";

        } catch (Exception e) {
            System.err.println("Errore nell'inserimento: " + e.getMessage());
            return "redirect:/nuovo-mezzo?errore=true";
        }
    }
    
    @GetMapping("/catalogo")
    public String mostraCatalogo(Model model) {
  
        model.addAttribute("listaMezzi", catalogoMezzi.findAll()); 
        
        return "catalogo"; 
    }

    public void aggiungiNuovoMezzo(int id, String marca, String modello, 
                            int anno, int cilindrata, int posti,
                            String tipo, PuntoNoleggio puntoNoleggio){

        // Controllo se esite già un mezzo con lo stesso ID
        if(catalogoMezzi.esisteMezzo(id)){
            throw new IllegalArgumentException("Esiste già un mezzo con ID " + id);
        }; 

        TipoMezzo tipoMezzo = catalogoTipoMezzo.getTipoMezzo(tipo);
        
        MezzoBuilder mezzoBuilder = new MezzoBuilder();

        Mezzo nuovoMezzo = mezzoBuilder.conId(id)
                                        .diMarca(marca)
                                        .modello(modello)
                                        .immatricolatoNel(anno)
                                        .conCilindrata(cilindrata)
                                        .conNumeroPosti(posti)
                                        .diTipo(tipoMezzo)
                                        .allocatoPresso(puntoNoleggio)
                                        .build();

        catalogoMezzi.aggiungiMezzo(nuovoMezzo);
    }

    
}
