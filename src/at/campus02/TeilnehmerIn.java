package at.campus02;

public class TeilnehmerIn {
    private int TeilInNr;
    private String Vorname;
    private String Nachname;

    private double Bonuspunkte;

    public TeilnehmerIn(int teilInNr, String vorname, String nachname, double bonuspunkte) {
        TeilInNr = teilInNr;
        Vorname = vorname;
        Nachname = nachname;
        Bonuspunkte = bonuspunkte;
    }

    @Override
    public String toString() {
        return "TeilnehmerIn{" +
                "TeilInNr=" + TeilInNr +
                ", Vorname='" + Vorname + '\'' +
                ", Nachname='" + Nachname + '\'' +
                ", Bonuspunkte=" + Bonuspunkte +
                '}';
    }

    public TeilnehmerIn() {
    }

    public int getTeilInNr() {
        return TeilInNr;
    }

    public void setTeilInNr(int teilInNr) {
        TeilInNr = teilInNr;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getNachname() {
        return Nachname;
    }

    public void setNachname(String nachname) {
        Nachname = nachname;
    }

    public double getBonuspunkte() {
        return Bonuspunkte;
    }

    public void setBonuspunkte(double bonuspunkte) {
        Bonuspunkte = bonuspunkte;
    }


}
