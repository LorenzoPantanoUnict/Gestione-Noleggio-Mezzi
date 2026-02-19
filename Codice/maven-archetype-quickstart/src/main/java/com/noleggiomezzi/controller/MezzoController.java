package com.noleggiomezzi.controller;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.utility.MezzoFactory;

public class MezzoController {

    private CatalogoMezzi catalogoMezzi;
    private MezzoFactory mezzoFactory;
    
    public void aggiungiNuovoMezzo(int id, String marca, String modello, 
                            int anno, int cilindrata, int posti,
                            String tipo, PuntoNoleggio puntoNoleggio){

        // Controllo se esite già un mezzo con lo stesso ID
        if(catalogoMezzi.esisteMezzo(id)){
            throw new IllegalArgumentException("Esiste già un mezzo con ID " + id);
        }; 

        TipoMezzo tipoMezzo = catalogoMezzi.getTipoMezzo(tipo);
        
        Mezzo nuovoMezzo = mezzoFactory.creaMezzo(id, marca, modello, anno, cilindrata,
                                                 posti, tipoMezzo, puntoNoleggio);

        catalogoMezzi.aggiungiMezzo(nuovoMezzo);
    }
}
