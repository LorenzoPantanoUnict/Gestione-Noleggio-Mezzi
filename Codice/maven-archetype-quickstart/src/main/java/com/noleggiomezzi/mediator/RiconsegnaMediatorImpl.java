package mediator;

import java.time.Duration;
import java.time.LocalDateTime;

import model.Cliente;
import model.Mezzo;
import model.Noleggio;

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
