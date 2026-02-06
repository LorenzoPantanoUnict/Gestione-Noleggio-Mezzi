

public class TariffaOraria implements ITariffa {

    private double costoOra = 5.0;

    @Override
    public double calcolaCosto(int durataMinuti, int km) {
        return (durataMinuti / 60.0) * costoOra;
    }

    @Override
    public String getNome() {
        return "Oraria";
    }

    public String getDescrizione(){
        return "Tariffa basata sulla durata del noleggio " + costoOra + " euro all'ora";
    }
}