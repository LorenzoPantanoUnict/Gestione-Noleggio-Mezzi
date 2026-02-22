package com.noleggiomezzi.model;

import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.utility.DateRange;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Prenotazione {
    private static int contatoreId = 1;

    private int id;
    private String pnr;
    private Cliente cliente;
    private Mezzo mezzo;
    private DateRange periodo; 
    private PuntoNoleggio puntoNoleggio;
    private double costoTotale; 


    public Prenotazione(Cliente cliente, Mezzo mezzo, DateRange periodo, PuntoNoleggio puntoNoleggio, ITariffa tariffaScelta) {
        this.id = contatoreId++;
        this.cliente = cliente;
        this.mezzo = mezzo;
        this.periodo = periodo;
        this.puntoNoleggio = puntoNoleggio;
        
        this.pnr = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        
        this.costoTotale = calcolaCostoPrenotazione(tariffaScelta);
    }

    private double calcolaCostoPrenotazione(ITariffa tariffa) {
        if (periodo == null) return 0.0;
        
        long minuti = ChronoUnit.MINUTES.between(periodo.getDataInizio(), periodo.getDataFine());
        
        double costoBase = tariffa.calcolaCosto((int) minuti, 0); 
        double moltiplicatore = mezzo.getTipo().getMoltiplicatore();
        
        return costoBase * moltiplicatore;
    }

    // Getters

    public double getCostoTotale() {
        return costoTotale;
    }

    public String getPnr() {
        return pnr;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public DateRange getPeriodo() {
        return periodo;
    }

    public PuntoNoleggio getPuntoNoleggio() {
        return puntoNoleggio;
    }
}