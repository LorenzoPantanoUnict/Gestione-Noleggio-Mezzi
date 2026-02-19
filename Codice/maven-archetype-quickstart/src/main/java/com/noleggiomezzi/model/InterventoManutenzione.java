package com.noleggiomezzi.model;

import java.time.LocalDateTime;

public class InterventoManutenzione {
    private static int contatoreId = 1; // Per auto-generare l'ID
    
    private int id;
    private LocalDateTime data;
    private String descrizione;
    private double costo;
    private String tipologia;

    public InterventoManutenzione(LocalDateTime data, String descrizione, double costo) {
        this.id = contatoreId++;
        this.data = data;
        this.descrizione = descrizione;
        this.costo = costo;
    }

    public int getId() { return id; }
    public double getCosto() { return costo; }
    public String getDescrizione() { return descrizione; }
    public LocalDateTime getData() { return data; }
    public String getTipologia() { return tipologia; }

    public void setData(LocalDateTime data) { this.data = data; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public void setCosto(double costo) { this.costo = costo; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }
}