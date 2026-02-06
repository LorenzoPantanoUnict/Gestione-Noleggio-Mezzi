
import java.util.HashMap;

public class RegistroClienti {

    private HashMap<Integer, Cliente> mappaClienti = new HashMap<>();

    public Cliente trovaCliente(int id) {
        return mappaClienti.get(id);
    }

    public void aggiungiCliente(Cliente c) {
        mappaClienti.put(c.getId(), c);
    }
}