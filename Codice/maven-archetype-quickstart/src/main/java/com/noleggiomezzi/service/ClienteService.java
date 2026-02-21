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


    public void registraNuovoCliente(String nome, String cognome, String email) {
    
        boolean emailGiaEsistente = registroClienti.findAll().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
        
        if (emailGiaEsistente) {
            throw new IllegalArgumentException("Errore: Esiste già un cliente registrato con l'email " + email);
        }

        Cliente nuovoCliente = new Cliente(nome, cognome, email);
        
        registroClienti.aggiungiCliente(nuovoCliente);
    }

    // --- GESTIONE LETTURA ---

    public List<Cliente> getTuttiIClienti() {
        return registroClienti.findAll();
    }
    
    public Cliente getClienteById(int id) {
        return registroClienti.getClienteById(id);
    }

    // --- GESTIONE BLACKLIST ---

    public void bloccaCliente(int idCliente, String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("È obbligatorio specificare un motivo per la blacklist.");
        }
        
        Cliente c = registroClienti.getClienteById(idCliente);
        //c.inserisciInBlacklist(motivo);
        
    }

    public void sbloccaCliente(int idCliente) {
        Cliente c = registroClienti.getClienteById(idCliente);
        //c.riabilita();
        
        // In un db reale qui chiameremmo registroClienti.save(c)
    }
}