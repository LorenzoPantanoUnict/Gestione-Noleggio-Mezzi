package com.noleggiomezzi.model;
public class TipoMezzo {

    private String nome;
    private boolean isElettrico;
    private boolean richiedePatente;
    private double moltiplicatoreTariffa;

    public TipoMezzo() { }

    public TipoMezzo(String nome,
                     boolean isElettrico,
                     boolean richiedePatente,
                     double moltiplicatoreTariffa) {
        this.nome = nome;
        this.isElettrico = isElettrico;
        this.richiedePatente = richiedePatente;
        this.moltiplicatoreTariffa = moltiplicatoreTariffa;
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

    public double getMoltiplicatore(){
        return this.moltiplicatoreTariffa;
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
