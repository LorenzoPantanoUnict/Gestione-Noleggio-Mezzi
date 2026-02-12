package com.noleggiomezzi.repository;

import java.util.HashMap;
import java.util.Map;

import com.noleggiomezzi.model.Cliente;


public class RegistroClienti {

    private static RegistroClienti instance;

    private Map<Integer, Cliente> mappaClienti;

    private RegistroClienti() {
        mappaClienti = new HashMap<>();
    }
    public static RegistroClienti getInstance() {

        if (instance == null) {
            instance = new RegistroClienti();
        }

        return instance;
    }
    
    public Cliente getCliente(int id) {
        return mappaClienti.get(id);
    }

    public void aggiungiCliente(Cliente c) {
        mappaClienti.put(c.getId(), c);
    }

    public boolean esiste(int id) {
        return mappaClienti.containsKey(id);
    }

    public void rimuoviCliente(int id) {
        mappaClienti.remove(id);
    }

    public int getClientiSospesi() {

        int count = 0;

        for (Cliente c : mappaClienti.values()) {
            if (!c.isAffidabile()) {
                count++;
            }
        }

        return count;
    }
}