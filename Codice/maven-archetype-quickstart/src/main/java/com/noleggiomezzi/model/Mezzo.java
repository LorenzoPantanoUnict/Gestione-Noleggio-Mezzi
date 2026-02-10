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

    public boolean isDisponibile() {
        return stato == StatoMezzo.DISPONIBILE;
    }

    public void aggiornaStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public void setLivelloCarica(double livelloCarica) {
        this.livelloCarica = livelloCarica;
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

    public void setPuntoNoleggio(PuntoNoleggio punto) {
        this.puntoNoleggio = punto;
    }
}