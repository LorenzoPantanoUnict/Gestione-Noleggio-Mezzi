


import java.util.HashMap;

public class RegistroNoleggi {

    private HashMap<Integer, Noleggio> mappa = new HashMap<>();

    public Noleggio creaNoleggio(
            Cliente c,
            Mezzo m,
            ITariffa t) {

        Noleggio n = new Noleggio(c, m, t);
        mappa.put(n.getId(), n);
        return n;
    }

    public Noleggio getNoleggio(int id) {
        return mappa.get(id);
    }
}