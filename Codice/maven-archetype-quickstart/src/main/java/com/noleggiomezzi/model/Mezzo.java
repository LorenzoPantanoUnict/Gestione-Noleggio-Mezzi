package com.noleggiomezzi.model;

import java.util.ArrayList; 
import java.util.List;

import com.noleggiomezzi.exceptions.StatoNonValidoException;      

public class Mezzo {

    private int id;
    private StatoMezzo stato;
    private double livelloCarica;
    private DescrizioneMezzo descrizione;
    private PuntoNoleggio puntoNoleggio;
    private TipoMezzo tipo;
    
    private List<InterventoManutenzione> interventi; 

    public Mezzo(int id, DescrizioneMezzo descrizione, PuntoNoleggio puntoNoleggio) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = StatoMezzo.DISPONIBILE;
        this.livelloCarica = 100.0;
        this.puntoNoleggio = puntoNoleggio;
        this.interventi = new ArrayList<>();
    }

    // public Mezzo(int id, DescrizioneMezzo descrizione) {
    //     this.id = id;
    //     this.descrizione = descrizione;
    //     this.stato = StatoMezzo.DISPONIBILE;
    //     this.livelloCarica = 100.0;
    //     this.interventi = new ArrayList<>(); 
    // }

    public void aggiungiIntervento(InterventoManutenzione i) {
        this.interventi.add(i);
    }

    public void setPuntoNoleggio(PuntoNoleggio punto) {
        this.puntoNoleggio = punto;
    }
    
    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }
    
    // Cambimenti di stato  per il mezzo
    
    public void inviaInManutenzione() {
        if (this.stato == StatoMezzo.NOLEGGIATO || this.stato == StatoMezzo.RUBATO) {
            throw new StatoNonValidoException("Impossibile inviare in manutenzione un mezzo noleggiato o rubato.");
        }
        this.stato = StatoMezzo.IN_MANUTENZIONE;
    }
    public void rendiDisponibile() {
        // Un mezzo può tornare disponibile solo se era noleggiato, in manutenzione o fuori servizio
        if (this.stato == StatoMezzo.RUBATO) {
            throw new StatoNonValidoException("Un mezzo rubato non può tornare direttamente disponibile senza procedure speciali.");
        }
        this.stato = StatoMezzo.DISPONIBILE;
    }

    public void impostaFuoriServizio() {
        this.stato = StatoMezzo.FUORI_SERVIZIO;
    }
    
    public void noleggia() {
        if (this.stato != StatoMezzo.DISPONIBILE) {
            throw new StatoNonValidoException("Impossibile noleggiare il mezzo: attualmente " + this.stato);
        }
        this.stato = StatoMezzo.NOLEGGIATO;
    }

    public void aggiornaLivelloCarica(double nuovaCarica) {
        if (nuovaCarica < 0.0 || nuovaCarica > 100.0) {
            throw new IllegalArgumentException("Il livello di carica deve essere tra 0 e 100.");
        }
        this.livelloCarica = nuovaCarica;
    }

    public void segnalaFurto() {
        if (this.stato == StatoMezzo.RUBATO) {
            throw new StatoNonValidoException("Il mezzo è già segnalato come rubato.");
        }
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
        if (this.tipo == null && this.descrizione != null) {
            return this.descrizione.getTipo();
        }
        return this.tipo;
    }

    public StatoMezzo getStato() {
        return stato;
    }

    public List<InterventoManutenzione> getInterventi() {
        return this.interventi;
    }

    public boolean isDisponibile() {
        return stato == StatoMezzo.DISPONIBILE;
    }

    public PuntoNoleggio getPuntoNoleggio() {
        return puntoNoleggio;
    }
}