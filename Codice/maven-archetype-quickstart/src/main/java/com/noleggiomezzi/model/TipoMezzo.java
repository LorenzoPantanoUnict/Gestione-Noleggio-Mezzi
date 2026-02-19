package com.noleggiomezzi.model;
public class TipoMezzo {

    private String nome;
    private boolean isElettrico;
    private boolean richiedePatente;

    public TipoMezzo() { }

    public TipoMezzo(String nome,
                     boolean isElettrico,
                     boolean richiedePatente) {
        this.nome = nome;
        this.isElettrico = isElettrico;
        this.richiedePatente = richiedePatente;
    }

    // Getters

    public String getNome() {
        return nome;
    }

    public boolean isElettrico() {
        return isElettrico;
    }
    
    public boolean isRichiedePatente() {
        return richiedePatente;
    }

    //Setters

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setElettrico(boolean elettrico) {
        this.isElettrico = elettrico;
    }

    public void setRichiedePatente(boolean richiedePatente) {
        this.richiedePatente = richiedePatente;
    }
}
