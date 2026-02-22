package com.noleggiomezzi.model;

import java.util.ArrayList; 
import java.util.List;
import com.noleggiomezzi.model.stati.*; 

public class Mezzo {

    private int id;
    private IStatoMezzo statoAttuale; 
    private double livelloCarica;
    private DescrizioneMezzo descrizione;
    private PuntoNoleggio puntoNoleggio;
    private TipoMezzo tipo;
    
    private List<InterventoManutenzione> interventi; 

    public Mezzo(int id, DescrizioneMezzo descrizione, PuntoNoleggio puntoNoleggio) {
        this.id = id;
        this.descrizione = descrizione;
        this.statoAttuale = new StatoDisponibile();  // stato di default
        this.livelloCarica = 100.0;
        this.puntoNoleggio = puntoNoleggio;
        this.interventi = new ArrayList<>();
    }
    
    /**
     * La logica di transizione dello stato è delegata
     * alle singole classi stato
     */
    public void setStato(IStatoMezzo nuovoStato) {
        this.statoAttuale = nuovoStato;
    }

    // --- DELEGAZIONI ALLO STATO 

    public void noleggia() {
        statoAttuale.noleggia(this);
    }

    public void rendiDisponibile() {
        statoAttuale.restituisci(this);
    }

    public void inviaInManutenzione() {
        statoAttuale.inviaInManutenzione(this);
    }

    public void segnalaFurto() {
        statoAttuale.segnaComeRubato(this);
    }
    
    public void impostaFuoriServizio() {
        statoAttuale.impostaFuoriServizio(this);
    }

    public boolean isDisponibile() {
        return statoAttuale.isDisponibile();
    }

    // Metodi 

    public void aggiungiIntervento(InterventoManutenzione i) {
        this.interventi.add(i);
    }

    public void aggiornaLivelloCarica(double nuovaCarica) {
        if (nuovaCarica < 0.0 || nuovaCarica > 100.0) {
            throw new IllegalArgumentException("Il livello di carica deve essere tra 0 e 100.");
        }
        this.livelloCarica = nuovaCarica;
    }

    // --- GETTERS ---

    public int getId() {
        return id;
    }

    public double getLivelloCarica() {
        return livelloCarica;
    }

    public DescrizioneMezzo getDescrizione() {
        return descrizione;
    }

    public PuntoNoleggio getPuntoNoleggio() {
        return puntoNoleggio;
    }

    public void setPuntoNoleggio(PuntoNoleggio punto) {
        this.puntoNoleggio = punto;
    }

    public TipoMezzo getTipo() {
        if (this.tipo == null && this.descrizione != null) {
            return this.descrizione.getTipo();
        }
        return this.tipo;
    }

    /**
     *
     * 
     */
    public String getStato() {
        return statoAttuale.getNomeStato();
    }

    public List<InterventoManutenzione> getInterventi() {
        return this.interventi;
    }
}