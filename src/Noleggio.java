import java.time.Duration;
import java.time.LocalDateTime;

public class Noleggio {
    private static int counter = 1;

    private int id;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int kmIniziali;
    private double costoTotale; 
    
    private Cliente cliente;
    private Mezzo mezzo;
    private ITariffa tariffa;
    private PuntoNoleggio puntoNoleggio;
    
    private StatoNoleggio statoNoleggio;
    private StatoPagamento statoPagamento;

    public Noleggio(Cliente cliente, Mezzo mezzo, ITariffa tariffa, PuntoNoleggio punto) {
        this.id = counter++;
        this.cliente = cliente;
        this.mezzo = mezzo;
        this.tariffa = tariffa;
        this.puntoNoleggio = punto;
        this.dataInizio = LocalDateTime.now();
        this.statoNoleggio = StatoNoleggio.ATTIVO;
    }

    public void chiudi(LocalDateTime dataFine, PuntoNoleggio puntoConsegna) {
        this.dataFine = dataFine;
        this.puntoNoleggio = puntoConsegna;
        this.statoNoleggio = StatoNoleggio.CONCLUSO;
    }

    public double calcolaCostoFinale(int kmFinali) {
        long minuti = Duration.between(dataInizio, dataFine).toMinutes();
        int kmPercorsi = kmFinali - this.kmIniziali;
        
        this.costoTotale = tariffa.calcolaCosto((int) minuti, kmPercorsi);
        return this.costoTotale;
    }

    public int getId() { return id; }
    public Mezzo getMezzo() { return mezzo; }
}
