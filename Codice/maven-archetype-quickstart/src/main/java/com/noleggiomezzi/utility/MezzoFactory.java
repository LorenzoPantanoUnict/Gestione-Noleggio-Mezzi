package com.noleggiomezzi.utility;


import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.model.DescrizioneMezzo;



public class MezzoFactory {

    public Mezzo creaMezzo(int id, String marca, String modello, 
                            int anno, int cilindrata, int posti,
                            TipoMezzo tipo, PuntoNoleggio puntoNoleggio)
    {

        // Creazione descrizione Mezzo
        DescrizioneMezzo descrizioneMezzo = new DescrizioneMezzo(marca, modello, anno, cilindrata, posti, tipo);

        Mezzo nuovoMezzo = new Mezzo(id, descrizioneMezzo, puntoNoleggio);

        return nuovoMezzo;
    }
    
}
