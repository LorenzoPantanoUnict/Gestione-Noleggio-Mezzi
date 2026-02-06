public class Cliente {

    private int id;
    private String nome;
    private String cognome;
    private int affidabilita;
    private String email;

    public Cliente(int id, String nome, String cognome, int affidabilita, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.affidabilita = affidabilita;
        this.email= email;
    }

    public boolean isAbilitato() {
        return affidabilita > 0;
    }

    public void sospendiAccount() {
        affidabilita = 0;
    }

    public String getDati() {
        return nome + " " + cognome + " (" + email + ")";
    }

    public int getId() {
        return id;
    }
    
}