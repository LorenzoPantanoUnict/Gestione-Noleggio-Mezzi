package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.interfacce.ICatalogoTipoMezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;
import com.noleggiomezzi.utility.MezzoBuilder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MezzoService {

    private final IMezzoRepository catalogoMezzi;
    private final IPuntoNoleggioRepository puntoRepo;
    private final ICatalogoTipoMezzo catalogoTipoMezzo;

    public MezzoService(IMezzoRepository catalogoMezzi, 
                        ICatalogoTipoMezzo catalogoTipoMezzo,
                        IPuntoNoleggioRepository puntoRepo) {
        this.catalogoMezzi = catalogoMezzi;
        this.catalogoTipoMezzo = catalogoTipoMezzo;
        this.puntoRepo = puntoRepo;
    }

    public void aggiungiNuovoMezzo(int id, String marca, String modello, 
                            int anno, int cilindrata, int posti,
                            String tipo, int puntoNoleggioId){

        // Controllo se esite già un mezzo con lo stesso ID
        if(catalogoMezzi.esisteMezzo(id)){
            throw new IllegalArgumentException("Esiste già un mezzo con ID " + id);
        }; 

        PuntoNoleggio puntoNole = puntoRepo.getPuntoById(puntoNoleggioId);

        TipoMezzo tipoMezzo = catalogoTipoMezzo.getTipoMezzo(tipo);
        
        MezzoBuilder mezzoBuilder = new MezzoBuilder();

        Mezzo nuovoMezzo = mezzoBuilder.conId(id)
                                        .diMarca(marca)
                                        .modello(modello)
                                        .immatricolatoNel(anno)
                                        .conCilindrata(cilindrata)
                                        .conNumeroPosti(posti)
                                        .diTipo(tipoMezzo)
                                        .allocatoPresso(puntoNole)
                                        .build();

        catalogoMezzi.aggiungiMezzo(nuovoMezzo);
    }

    public List<Mezzo> getTuttiMezzi() {
        return catalogoMezzi.findAll();
    }

    public List<PuntoNoleggio> getTutteSedi(){
        return puntoRepo.findAll();
    }
}