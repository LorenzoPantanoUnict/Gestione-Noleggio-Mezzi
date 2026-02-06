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

    public int avviaNoleggio(int idCliente,
                             int idMezzo,
                             ITariffa tariffa) {

        Cliente cliente = registroClienti.trovaCliente(idCliente);
        Mezzo mezzo = catalogoMezzi.getMezzo(idMezzo);

        if (!cliente.isAbilitato() || !mezzo.isDisponibile()) {
            throw new RuntimeException("Noleggio non consentito");
        }

        Noleggio n = registroNoleggi.creaNoleggio(
                cliente, mezzo, tariffa
        );

        mezzo.aggiornaStato(StatoMezzo.NOLEGGIATO);

        return n.getId();
    }

    public void concludiNoleggio(int idNoleggio,
                                 int idPuntoConsegna,
                                 double livelloCarica) {

        Noleggio n = registroNoleggi.getNoleggio(idNoleggio);

        n.chiudi();

        double costo = n.calcolaCostoFinale();

        Mezzo m = n.getMezzo();
        m.setLivelloCarica(livelloCarica);
        m.aggiornaStato(StatoMezzo.DISPONIBILE);

        System.out.println("Costo totale: " + costo);
    }
}