public class TipoMezzo {

    private String nome;
    private boolean elettrico;
    private boolean richiedePatente;

    public TipoMezzo(String nome,
                     boolean elettrico,
                     boolean richiedePatente) {
        this.nome = nome;
        this.elettrico = elettrico;
        this.richiedePatente = richiedePatente;
    }
}