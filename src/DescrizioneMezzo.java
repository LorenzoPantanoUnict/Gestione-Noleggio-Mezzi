
public class DescrizioneMezzo {

    private String marca;
    private String modello;
    private int annoProduzione;
    private int cilindrata;
    private int posti;

    public DescrizioneMezzo(String marca,
                             String modello,
                             int annoProduzione,
                             int cilindrata,
                             int posti) {

        this.marca = marca;
        this.modello = modello;
        this.annoProduzione = annoProduzione;
        this.cilindrata = cilindrata;
        this.posti = posti;
    }

    // --- Getter ---

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public int getAnnoProduzione() {
        return annoProduzione;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public int getPosti() {
        return posti;
    }

    // --- Metodo descrittivo ---

    public String stampaDescrizione() {
        return marca + " " + modello +
               " (" + annoProduzione + ")" +
               " - " + cilindrata + "cc" +
               " - " + posti + " posti";
    }
}