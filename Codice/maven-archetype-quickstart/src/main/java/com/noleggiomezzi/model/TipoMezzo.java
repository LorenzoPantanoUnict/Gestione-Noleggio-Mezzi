package com.noleggiomezzi.model;
public class TipoMezzo {

    private String nome;
    private boolean isElettrico;
    private boolean richiedePatente;

    public TipoMezzo(String nome,
                     boolean isElettrico,
                     boolean richiedePatente) {
        this.nome = nome;
        this.isElettrico = isElettrico;
        this.richiedePatente = richiedePatente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isElettrico() {
        return isElettrico;
    }

    public void setElettrico(boolean elettrico) {
        this.isElettrico = elettrico;
    }

    public boolean isRichiedePatente() {
        return richiedePatente;
    }

    public void setRichiedePatente(boolean richiedePatente) {
        this.richiedePatente = richiedePatente;
    }
}
