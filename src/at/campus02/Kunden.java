package at.campus02;

public class Kunden {
    public String toString() {
        return "Kunden{" +
                "KDNR=" + KDNR +
                ", Vorname='" + Vorname + '\'' +
                ", Nachname='" + Nachname + '\'' +
                ", Geschlecht='" + Geschlecht + '\'' +
                ", Bonuspunkte=" + Bonuspunkte +
                '}';
    }

    public int getKDNR() {
        return KDNR;
    }

    public void setKDNR(int KDNR) {
        this.KDNR = KDNR;
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

    public String getGeschlecht() {
        return Geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        Geschlecht = geschlecht;
    }

    public double getBonuspunkte() {
        return Bonuspunkte;
    }

    public void setBonuspunkte(double bonuspunkte) {
        Bonuspunkte = bonuspunkte;
    }

    private int KDNR;
    private String Vorname;
    private String Nachname;
    private String Geschlecht;
    private double Bonuspunkte;
}
