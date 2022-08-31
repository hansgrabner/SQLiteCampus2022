package at.campus02;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class DBKlausurVorbereitung {

    private String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\KlausurVorbereitung.db";

    //Aufgabe 3
    public void createTableKunden(){

        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateKunden="CREATE TABLE Kunden\n" +
                    "(\n" +
                    "    KDNR INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Vorname varchar(50), " +
                    "    Nachname varchar(50),\n" +
                    "    Geschlecht varchar(10),\n" +
                    "    Bonuspunkte double(10,2)\n" +
                    ")";

            Statement ddlCreateKundenStmt = conn.createStatement();
            ddlCreateKundenStmt.execute(ddlCreateKunden);
            System.out.println("Table Kunden succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 3
    public void createTableRechnungen(){

        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlToCreateRechnungen="CREATE TABLE Rechnungen\n" +
                    "(\n" +
                    "    ReNr INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Datum varchar(50), " +
                    "    Gesamtbetrag double(10,2),\n" +
                    "    KDNR int,\n" +
                    " FOREIGN KEY ( " +
                    " KDNR  ) "+
                    " REFERENCES Kunden (KDNR)  ON UPDATE NO ACTION" +
                    ")";

            Statement dddlToCreateRechnungenStmt = conn.createStatement();
            dddlToCreateRechnungenStmt.execute(ddlToCreateRechnungen);
            System.out.println("Table Rechnungen succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 3
    public int insertKunde(Kunden kunde){
        int rowId=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertKunde = "INSERT INTO Kunden(Vorname,Nachname,Geschlecht,Bonuspunkte) ";
            insertKunde += "VALUES(?,?,?,?)";

            PreparedStatement insStmt =  conn.prepareStatement(insertKunde);
            insStmt.setString(1,kunde.getVorname());
            insStmt.setString(2,kunde.getNachname());
            insStmt.setString(3,kunde.getGeschlecht());
            insStmt.setDouble(4,kunde.getBonuspunkte());

            int rowCount = insStmt.executeUpdate();

            String sqlTextRowId = "SELECT last_insert_rowid() as rowid;";
            Statement stmt = conn.createStatement();
            ResultSet rsRowId =  stmt.executeQuery(sqlTextRowId);
            rsRowId.next();
            rowId= rsRowId.getInt("rowid");
            rsRowId.close();
            stmt.close();

            kunde.setKDNR(rowId);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowId;
    }

    public int insertRechnungen(Rechnungen rechnung){
        int rowId=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String insertRechnung = "INSERT INTO Rechnungen(Datum,Gesamtbetrag,Kdnr) ";
            insertRechnung += "VALUES(?,?,?)";

            PreparedStatement insStmt =  conn.prepareStatement(insertRechnung);
            insStmt.setString(1,rechnung.getDatum());
            insStmt.setDouble(2,rechnung.getGesamtbetrag());
            insStmt.setInt(3,rechnung.getKDNR());


            int rowCount = insStmt.executeUpdate();

            String sqlTextRowId = "SELECT last_insert_rowid() as rowid;";
            Statement stmt = conn.createStatement();
            ResultSet rsRowId =  stmt.executeQuery(sqlTextRowId);
            rsRowId.next();
            rowId= rsRowId.getInt("rowid");
            rsRowId.close();
            stmt.close();

            rechnung.setReNr(rowId);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowId;
    }

    //Aufgabe 4a
    public Kunden getKunde(int kdnr) {
        Kunden kunde =new Kunden();

        try (Connection conn = DriverManager.getConnection(url)) {

            String selTeilnehmerIn = "SELECT Vorname, Nachname, Geschlecht, Bonuspunkte FROM Kunden WHERE KDNR=?";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
            pSelect.setInt(1,kdnr);;
            ResultSet rs=  pSelect.executeQuery();

            if (rs.next()){

                kunde.setKDNR(kdnr);
                kunde.setVorname(rs.getString("Vorname"));
                kunde.setNachname(rs.getString("Nachname"));
                String geschlecht = rs.getString("Geschlecht");
                if (rs.wasNull()){
                    kunde.setGeschlecht("nicht definiert");
                } else {
                    kunde.setGeschlecht(geschlecht);
                }
                kunde.setBonuspunkte(rs.getDouble("Bonuspunkte"));
            } else {
                //kein Kunde Gefunden
                kunde.setVorname("nicht vorhanden");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  kunde;
    }

    //Aufgabe 4b

    public  ArrayList<Kunden> getAlleKunden() {
        ArrayList<Kunden> alleKunden=new ArrayList<Kunden>();

        try (Connection conn = DriverManager.getConnection(url)) {

            String selTeilnehmerIn = "SELECT Kdnr, Vorname, Nachname, Geschlecht, Bonuspunkte FROM Kunden ";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
           ;
            ResultSet rs=  pSelect.executeQuery();

            while (rs.next()){
                Kunden kunde =new Kunden();
                kunde.setKDNR(rs.getInt("Kdnr"));
                kunde.setVorname(rs.getString("Vorname"));
                kunde.setNachname(rs.getString("Nachname"));
                String geschlecht = rs.getString("Geschlecht");
                if (rs.wasNull()){
                    kunde.setGeschlecht("nicht definiert");
                } else {
                    kunde.setGeschlecht(geschlecht);
                }
                kunde.setBonuspunkte(rs.getDouble("Bonuspunkte"));
                alleKunden.add(kunde);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  alleKunden;
    }

    //Aufgabe 5b
    public String updateKunde(Kunden kunde){
        String meldung="";
        try (Connection conn = DriverManager.getConnection(url)) {

            String updateKunde = "UPDATE KUNDEN SET Vorname=?, Nachname=?, Geschlecht=?, Bonuspunkte=?";
            updateKunde += "WHERE KDNR=?";

            PreparedStatement updStm =  conn.prepareStatement(updateKunde);
            updStm.setString(1,kunde.getVorname());
            updStm.setString(2,kunde.getNachname());
            updStm.setString(3,kunde.getGeschlecht());
            updStm.setDouble(4,kunde.getBonuspunkte());
            updStm.setInt(5,kunde.getKDNR());

            int rowCount = updStm.executeUpdate();
            if (rowCount==1){
                meldung="Kunde wurde geändert";

            }else {
                meldung="Kunde wurde nicht gefunden";
            }

            System.out.println(meldung);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  meldung;
    }

    //Aufgabe 6a
    public int insertRechnung(Rechnungen neueRechnung, Kunden vorhandenerKunde){
        neueRechnung.setKDNR((vorhandenerKunde.getKDNR()));
        return insertRechnungen(neueRechnung);
    }

    //Aufgabe 6b
    public void insertKundeUndRechnungen(ArrayList<Rechnungen> neueRechnungen, Kunden neuerKunde){
        int kdnr= insertKunde(neuerKunde);

        for (Rechnungen r:neueRechnungen
             ) {
            r.setKDNR(kdnr);
            insertRechnungen(r);
        }

    }

    //Aufgabe 6c
    public String updateRechnungen(Rechnungen rechnung){
        String meldung="";
        try (Connection conn = DriverManager.getConnection(url)) {

            String updateKunde = "UPDATE Rechnungen SET Gesamtbetrag=?, Datum=? ";
            updateKunde += "WHERE ReNr=?";

            PreparedStatement updStm =  conn.prepareStatement(updateKunde);
            updStm.setDouble(1,rechnung.getGesamtbetrag());
            updStm.setString(2,rechnung.getDatum());

            updStm.setInt(3,rechnung.getReNr());

            int rowCount = updStm.executeUpdate();
            if (rowCount==1){
                meldung="Rechnung wurde geändert";

            }else {
                meldung="Rechnung wurde nicht gefunden";
            }

            System.out.println(meldung);



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  meldung;
    }

//Aufgabe 6d
    public  ArrayList<Rechnungen> getAlleRechnunge(int kdnr) {
        ArrayList<Rechnungen> alleRechnungen=new ArrayList<Rechnungen>();

        try (Connection conn = DriverManager.getConnection(url)) {

            String selRechnungen = "SELECT Kdnr, ReNr, Gesamtbetrag, Datum FROM Rechnungen WHERE KDNR=? ";

            PreparedStatement pSelect = conn.prepareStatement(selRechnungen);
            pSelect.setInt(1,kdnr);
            ;
            ResultSet rs=  pSelect.executeQuery();

            while (rs.next()){
               Rechnungen r=new Rechnungen();
               r.setGesamtbetrag(rs.getDouble("Gesamtbetrag"));
               r.setDatum(rs.getString("Datum"));
               r.setReNr(rs.getInt("ReNr"));
               r.setKDNR(rs.getInt("Kdnr"));

                alleRechnungen.add(r);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  alleRechnungen;
    }

    public  ArrayList<Kunden> getAlleWeiblichenKunden() {
        ArrayList<Kunden> alleKunden=new ArrayList<Kunden>();

        try (Connection conn = DriverManager.getConnection(url)) {

            String selTeilnehmerIn = "SELECT Kdnr, Vorname, Nachname, Geschlecht, Bonuspunkte FROM Kunden WHERE Geschlecht=?";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
            pSelect.setString(1,"Frau");
            ;
            ResultSet rs=  pSelect.executeQuery();

            while (rs.next()){
                Kunden kunde =new Kunden();
                kunde.setKDNR(rs.getInt("Kdnr"));
                kunde.setVorname(rs.getString("Vorname"));
                kunde.setNachname(rs.getString("Nachname"));
                String geschlecht = rs.getString("Geschlecht");
                if (rs.wasNull()){
                    kunde.setGeschlecht("nicht definiert");
                } else {
                    kunde.setGeschlecht(geschlecht);
                }
                kunde.setBonuspunkte(rs.getDouble("Bonuspunkte"));
                alleKunden.add(kunde);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  alleKunden;
    }

    //Aufgabe 4a
    public Kunden getKundeMitMeistenBonuspunkten() {
        Kunden kunde =new Kunden();

        try (Connection conn = DriverManager.getConnection(url)) {

            String selKunde = "SELECT KDNR, Vorname, Nachname, Geschlecht, Bonuspunkte FROM Kunden ORDER BY Bonuspunkte desc";
            PreparedStatement pSelect = conn.prepareStatement(selKunde);

            ResultSet rs=  pSelect.executeQuery();

            if (rs.next()){

                kunde.setKDNR(rs.getInt("KDNR"));
                kunde.setVorname(rs.getString("Vorname"));
                kunde.setNachname(rs.getString("Nachname"));
                String geschlecht = rs.getString("Geschlecht");
                if (rs.wasNull()){
                    kunde.setGeschlecht("nicht definiert");
                } else {
                    kunde.setGeschlecht(geschlecht);
                }
                kunde.setBonuspunkte(rs.getDouble("Bonuspunkte"));
            } else {
                //kein Kunde Gefunden
                kunde.setVorname("nicht vorhanden");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  kunde;
    }


    //Aufgabe 9
    public void loescheAlleRechnungenUndDanachDenKunden(Kunden kunde){
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String delete1 = "DELETE FROM Rechnungen WHERE KDNR=? ";


            String delete2 = "DELETE FROM Kunden WHERE KDNR=?";



            PreparedStatement delStmt1 =  conn.prepareStatement(delete1);
            delStmt1.setInt(1,kunde.getKDNR());
            PreparedStatement delStm2 =  conn.prepareStatement(delete2);
            delStm2.setInt(1,kunde.getKDNR());

            conn.setAutoCommit(false); //executeUpdate werden nicht automatisch abgeschlossen

            int affect1 = delStmt1.executeUpdate(); //die Transaktion wird per Default abgeschlossen
            int affect2 = delStm2.executeUpdate();

            if (affect2==1){
                conn.commit(); //Speichert alle Änderungen "endgültig in der DB ab
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    //Aufabe 10
    public void printKundenMetadata(){


        try (Connection conn = DriverManager.getConnection(url)) {


            String selTN="SELECT * ";
            selTN += " FROM Kunden";

            Statement selStmt = conn.createStatement();
            ResultSet rs =  selStmt.executeQuery(selTN);

            ResultSetMetaData meta = rs.getMetaData();

            int numerics = 0;

            for ( int i = 1; i <= meta.getColumnCount(); i++ )
            {
                System.out.printf( "%-20s %-20s%n", meta.getColumnLabel( i ),
                        meta.getColumnTypeName( i ) );

                //Vorname              String
                //Nachname             String

                //Vorname String
                //NACHNAME sTRING
                //Bonsupunkte int

                if ( meta.isSigned( i ) )
                    numerics++;
            }

            System.out.println();
            System.out.println( "Spalten: " + meta.getColumnCount() +
                    ", Numerisch: " + numerics );


        } catch (SQLException e) {

        }
    }
}
