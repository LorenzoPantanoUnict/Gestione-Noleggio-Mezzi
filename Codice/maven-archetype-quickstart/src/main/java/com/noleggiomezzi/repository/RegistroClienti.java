package com.noleggiomezzi.repository;

import java.util.HashMap;
import java.util.Map;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroClienti implements IClienteRepository {

    private Map<Integer, Cliente> mappaClienti;

    public RegistroClienti() {
        mappaClienti = new HashMap<>();

        // Dati di prova
        Cliente c = new Cliente("Mario", "Rossi", "mario.rossi@email.it", "mario123");
        mappaClienti.put(c.getId(), c);
    }

    public boolean emailEsistente(String email) {
        return mappaClienti.values()
                .stream()
                .anyMatch(c ->
                    c.getEmail().equals(email));
    }

    public Cliente getClienteById(int id) {
        return mappaClienti.get(id);
    }

    public void aggiungiCliente(Cliente c) {
        mappaClienti.put(c.getId(), c);
    }

    public boolean esiste(int id) {
        return mappaClienti.containsKey(id);
    }

    public List<Cliente> findAll() {
        return new ArrayList<>(mappaClienti.values());
    }

}