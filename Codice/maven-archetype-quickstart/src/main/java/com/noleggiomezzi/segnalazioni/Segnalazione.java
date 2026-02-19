package com.noleggiomezzi.segnalazioni;

import java.time.LocalDateTime;
import com.noleggiomezzi.model.Mezzo;

public abstract class Segnalazione {

    private LocalDateTime data;
    private String descrizione;
    private int idNoleggio;

    public Segnalazione(){

    }

    public Segnalazione(int idNoleggio, String descrizione) {
        this.data = LocalDateTime.now();
        this.descrizione = descrizione;
        this.idNoleggio = idNoleggio;
    }

    public abstract void aggiornaStatoMezzo(Mezzo m);

    public abstract double calcolaPerdite();

    //Getters 

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    // Setters

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getIdNoleggio() {
        return idNoleggio;
    }

    public void setIdNoleggio(int idNoleggio) {
        this.idNoleggio = idNoleggio;
    }


}
