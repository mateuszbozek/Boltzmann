package Boltzmann;

public class Cell {

    private double temperatura;
    private String stan;

    public Cell()
    {
        this.temperatura = 50;
        this.stan=Stan.stan[0];
    }

    public Cell(double temperatura, int stan)
    {
        this.temperatura=temperatura;
        this.stan=Stan.stan[stan];
    }

    public Cell(double temperatura, String stan)
    {
        this.temperatura=temperatura;
        this.stan=stan;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }


}
