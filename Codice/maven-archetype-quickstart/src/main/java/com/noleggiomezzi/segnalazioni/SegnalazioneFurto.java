package com.noleggiomezzi.segnalazioni;


import com.noleggiomezzi.model.Mezzo;

public class SegnalazioneFurto extends Segnalazione {

    private static final double PERDITA_FURTO = 100.0; // Valore fisso per esempio

    public SegnalazioneFurto(int idNoleggio, String descrizione) {
        super(idNoleggio, descrizione);
    }

    @Override
    public void aggiornaStatoMezzo(Mezzo m) {
        m.segnalaFurto();
    }

    @Override
    public double calcolaPerdite() {
        // Perdita totale del mezzo
        return PERDITA_FURTO; // Valore fisso per esempio
    }

}
