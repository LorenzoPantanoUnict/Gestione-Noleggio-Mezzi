public class Cliente {

    private int id;
    private String nome;
    private String cognome;
    private int affidabilita;

    public Cliente(int id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.affidabilita = 100;
    }

    public boolean isAbilitato() {
        return affidabilita > 0;
    }

    public void sospendiAccount() {
        affidabilita = 0;
    }

    public int getId() {
        return id;
    }
}