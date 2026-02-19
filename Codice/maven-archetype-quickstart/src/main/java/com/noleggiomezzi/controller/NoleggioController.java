package com.noleggiomezzi.controller;
import java.time.Duration;
import java.time.LocalDateTime;

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


public class NoleggioController {

    private RegistroClienti registroClienti;
    private RegistroNoleggi registroNoleggi;
    private CatalogoMezzi catalogoMezzi;

    public NoleggioController(RegistroClienti rc,
                              RegistroNoleggi rn,
                              CatalogoMezzi cm) {
        this.registroClienti = rc;
        this.registroNoleggi = rn;
        this.catalogoMezzi = cm;
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

        Noleggio n = registroNoleggi.getNoleggio(idNoleggio);

        n.setLivelloCarica(livelloCarica);

        double durata = Duration.between(n.getDataInizio(), LocalDateTime.now()).toMinutes();

        Mezzo m = n.getMezzo();
        
        m.setLivelloCarica(livelloCarica);
        
        m.setStatoDisponibile();
        
        double costo = n.calcolaCostoFinale(kmFinali, durata);

        Cliente c = n.getCliente();

        boolean pagamentoEffettuato = c.addebbitaImporto(costo);

        if (!pagamentoEffettuato) {
            throw new PagamentoException("Pagamento non riuscito");
        }

        n.chiudi();

        System.out.println("Noleggio concluso con successo");

    }

    public void registraCliente(String nome, String cognome, String email) {
        
        Cliente c = new Cliente( nome, cognome, email);

        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email non valida");
        }

        if(!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email non valida");
        }
        
        registroClienti.aggiungiCliente(c);
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