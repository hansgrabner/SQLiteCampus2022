package at.campus02;

public class Rechnungen {
   private int ReNr;
   private String Datum;

    @Override
    public String toString() {
        return "Rechnungen{" +
                "ReNr=" + ReNr +
                ", Datum='" + Datum + '\'' +
                ", Gesamtbetrag=" + Gesamtbetrag +
                ", KDNR=" + KDNR +
                '}';
    }

    public int getReNr() {
        return ReNr;
    }

    public void setReNr(int reNr) {
        ReNr = reNr;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public double getGesamtbetrag() {
        return Gesamtbetrag;
    }

    public void setGesamtbetrag(double gesamtbetrag) {
        Gesamtbetrag = gesamtbetrag;
    }

    public int getKDNR() {
        return KDNR;
    }

    public void setKDNR(int KDNR) {
        this.KDNR = KDNR;
    }

    private  double Gesamtbetrag;
   private int KDNR;

   private Kunden Kunde;


}
