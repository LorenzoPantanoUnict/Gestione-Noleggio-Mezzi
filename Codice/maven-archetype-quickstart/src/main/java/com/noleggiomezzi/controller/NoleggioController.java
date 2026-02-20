package com.noleggiomezzi.controller;

import com.noleggiomezzi.exceptions.PagamentoException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;
import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.interfacce.IClienteRepository;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.repository.interfacce.INoleggioRepository;
import com.noleggiomezzi.segnalazioni.SegnalazioneFurto;
import com.noleggiomezzi.service.IChiusuraNoleggioService;

import org.springframework.stereotype.Controller;

@Controller
public class NoleggioController {
    private IChiusuraNoleggioService chiusuraService;
    private IClienteRepository registroClienti;
    private INoleggioRepository registroNoleggi;
    private IMezzoRepository catalogoMezzi;

   public NoleggioController(IClienteRepository rc,
                          INoleggioRepository rn,
                          IMezzoRepository cm,
                          IChiusuraNoleggioService cs) {

        this.registroClienti = rc;
        this.registroNoleggi = rn;
        this.catalogoMezzi = cm;
        this.chiusuraService = cs;
    }

    public int avviaNoleggio(int idCliente, int idMezzo, ITariffa tariffa, PuntoNoleggio puntoNoleggio) {

        Cliente cliente = registroClienti.getClienteById(idCliente);

        Mezzo mezzo = catalogoMezzi.getMezzoSeValido(idMezzo);

        if (!cliente.isAffidabile()) {
            throw new StatoNonValidoException("Cliente non abilitato a noleggiare");
        }

        Noleggio n = new Noleggio( cliente, mezzo, tariffa, puntoNoleggio);
        
        mezzo.noleggia();

        registroNoleggi.aggiungiNoleggio(n);

        return n.getId();
    }

    public void concludiNoleggio(int idNoleggio, int kmFinali, double livelloCarica) {
        Noleggio n =
            registroNoleggi.getNoleggioById(idNoleggio);

        boolean pagamentoEffettuato =
            chiusuraService.gestisciChiusura(
                n,
                kmFinali,
                livelloCarica
            );

        if (!pagamentoEffettuato) {
            throw new PagamentoException(
                "Pagamento non riuscito");
        }

        n.chiudi();

        System.out.println(
            "Noleggio concluso con successo");
    }



    public void segnalaFurto(int idNoleggio, String descrizione){

        Noleggio n = registroNoleggi.getNoleggioById(idNoleggio);

        SegnalazioneFurto segnalazione = new SegnalazioneFurto(idNoleggio, descrizione);

        boolean pagamentoEffettuato = n.gestisciSegnalazione(segnalazione);

        if (!pagamentoEffettuato) {
            throw new PagamentoException("Pagamento non riuscito");
        }

        n.chiudi();
    }

    
}