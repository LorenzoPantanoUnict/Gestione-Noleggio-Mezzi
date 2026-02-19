package com.noleggiomezzi.controller;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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

//Validatore
import org.apache.commons.validator.routines.EmailValidator;

import com.noleggiomezzi.mediator.RiconsegnaMediator;
import com.noleggiomezzi.mediator.RiconsegnaMediatorImpl;


public class NoleggioController {
    private RiconsegnaMediator mediator;
    private RegistroClienti registroClienti =
        RegistroClienti.getInstance();

    private RegistroNoleggi registroNoleggi;
    private CatalogoMezzi catalogoMezzi;

   public NoleggioController(RegistroClienti rc,
                          RegistroNoleggi rn,
                          CatalogoMezzi cm) {

    this.registroClienti = rc;
    this.registroNoleggi = rn;
    this.catalogoMezzi = cm;

    // inizializzazione mediator
    this.mediator = new RiconsegnaMediatorImpl();
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

 public void concludiNoleggio(int idNoleggio,
                             int kmFinali,
                             double livelloCarica) {

    Noleggio n =
        registroNoleggi.getNoleggio(idNoleggio);

    boolean pagamentoEffettuato =
        mediator.gestisciChiusura(
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


   public int registraCliente(String nome,
                           String cognome,
                           String email) {

    // 1️⃣ Validazione email
    if (email == null || email.isEmpty()) {
        throw new IllegalArgumentException(
                "Email non valida");
    }

    if (!EmailValidator.getInstance()
            .isValid(email)) {

        throw new IllegalArgumentException(
                "Formato email non valido");
    }
    if (registroClienti.emailEsistente(email)) {
    throw new IllegalArgumentException(
            "Email già registrata");
    }

    // 2️⃣ Creazione cliente
    Cliente cliente =
            new Cliente(nome,
                        cognome,
                        email);

    // 3️⃣ Salvataggio nel registro
    registroClienti.aggiungiCliente(cliente);

    // 4️⃣ Return ID (utile per UI / test)
    return cliente.getId();
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
    
    public List<Mezzo> visualizzaDisponibilita(
        PuntoNoleggio punto) {

    List<Mezzo> lista =
            punto.getListaMezziDisponibili();

    for (Mezzo m : lista) {

        System.out.println(
            "Mezzo ID: " + m.getId() +
            " | Stato: " + m.getStato());
    }

    return lista;
    }

    
}