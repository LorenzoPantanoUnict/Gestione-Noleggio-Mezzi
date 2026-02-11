import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroClientiTest {

    @Test
    void registraClienteCorrettamente() {

        RegistroClienti registro = RegistroClienti.getInstance();

        Cliente c = new Cliente(1, "Mario", "Rossi");

        registro.aggiungiCliente(c);

        Cliente trovato = registro.trovaCliente(1);

        assertNotNull(trovato);
        assertEquals("Mario", trovato.getNome());
    }

    @Test
    void clienteEsiste() {

        RegistroClienti registro = RegistroClienti.getInstance();

        Cliente c = new Cliente(2, "Luca", "Bianchi");

        registro.aggiungiCliente(c);

        assertTrue(registro.esiste(2));
    }

    @Test
    void clienteSospesoConteggio() {

        RegistroClienti registro = RegistroClienti.getInstance();

        Cliente c = new Cliente(3, "Anna", "Verdi");
        c.sospendiAccount();

        registro.aggiungiCliente(c);

        assertEquals(1, registro.getClientiSospesi());
    }
}