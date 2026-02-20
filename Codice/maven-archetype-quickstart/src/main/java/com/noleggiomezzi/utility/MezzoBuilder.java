package com.noleggiomezzi.utility;

import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.model.DescrizioneMezzo;

public class MezzoBuilder {
    // Parametri obbligatori o opzionali
    private int id;
    private String marca;
    private String modello;
    private int anno;
    private int cilindrata;
    private int posti;
    private TipoMezzo tipo;
    private PuntoNoleggio puntoNoleggio;

    // Metodi "setter" che ritornano il Builder stesso 
    public MezzoBuilder conId(int id) {
        this.id = id;
        return this;
    }

    public MezzoBuilder diMarca(String marca) {
        this.marca = marca;
        return this;
    }

    public MezzoBuilder modello(String modello) {
        this.modello = modello;
        return this;
    }

    public MezzoBuilder immatricolatoNel(int anno) {
        this.anno = anno;
        return this;
    }

    public MezzoBuilder conCilindrata(int cilindrata) {
        this.cilindrata = cilindrata;
        return this;
    }

    public MezzoBuilder conNumeroPosti(int posti) {
        this.posti = posti;
        return this;
    }

    public MezzoBuilder diTipo(TipoMezzo tipo) {
        this.tipo = tipo;
        return this;
    }

    public MezzoBuilder allocatoPresso(PuntoNoleggio puntoNoleggio) {
        this.puntoNoleggio = puntoNoleggio;
        return this;
    }

    
    public Mezzo build() {
        
        if (marca == null || modello == null) {
            throw new IllegalStateException("Marca e modello sono obbligatori per creare un mezzo.");
        }
        if (anno < 1900) {
            throw new IllegalStateException("Anno di immatricolazione non valido.");
        }

        DescrizioneMezzo descrizioneMezzo = new DescrizioneMezzo(marca, modello, anno, cilindrata, posti, tipo);
        
        return new Mezzo(id, descrizioneMezzo, puntoNoleggio);
    }
}