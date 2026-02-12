package com.noleggiomezzi.model;
public class Mezzo {

    private int id;
    private StatoMezzo stato;
    private double livelloCarica;
    DescrizioneMezzo descrizione;
    PuntoNoleggio puntoNoleggio;
    private TipoMezzo tipo;

    public Mezzo(int id, DescrizioneMezzo descrizione) {
        this.id = id;
        this.descrizione = descrizione;
        this.stato = StatoMezzo.DISPONIBILE;
        this.livelloCarica = 100.0;
    }

    // Setters

    public void setPuntoNoleggio(PuntoNoleggio punto) {
        this.puntoNoleggio = punto;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public void setStatoDisponibile() {
        this.stato = StatoMezzo.DISPONIBILE;
    }

    public void setStatoNoleggiato() {
        this.stato = StatoMezzo.NOLEGGIATO;
    }

    public void setLivelloCarica(double livelloCarica) {
        this.livelloCarica = livelloCarica;
    }

    // Getters

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

    // Altri metodi

    public boolean isDisponibile() {
        return stato == StatoMezzo.DISPONIBILE;
    }
}