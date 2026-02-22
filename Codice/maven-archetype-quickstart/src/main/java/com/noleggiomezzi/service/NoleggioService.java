package com.noleggiomezzi.service;

import com.noleggiomezzi.exceptions.PagamentoException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;
import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.enums.StatoNoleggio;
import com.noleggiomezzi.model.tariffe.AssicurazioneFurto;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.model.tariffe.PenaleGiovaneGuidatore;
import com.noleggiomezzi.repository.interfacce.*;
import com.noleggiomezzi.segnalazioni.SegnalazioneFurto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class NoleggioService {
    
    private final IClienteRepository registroClienti;
    private final INoleggioRepository registroNoleggi;
    private final IMezzoRepository catalogoMezzi;
    private final IPuntoNoleggioRepository registroPuntiNoleggio;
    private final ITariffaRepository catalogoTariffe;

    public NoleggioService(IClienteRepository rc,
                          INoleggioRepository rn,
                          IMezzoRepository cm,
                          IPuntoNoleggioRepository rp,
                          ITariffaRepository catalogoTariffe) {
        this.registroClienti = rc;
        this.registroNoleggi = rn;
        this.catalogoMezzi = cm;
        this.catalogoTariffe = catalogoTariffe;
        this.registroPuntiNoleggio = rp;
    }

    // --- Metodi per il Controller 

    public List<Cliente> getTuttiIClienti() {
        return registroClienti.findAll();
    }

    public List<Mezzo> getMezziDisponibili() {
        return catalogoMezzi.findAll().stream()
                .filter(Mezzo::isDisponibile)
                .collect(Collectors.toList());
    }

    public List<PuntoNoleggio> getTutteLeSedi() {
        return registroPuntiNoleggio.findAll();
    }

    public List<ITariffa> getTutteLeTariffe() {
        return catalogoTariffe.findAll();
    }

    // --- Logica Operativa ---

    public int avviaNoleggio(int idCliente, int idMezzo, String nomeTariffa, int idPuntoNoleggio, List<String> extraSelezionati) {
        
        Cliente cliente = registroClienti.getClienteById(idCliente);
        Mezzo mezzo = catalogoMezzi.getMezzoSeValido(idMezzo);
        PuntoNoleggio puntoNoleggio = registroPuntiNoleggio.getPuntoById(idPuntoNoleggio);
        
        ITariffa tariffa = catalogoTariffe.getTariffaByName(nomeTariffa);

        if (extraSelezionati != null) {
            if (extraSelezionati.contains("ASSICURAZIONE_FURTO")) {
                tariffa = new AssicurazioneFurto(tariffa);
            }
            if (extraSelezionati.contains("PENALE_GIOVANE")) {
                tariffa = new PenaleGiovaneGuidatore(tariffa);
            }
        }

        if (!cliente.isAffidabile()) {
            throw new StatoNonValidoException("Cliente non abilitato a noleggiare");
        }

        Noleggio n = new Noleggio(cliente, mezzo, tariffa, puntoNoleggio);
        mezzo.noleggia();
        
        registroNoleggi.aggiungiNoleggio(n);

        return n.getId();
    }

    public void concludiNoleggio(int idNoleggio, int kmFinali, double livelloCarica) {
        Noleggio n = registroNoleggi.getNoleggioById(idNoleggio);

        double durata = Duration.between(n.getDataInizio(), LocalDateTime.now()).toMinutes();
        double costo = n.calcolaCostoFinale(kmFinali, durata);

        Mezzo m = n.getMezzo();
        m.aggiornaLivelloCarica(livelloCarica);
        m.rendiDisponibile();

        Cliente c = n.getCliente();
        boolean pagamentoEffettuato = c.addebbitaImporto(costo);

        if (!pagamentoEffettuato) {
            c.sospendiAccount();
            throw new PagamentoException("Pagamento non riuscito: credito insufficiente.");
        }

        n.chiudi();
        System.out.println("Noleggio concluso con successo. Costo: €" + costo);
    }

    public void segnalaFurto(int idNoleggio, String descrizione) {
        Noleggio n = registroNoleggi.getNoleggioById(idNoleggio);
        SegnalazioneFurto segnalazione = new SegnalazioneFurto(idNoleggio, descrizione);

        boolean pagamentoEffettuato = n.gestisciSegnalazione(segnalazione);

        //Sospensione di sicurezza
        Cliente c = n.getCliente();
        c.sospendiAccount();

        if (!pagamentoEffettuato) {
            throw new PagamentoException("Pagamento penale non riuscito");
        }

        n.chiudi();
    }

    public List<Noleggio> noleggiAttivi() {
        return registroNoleggi.findAll().stream()
                .filter(n -> n.getStatoNoleggio() == StatoNoleggio.ATTIVO)
                .collect(Collectors.toList());
    }
}