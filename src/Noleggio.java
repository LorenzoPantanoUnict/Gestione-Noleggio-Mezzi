import java.time.Duration;
import java.time.LocalDateTime;

public class Noleggio {

    private static int counter = 1;

    private int id;
    private Cliente cliente;
    private Mezzo mezzo;
    private ITariffa tariffa;

    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

    public Noleggio(Cliente c, Mezzo m, ITariffa t) {
        this.id = counter++;
        this.cliente = c;
        this.mezzo = m;
        this.tariffa = t;
        this.dataInizio = LocalDateTime.now();
    }

    public void chiudi() {
        this.dataFine = LocalDateTime.now();
    }

    public double calcolaCostoFinale() {
        long minuti = Duration.between(
                dataInizio, dataFine
        ).toMinutes();

        return tariffa.calcolaCosto((int) minuti, 0);
    }

    public int getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }
}