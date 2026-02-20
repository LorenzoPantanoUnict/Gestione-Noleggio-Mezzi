package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.utility.MezzoBuilder;

public class MezzoController {

    private CatalogoMezzi catalogoMezzi;
    private MezzoBuilder mezzoBuilder;
    
    public void aggiungiNuovoMezzo(int id, String marca, String modello, 
                            int anno, int cilindrata, int posti,
                            String tipo, PuntoNoleggio puntoNoleggio){

        // Controllo se esite già un mezzo con lo stesso ID
        if(catalogoMezzi.esisteMezzo(id)){
            throw new IllegalArgumentException("Esiste già un mezzo con ID " + id);
        }; 

        TipoMezzo tipoMezzo = catalogoMezzi.getTipoMezzo(tipo);
        
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
