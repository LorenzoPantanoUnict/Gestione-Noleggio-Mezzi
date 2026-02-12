package com.noleggiomezzi.controller;
import java.time.Duration;
import java.time.LocalDateTime;

import com.noleggiomezzi.exceptions.StatoNonValidoException;
import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.StatoMezzo;
//import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.model.tariffe.ITariffa;
import com.noleggiomezzi.repository.CatalogoMezzi;
import com.noleggiomezzi.repository.RegistroClienti;
import com.noleggiomezzi.repository.RegistroNoleggi;


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

        Cliente cliente = registroClienti.trovaCliente(idCliente);
        Mezzo mezzo = catalogoMezzi.getMezzoSeValido(idMezzo);

        if (!cliente.isAbilitato()) {
            throw new StatoNonValidoException("Cliente non abilitato a noleggiare");
        }

        Noleggio n = registroNoleggi.creaNoleggio( cliente, mezzo, tariffa, puntoNoleggio);

        mezzo.aggiornaStato(StatoMezzo.NOLEGGIATO);

        return n.getId();
    }

    public void concludiNoleggio(int idNoleggio,
                                 int kmFinali,
                                 double livelloCarica) {

        Noleggio n = registroNoleggi.getNoleggio(idNoleggio);

        double durata = Duration.between(n.getDataInizio(), LocalDateTime.now()).toMinutes();

        double costo = n.calcolaCostoFinale(kmFinali, durata);


        Mezzo m = n.getMezzo();
        m.setLivelloCarica(livelloCarica);
        m.aggiornaStato(StatoMezzo.DISPONIBILE);

        //Cliente c = n.getCliente();
        //registroClienti.addebitaImporto(c.getId(), costo);


        //Supporre pagamento concluso con successo


        n.chiudi();

        System.out.println("Costo totale: " + costo);

    }

    public void registraCliente(int id, String nome, String cognome, String email) {
        int affidabilitaDefault = 1; // Valore iniziale di affidabilità
        Cliente c = new Cliente(id, nome, cognome, affidabilitaDefault, email);
        registroClienti.aggiungiCliente(c);
    }

    
}