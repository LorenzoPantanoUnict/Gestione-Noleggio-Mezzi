package com.noleggiomezzi.model;

import java.util.ArrayList; 
import java.util.List;      

public class Mezzo {

    private int id;
    private StatoMezzo stato;
    private double livelloCarica;
    DescrizioneMezzo descrizione;
    PuntoNoleggio puntoNoleggio;
    private TipoMezzo tipo;
    
    private List<InterventoManutenzione> interventi; 

    public Mezzo(int id, DescrizioneMezzo descrizione, PuntoNoleggio puntoNoleggio) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = StatoMezzo.DISPONIBILE;
        this.livelloCarica = 100.0;
        this.puntoNoleggio = puntoNoleggio;
    }

    public Mezzo(int id, DescrizioneMezzo descrizione) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = StatoMezzo.DISPONIBILE;
        this.livelloCarica = 100.0;
        
        this.interventi = new ArrayList<>(); 
    }

    public void aggiungiIntervento(InterventoManutenzione i) {
        this.interventi.add(i);
    }
    
    public List<InterventoManutenzione> getInterventi() {
        return this.interventi;
    }
    
    public void setPuntoNoleggio(PuntoNoleggio punto) {
        this.puntoNoleggio = punto;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public void setStatoDisponibile() {
        this.stato = StatoMezzo.DISPONIBILE;
    }

    public void setStatoFuoriServizio() {
        this.stato = StatoMezzo.FUORI_SERVIZIO;
    }
    
    public void setStatoNoleggiato() {
        this.stato = StatoMezzo.NOLEGGIATO;
    }

    public void setLivelloCarica(double livelloCarica) {
        this.livelloCarica = livelloCarica;
    }

    public void setStatoRubato() {
        this.stato = StatoMezzo.RUBATO;
    }

    public double getLivelloCarica() {
        return livelloCarica;
    }

    public int getId() {
        return id;
    }

    public DescrizioneMezzo getDescrizione() {
        return descrizione;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    public StatoMezzo getStato() {
        return stato;
    }

    public boolean isDisponibile() {
        return stato == StatoMezzo.DISPONIBILE;
    }
}