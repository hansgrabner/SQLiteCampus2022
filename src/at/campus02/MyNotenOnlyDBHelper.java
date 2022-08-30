package at.campus02;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class MyNotenOnlyDBHelper {
    public void createTableNoten(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlToCreateNoten="CREATE TABLE Noten\n" +
                    "(\n" +
                    "    NotenId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    TeilInNr int, -- Foreign Key Constrain wird nachträglich hinzugefügt\n" +
                    "    Fach varchar(50),\n" +
                    "    Note int,\n" +
                    " FOREIGN KEY ( " +
                    " TeilInNr  ) "+
            " REFERENCES TeilnehmerInnen (TeilInNr)  ON UPDATE NO ACTION" +
                    ")";

            Statement ddlCreateNotenStmt = conn.createStatement();
            ddlCreateNotenStmt.execute(ddlToCreateNoten);
            System.out.println("Table Noten succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe
    public void deleteTeilnehmerIn(String dbName, int teilInNr){
    // Fehler bei Foreign Key Constrain Verletzung
        //TeilnehmerIn kann nicht gelöscht werden, weil es noch noten gibt
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

            //BEI DML
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            String delTN="DELETE FROM TeilnehmerInnen  ";
            delTN += " Where TeilInNr=" + teilInNr;

            Statement delTNStmt = conn.createStatement();

            delTNStmt.executeUpdate(delTN); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("TN gelöscht");



        } catch (SQLException e) {
            if (e.getMessage().toLowerCase(Locale.ROOT).contains("foreign key")){
                System.out.println("TN kann nicht gelöscht werden, weil noch Noten vorhanden sind");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectTeilnehmerInnen(String dbName){

        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


            String selTN="SELECT TeilInNr, Vorname, Nachname, Bonuspunkte ";
            selTN += " FROM TeilnehmerInnen";

            Statement selStmt = conn.createStatement();
            ResultSet rs =  selStmt.executeQuery(selTN);

            /*
            rs.next();
            //3	Karolina	Wasalska	20
            System.out.printf("%d %s %s %f",
                    rs.getInt("TeilInNr"), rs.getString("Vorname"),
                    rs.getString("Nachname"),rs.getDouble("Bonuspunkte"));

             */
            //4	Vanja	Zivanic	40
            //5	Mayssa	Alnawaqil	50

            //Beispiel für printf

            System.out.printf("%s das ist ein Text %d eine Zahl","Hello",17);

            while(rs.next()) {

                System.out.printf("%d %s %s %f\n",
                        rs.getInt("TeilInNr"), rs.getString("Vorname"),
                        rs.getString("Nachname"), rs.getDouble("Bonuspunkte"));
            }



        } catch (SQLException e) {

        }
    }

    public void printAllNoten(String dbName) {
    //TeilInNr, Fach, Note SELECT TeilNr, Fach, Note FROM Noten
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

            String selNoten="SELECT t.TeilInNr, Vorname, Bonuspunkte, Nachname, Fach, n.Note ";
            selNoten += " FROM TeilnehmerInnen t LEFT JOIN Noten n";
            selNoten += " ON t.TeilInNr=n.TeilInNr";

            Statement selStmt = conn.createStatement();
            ResultSet rs =  selStmt.executeQuery(selNoten);

            while(rs.next()) {

                System.out.printf("%s %d %s %f %s %d\n",
                        rs.getString("Vorname"), rs.getInt("TeilInNr"),
                        rs.getString("Nachname"),rs.getDouble("Bonuspunkte"), rs.getString("Fach"),
                        rs.getInt("Note"));

                double bonuspunkte = rs.getDouble("Bonuspunkte");
                if (rs.wasNull()){
                    System.out.println("Die Bonuspunkte sind NULL");
                }

                int note = rs.getInt("Note");
                if (rs.wasNull()){
                    System.out.println("Die Note ist NULL");
                }
            }



        } catch (SQLException e) {

        }
    }

    public void insertNoten(
            String dbName,
            int teilNr,
            String fach,
            int note){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


            //BEI DML
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            String insertNoten="INSERT INTO Noten(TeilInNr, Fach, Note) ";
            insertNoten += " VALUES(" + teilNr + ", '" + fach + "', " +note + ")";

            Statement notenInsStmt = conn.createStatement();

            notenInsStmt.executeUpdate(insertNoten); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("Note hinzugefügt");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertNotenPrepared(
            String dbName,
            int teilNr,
            String fach,
            int note){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


            //BEI DML
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            String insertNoten="INSERT INTO Noten(TeilInNr, Fach, Note) VALUES(?,?,?)";


            PreparedStatement notenInsStmt = conn.prepareStatement(insertNoten);
            notenInsStmt.setInt(1,teilNr);
            notenInsStmt.setString(2,fach); //drop table MeineKunden
            notenInsStmt.setInt(3,note);

            notenInsStmt.executeUpdate(); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("Note hinzugefügt");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





    }

    String dbConnectionString  = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\MeineNoten.db";

    public int updateTeilnehmerIn(TeilnehmerIn teilnehmerIn){
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String updateTeilnehmerIn = "UPDATE TeilnehmerInnen SET ";
            updateTeilnehmerIn += "Vorname=?, Nachname=?, Bonuspunkte=? WHERE TeilInNr=?";

            PreparedStatement updStmt =  conn.prepareStatement(updateTeilnehmerIn);
            updStmt.setString(1,teilnehmerIn.getVorname());
            updStmt.setString(2,teilnehmerIn.getNachname());
            updStmt.setDouble(3,teilnehmerIn.getBonuspunkte());
            updStmt.setInt(4,teilnehmerIn.getTeilInNr());

            rowCount = updStmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }


    public int updateNoten(String dbName, int notenId, String geaendertesFach,
            int geaenderdeNote){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            //TeilInNR kann nicht geändert werden, führt nie zu einer FK-Constrain verletzung
            //conn.createStatement().execute("PRAGMA foreign_keys = ON");

            String notenUpdate= "UPDATE Noten SET Fach='" + geaendertesFach ;
            notenUpdate += "', Note = " + geaenderdeNote;
            notenUpdate += " WHERE NotenID="+notenId;
            Statement notenUpdStmt = conn.createStatement();
            rowCount= notenUpdStmt.executeUpdate(notenUpdate); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate
            return rowCount;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }

    public int deleteNote(String dbName, int notenId){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            //DELETE FROM NOtEN WHERE NotenId=?

            String notenDelete= "DELETE FROM Noten ";
            notenDelete += " WHERE NotenID="+notenId;
            Statement notenDelStmt = conn.createStatement();
            rowCount= notenDelStmt.executeUpdate(notenDelete); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate
            return rowCount;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }



    public TeilnehmerIn getTeilnehmerIn(int teilInNr) {
        TeilnehmerIn tGefunden =new TeilnehmerIn();

        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {
            //SELECT * FROM TeilnehmerInnen WHERE Bonsupunkte >? OR (Vorname = ? AND TeilInNr < ?
            String selTeilnehmerIn = "SELECT Vorname, Nachname, Bonuspunkte FROM TeilnehmerInnen WHERE TeilInNr=?";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
            pSelect.setInt(1,teilInNr);;
            ResultSet rs=  pSelect.executeQuery();

            if (rs.next()){
                //Es gibt eine Zeile - eine Teilnehmerin
                tGefunden.setTeilInNr(teilInNr);
                tGefunden.setVorname(rs.getString("Vorname"));
                tGefunden.setNachname(rs.getString("Nachname"));
                tGefunden.setBonuspunkte(rs.getDouble("Bonuspunkte"));
            } else {
                //kein TN Gefunden
                tGefunden.setVorname("nicht vorhanden");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  tGefunden;
    }

    public ArrayList<TeilnehmerIn> getAlleTeilnehmerInnen() {
        ArrayList<Integer> teilInNrList =new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selAlleTeilnehmerIn = "SELECT TeilInNr FROM TeilnehmerInnen";
            PreparedStatement pSelect = conn.prepareStatement(selAlleTeilnehmerIn);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                teilInNrList.add(rs.getInt("TeilInNr"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<TeilnehmerIn> liste=new ArrayList<>();

        for (int nr : teilInNrList) {
            liste.add(getTeilnehmerIn(nr));
        }

        return liste;

        }

    //Aufgaben
    //1. UPDATE TeilnehmerInnen mit PrepardStatement
    //2. DELETE Noten mit PreparedStatment
    //3. INSERT TeilnehmerInnen mit PreparedStatement
    //4. SELECT
    //a. alle TN mit Bonus > ? --- PreparedStatement
    //b. alle Noten von einer TeilnehmerIn  --- printNotenFuerTeilInNr(3) PreparedStatement, bei keine Noten - es gibt keine Noten

    public void deleteNoten(int teilNr){

        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String deleteNote= "DELETE FROM NOTEN WHERE TeilInNr=?";
            PreparedStatement deleteStmt  = conn.prepareStatement(deleteNote);
            deleteStmt.setInt(1,teilNr);
            deleteStmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int insertTeilnehmerIn(TeilnehmerIn teilnehmerIn){
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String insertTeilnehmerIn = "INSERT INTO TeilnehmerInnen(Vorname,Nachname,Bonuspunkte) ";
            insertTeilnehmerIn += "VALUES(?,?,?)";

            PreparedStatement insStmt =  conn.prepareStatement(insertTeilnehmerIn);
            insStmt.setString(1,teilnehmerIn.getVorname());
            insStmt.setString(2,teilnehmerIn.getNachname());
            insStmt.setDouble(3,teilnehmerIn.getBonuspunkte());

            rowCount = insStmt.executeUpdate();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  rowCount;
    }




    public ArrayList<TeilnehmerIn> getAlleTeilnehmerInnenMitFilter(double minBonuspunkte) {
        ArrayList<Integer> teilInNrList =new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selAlleTeilnehmerIn = "SELECT TeilInNr FROM TeilnehmerInnen WHERE Bonuspunkte > ?";

            PreparedStatement pSelect = conn.prepareStatement(selAlleTeilnehmerIn);
            pSelect.setDouble(1,minBonuspunkte);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                teilInNrList.add(rs.getInt("TeilInNr"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<TeilnehmerIn> liste=new ArrayList<>();

        for (int nr : teilInNrList) {
            liste.add(getTeilnehmerIn(nr));
        }

        return liste;

    }

    public ArrayList<Noten> getNotenFuerTN(int teilInNr) {
        ArrayList<Noten> noten =new ArrayList<Noten>();
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selectNoten = "SELECT NotenId, Fach, Note fROm Noten WHERE teilinNr = ?";

            PreparedStatement pSelect = conn.prepareStatement(selectNoten);
            pSelect.setInt(1,teilInNr);

            ResultSet rs = pSelect.executeQuery();

            while (rs.next()) {
                Noten n=new Noten();
                n.setNotenId(rs.getInt("NotenId"));
                n.setTeilInNr(teilInNr);
                n.setFach(rs.getString("Fach"));
                n.setNote(rs.getInt("Note"));
                noten.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return noten;

    }



    public String updateOrInsertTeilnehmerIn(TeilnehmerIn teilnehmerIn){

        String action="";
        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            int teilInNr=getTeilInNr(teilnehmerIn.getVorname(),teilnehmerIn.getNachname());
            if (teilInNr>-1){
                //Update
                teilnehmerIn.setTeilInNr(teilInNr);
                updateTeilnehmerIn(teilnehmerIn);
                action="wurde geändert";
            } else{
                //Insert
                insertTeilnehmerIn(teilnehmerIn);
                action="wurde hinzugefügt";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      return  action;

    }

    public int getTeilInNr(String vorname, String nachname) {

       int teilInNr=-1;

        try (Connection conn = DriverManager.getConnection(dbConnectionString)) {

            String selTeilnehmerIn = "SELECT TeilInNr FROM TeilnehmerInnen WHERE Vorname=? AND Nachname = ?";
            PreparedStatement pSelect = conn.prepareStatement(selTeilnehmerIn);
            pSelect.setString(1,vorname);
            pSelect.setString(2,nachname);
            ResultSet rs=  pSelect.executeQuery();

            if (rs.next()){
                teilInNr = rs.getInt("TeilInNr");
            }
            else {
                teilInNr= -1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  teilInNr;
    }

    //b. alle Noten von einer TeilnehmerIn  --- printNotenFuerTeilInNr(3) PreparedStatement, bei keine Noten - es gibt keine Noten
    //ArrayList<Note> getAlleNotenFuerTN(4)
    //1. neue Klasse Noten
}
