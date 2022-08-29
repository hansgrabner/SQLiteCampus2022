package at.campus02;

public class Noten {
    private int NotenId;
    private int TeilInNr;
    private String Fach;
    private int Note;

    public Noten(int notenId, int teilInNr, String fach, int note) {
        NotenId = notenId;
        TeilInNr = teilInNr;
        Fach = fach;
        Note = note;
    }

    public Noten(){

    }

    @Override
    public String toString() {
        return "Noten{" +
                "NotenId=" + NotenId +
                ", TeilInNr=" + TeilInNr +
                ", Fach=" + Fach +
                ", Note=" + Note +
                '}';
    }

    public int getNotenId() {
        return NotenId;
    }

    public void setNotenId(int notenId) {
        NotenId = notenId;
    }

    public int getTeilInNr() {
        return TeilInNr;
    }

    public void setTeilInNr(int teilInNr) {
        TeilInNr = teilInNr;
    }

    public String getFach() {
        return Fach;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public int getNote() {
        return Note;
    }

    public void setNote(int note) {
        Note = note;
    }
}
