package at.campus02;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Klausurvorbereitung");

        DBKlausurVorbereitung db=new DBKlausurVorbereitung();
       // db.createTableKunden();
        // db.createTableRechnungen();

        Kunden k=new Kunden();
        k.setVorname("Hans");
        k.setNachname("Grabner");
        k.setGeschlecht("Mann");
        k.setBonuspunkte(120);

       db.insertKunde(k);

        System.out.printf("Kunde wurd mit der Nummer %d hinzugefügt",k.getKDNR());


        Rechnungen r =new Rechnungen();
        r.setDatum("31.08.2022");
        r.setGesamtbetrag(200);
        r.setKDNR(k.getKDNR());

        db.insertRechnungen(r);

        System.out.printf("Rechnung wurd mit der Nummer %d hinzugefügt",r.getReNr());

        System.out.println("Kunde 1 " + db.getKunde(1));

        ArrayList<Kunden> alleKunden = db.getAlleKunden();

        System.out.println("Alle Kunden " + alleKunden);

        k.setKDNR(1);
        System.out.println(db.updateKunde(k));

        k.setKDNR(7);
        db.insertRechnung(r,k);

        ArrayList<Rechnungen> rechnungen =new ArrayList<Rechnungen>();
        Rechnungen r1=new Rechnungen();
        r1.setGesamtbetrag(200);
        r1.setDatum("01.01.2022");
        rechnungen.add(r1);

        Rechnungen r2=new Rechnungen();
        r2.setGesamtbetrag(700);
        r2.setDatum("03.07.2022");
        rechnungen.add(r2);

        Kunden kNeu =new Kunden();
        kNeu.setVorname("Vanja");

        db.insertKundeUndRechnungen(rechnungen,kNeu);

        r2.setGesamtbetrag(999);
        db.updateRechnungen(r2);


        System.out.println("Rechnungen für KDNR 20 " + db.getAlleRechnunge(20));

        System.out.println("Weibliche Kunden " + db.getAlleWeiblichenKunden());

        System.out.println("Meisten Bonuspunkten " + db.getKundeMitMeistenBonuspunkten());

        Kunden kDelete = new Kunden();
        kDelete.setKDNR(3);

        db.loescheAlleRechnungenUndDanachDenKunden(kDelete);

        db.printKundenMetadata();
    }
}
