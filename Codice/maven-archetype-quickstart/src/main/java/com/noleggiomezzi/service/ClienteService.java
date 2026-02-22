package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final IClienteRepository registroClienti;

    public ClienteService(IClienteRepository registroClienti) {
        this.registroClienti = registroClienti;
    }


    public void registraNuovoCliente(String nome, String cognome, String email, String password) {
        
        //Validazione campi
        if(email == null || nome == null || cognome == null || password == null || password.isEmpty()){
            throw new IllegalArgumentException("Tutti i campi, inclusa la password, sono obbligatori");
        }

        // Controllo unicità email 
        boolean emailGiaEsistente = registroClienti.findAll().stream()
                .filter(c -> c.getEmail() != null)
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
        
        if (emailGiaEsistente) {
            throw new IllegalArgumentException("Errore: Esiste già un cliente registrato con l'email " + email);
        }

        // Creazione cliente con password (assicurati che il costruttore di Cliente sia aggiornato)
        Cliente nuovoCliente = new Cliente(nome, cognome, email, password);
        
        registroClienti.aggiungiCliente(nuovoCliente);
    }
    
    public void sospendiCliente(int idCliente) {
        Cliente c = registroClienti.getClienteById(idCliente);
        if (c != null) {
            c.sospendiAccount(); 
        }
    }

    public void riabilitaCliente(int idCliente) {
        Cliente c = registroClienti.getClienteById(idCliente);
        if (c != null) {
            c.riattivaAccount(); 
        }
    }

    public List<Cliente> getTuttiClienti(){
        return registroClienti.findAll();
    }

}