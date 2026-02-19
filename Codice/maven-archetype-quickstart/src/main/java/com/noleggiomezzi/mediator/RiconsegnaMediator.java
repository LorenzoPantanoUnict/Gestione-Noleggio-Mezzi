package mediator;

import model.Noleggio;

public interface RiconsegnaMediator {

    /**
     * Coordina la chiusura del noleggio
     * @return true se il pagamento va a buon fine
     */
    boolean gestisciChiusura(
            Noleggio n,
            int kmFinali,
            double livelloCarica
    );
}
