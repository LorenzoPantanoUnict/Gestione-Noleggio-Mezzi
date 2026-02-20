package com.noleggiomezzi.controller;

import com.noleggiomezzi.exceptions.PagamentoException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;
import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.RegistroClienti;
import com.noleggiomezzi.repository.RegistroNoleggi;
import com.noleggiomezzi.segnalazioni.SegnalazioneFurto;
import com.noleggiomezzi.service.ChiusuraNoleggio;
import com.noleggiomezzi.service.IChiusuraNoleggioService;


public class NoleggioController {
    private IChiusuraNoleggioService chiusuraService;
    private RegistroClienti registroClienti;
    private RegistroNoleggi registroNoleggi;
    private CatalogoMezzi catalogoMezzi;

   public NoleggioController(RegistroClienti rc,
                          RegistroNoleggi rn,
                          CatalogoMezzi cm) {

        this.registroClienti = rc;
        this.registroNoleggi = rn;
        this.catalogoMezzi = cm;

        // inizializzazione chiusuraService
        this.chiusuraService = new ChiusuraNoleggio();
    }

    public int avviaNoleggio(int idCliente, int idMezzo, ITariffa tariffa, PuntoNoleggio puntoNoleggio) {

        Cliente cliente = registroClienti.getCliente(idCliente);

        Mezzo mezzo = catalogoMezzi.getMezzoSeValido(idMezzo);

        if (!cliente.isAffidabile()) {
            throw new StatoNonValidoException("Cliente non abilitato a noleggiare");
        }

        Noleggio n = new Noleggio( cliente, mezzo, tariffa, puntoNoleggio);
        
        mezzo.setStatoNoleggiato();

        registroNoleggi.aggiungiNoleggio(n);

        return n.getId();
    }

    public void concludiNoleggio(int idNoleggio, int kmFinali, double livelloCarica) {
        Noleggio n =
            registroNoleggi.getNoleggio(idNoleggio);

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

        Noleggio n = registroNoleggi.getNoleggio(idNoleggio);

        SegnalazioneFurto segnalazione = new SegnalazioneFurto(idNoleggio, descrizione);

        boolean pagamentoEffettuato = n.gestisciSegnalazione(segnalazione);

        if (!pagamentoEffettuato) {
            throw new PagamentoException("Pagamento non riuscito");
        }

        n.chiudi();
    }

    
}