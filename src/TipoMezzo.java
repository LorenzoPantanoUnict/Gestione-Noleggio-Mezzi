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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isElettrico() {
        return elettrico;
    }

    public void setElettrico(boolean elettrico) {
        this.elettrico = elettrico;
    }

    public boolean isRichiedePatente() {
        return richiedePatente;
    }

    public void setRichiedePatente(boolean richiedePatente) {
        this.richiedePatente = richiedePatente;
    }
}
