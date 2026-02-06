public class TipoMezzo {

    private String nome;
    private boolean isElettrico;
    private boolean richiedePatente;

    public TipoMezzo(String nome,
                     boolean isElettrico,
                     boolean richiedePatente) {
        this.nome = nome;
        this.isElettrico = isElettrico;
        this.richiedePatente = richiedePatente;
    }
}
