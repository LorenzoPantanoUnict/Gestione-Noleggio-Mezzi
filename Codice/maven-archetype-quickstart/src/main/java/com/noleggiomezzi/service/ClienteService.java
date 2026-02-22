package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final IClienteRepository registroClienti;

    public ClienteService(IClienteRepository registroClienti) {
        this.registroClienti = registroClienti;
    }


    public void registraNuovoCliente(String nome, String cognome, String email) {
    
        if(email == null || nome == null || cognome == null){
            throw new IllegalArgumentException("i campi per registrare un utente non devono essere nulli");
        }

        boolean emailGiaEsistente = registroClienti.findAll().stream()
                .filter(c -> c.getEmail() != null)
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
        
        if (emailGiaEsistente) {
            throw new IllegalArgumentException("Errore: Esiste già un cliente registrato con l'email " + email);
        }

        Cliente nuovoCliente = new Cliente(nome, cognome, email);
        
        registroClienti.aggiungiCliente(nuovoCliente);
    }

}