import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConcludiNoleggioTest {

    @Test
    void concludiNoleggioCalcolaCosto() {

        RegistroClienti rc = RegistroClienti.getInstance();
        RegistroNoleggi rn = new RegistroNoleggi();
        CatalogoMezzi cm = new CatalogoMezzi();

        Cliente cliente = new Cliente(1, "Mario", "Rossi");
        rc.aggiungiCliente(cliente);

        TipoMezzo tipo = new TipoMezzo("Scooter", true, false);
        Mezzo mezzo = new Mezzo(1, tipo);
        cm.aggiungiMezzo(mezzo);

        ITariffa tariffa = new TariffaOraria();

        NoleggioController controller =
                new NoleggioController(rc, rn, cm);

        int id =
            controller.avviaNoleggio(1, 1, tariffa);

        controller.concludiNoleggio(id, 1, 80.0);

        assertTrue(mezzo.isDisponibile());
    }
}