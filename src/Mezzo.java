public class Mezzo {

    private int id;
    private String stato;
    private double livelloCarica;
    DescrizioneMezzo descrizione;
    private TipoMezzo tipo;

    public Mezzo(int id, DescrizioneMezzo descrizione) {
        this.id = id;
        this.tipo = tipo;
        this.stato = "DISPONIBILE";
        this.livelloCarica = 100.0;
    }

    public boolean isDisponibile() {
        return stato.equals("DISPONIBILE");
    }

    public void aggiornaStato(String stato) {
        this.stato = stato;
    }

    public void setLivelloCarica(double livelloCarica) {
        this.livelloCarica = livelloCarica;
    }

    public int getId() {
        return id;
    }

    public DescrizioneMezzo getDescrizione() {
        return descrizione;
    }
}