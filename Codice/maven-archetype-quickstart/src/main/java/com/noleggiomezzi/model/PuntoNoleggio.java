package com.noleggiomezzi.model;
import java.util.ArrayList;
import java.util.List;

public class PuntoNoleggio {

    private int id;
    private String nome;
    private String indirizzo;

    // Mezzi presenti nel punto
    private List<Mezzo> inventario;

    // Capacità massima (opzionale)
    private int capacitaMassima;

    public PuntoNoleggio(int id,
                         String nome,
                         String indirizzo,
                         int capacitaMassima) {

        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.capacitaMassima = capacitaMassima;
        this.inventario = new ArrayList<>();
    }

    public void rilasciaMezzo(Mezzo m) {

        if (isPieno()) {
            throw new RuntimeException(
                "Punto noleggio pieno"
            );
        }

        inventario.add(m);
        m.setPuntoNoleggio(this);
    }

    public void accettaRestituzione(Mezzo m) {

        if (isPieno()) {
            throw new RuntimeException(
                "Impossibile accettare restituzione"
            );
        }

        inventario.add(m);
        m.setStatoDisponibile();

        m.setPuntoNoleggio(this);
    }

    public void prelevaMezzo(Mezzo m) {

        inventario.remove(m);
        
        m.setStatoNoleggiato();
    }


    public boolean isPieno() {
        return inventario.size() >= capacitaMassima;
    }

    public int getMezziDisponibili() {
        return inventario.size();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public List<Mezzo> getInventario() {
        return inventario;
    }
}