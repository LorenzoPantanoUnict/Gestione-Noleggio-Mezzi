package com.noleggiomezzi.mediator;

import java.time.Duration;
import java.time.LocalDateTime;

import com.noleggiomezzi.model.Cliente;
import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.Noleggio;

public class RiconsegnaMediatorImpl
        implements RiconsegnaMediator {

    @Override
    public boolean gestisciChiusura(
            Noleggio n,
            int kmFinali,
            double livelloCarica) {

        double durata = Duration
                .between(n.getDataInizio(),
                         LocalDateTime.now())
                .toMinutes();

        double costo =
                n.calcolaCostoFinale(
                        kmFinali,
                        durata);

        Mezzo m = n.getMezzo();
        m.setLivelloCarica(livelloCarica);
        m.setStatoDisponibile();

        Cliente c = n.getCliente();
        return c.addebbitaImporto(costo);
    }
}
