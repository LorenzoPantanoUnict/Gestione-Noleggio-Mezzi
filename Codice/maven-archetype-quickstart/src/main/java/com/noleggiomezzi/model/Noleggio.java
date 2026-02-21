package com.noleggiomezzi.model;
import java.time.LocalDateTime;

import com.noleggiomezzi.model.enums.StatoNoleggio;
import com.noleggiomezzi.model.enums.StatoPagamento;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.segnalazioni.Segnalazione;

import java.util.ArrayList;
import java.util.List;

public class Noleggio {
    private static int counter = 1;

    private int id;
    private LocalDateTime dataInizio;
    private int kmIniziali;
    private double costoTotale; 
    
    private Cliente cliente;
    private Mezzo mezzo;
    private ITariffa tariffa;
    private PuntoNoleggio puntoNoleggio;
    
    private StatoNoleggio statoNoleggio;
    private StatoPagamento statoPagamento;

    private List<Segnalazione> segnalazioni;

    public Noleggio(Cliente cliente, Mezzo mezzo, ITariffa tariffa, PuntoNoleggio punto) {
        this.id = counter++;
        this.cliente = cliente;
        this.mezzo = mezzo;
        this.tariffa = tariffa;
        this.puntoNoleggio = punto;
        this.dataInizio = LocalDateTime.now();
        this.statoNoleggio = StatoNoleggio.ATTIVO;
        this.kmIniziali = 0;
        segnalazioni = new ArrayList<>();
    }

    public void chiudi(PuntoNoleggio puntoConsegna) {
        this.puntoNoleggio = puntoConsegna;
        this.statoNoleggio = StatoNoleggio.CONCLUSO;
    }

    public double calcolaCostoFinale(int kmFinali, double durataMinuti) {
        
        int kmPercorsi = kmFinali - this.kmIniziali;
        
        this.costoTotale = tariffa.calcolaCosto((int) durataMinuti, kmPercorsi);
        return this.costoTotale;
    }

    public void chiudi(){
        this.statoNoleggio = StatoNoleggio.CONCLUSO;
        this.statoPagamento = StatoPagamento.PAGATO;
    }

    public boolean gestisciSegnalazione(Segnalazione s){
        this.segnalazioni.add(s);
        s.aggiornaStatoMezzo(mezzo);
        double perdite = s.calcolaPerdite();
        boolean pagamentoEffettuato = cliente.addebbitaImporto(perdite);
        return pagamentoEffettuato;
    }


   // Getters

    public int getId() { return id; }

    public Mezzo getMezzo() { return mezzo; }

    public Cliente getCliente() { return cliente; }

    public ITariffa getTariffa() { return tariffa; }

    public PuntoNoleggio getPuntoNoleggio() { return puntoNoleggio; }

    public StatoNoleggio getStatoNoleggio() { return statoNoleggio; }

    public StatoPagamento getStatoPagamento() { return statoPagamento; }

    public LocalDateTime getDataInizio() { return dataInizio; }

    //Setters

    public void setLivelloCarica(double livelloCarica) {
        this.mezzo.aggiornaLivelloCarica(livelloCarica);
    }
}
