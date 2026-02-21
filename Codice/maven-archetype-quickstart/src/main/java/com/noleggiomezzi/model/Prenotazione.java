package com.noleggiomezzi.model;

import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.model.tariffe.TariffaGiornaliera;
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

    public Prenotazione(Cliente cliente, Mezzo mezzo, DateRange periodo, PuntoNoleggio puntoNoleggio) {
        this.id = contatoreId++;
        this.cliente = cliente;
        this.mezzo = mezzo;
        this.periodo = periodo;
        this.puntoNoleggio = puntoNoleggio;
        this.costoTotale = calcolaCostoStimato(); 
        
        generaPNR(); 
    }

    private void generaPNR() {
        this.pnr = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private double calcolaCostoStimato() {
        ITariffa tariffa = new TariffaGiornaliera();
        long durataMinuti = ChronoUnit.MINUTES.between(periodo.getDataInizio(), periodo.getDataFine());
        return tariffa.calcolaCosto((int) durataMinuti, 0);
    }

    public Cliente getCliente() { return cliente; }
    public DateRange getPeriodo() { return periodo; }
    public PuntoNoleggio getPuntoNoleggio() { return puntoNoleggio; }
    public double getCostoTotale() { return costoTotale; }
    public String getPnr() { return pnr; }
    public Mezzo getMezzo() { return mezzo; }
    public int getId() { return id; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setMezzo(Mezzo mezzo) { this.mezzo = mezzo; }
    public void setPeriodo(DateRange periodo) { this.periodo = periodo; }
    public void setPuntoNoleggio(PuntoNoleggio puntoNoleggio) { this.puntoNoleggio = puntoNoleggio; }
    public void setCostoTotale(double costoTotale) { this.costoTotale = costoTotale; }
    public void setPnr(String pnr) { this.pnr = pnr; }
}