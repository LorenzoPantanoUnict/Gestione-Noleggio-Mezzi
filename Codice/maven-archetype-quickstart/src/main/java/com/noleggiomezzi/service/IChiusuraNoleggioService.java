package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Noleggio;

public interface IChiusuraNoleggioService {

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
