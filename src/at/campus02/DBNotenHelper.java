package at.campus02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBNotenHelper {

    private String _dbName;


    public void createDB(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableTeilnehmerInnen(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String ddlToCreateTeilnehmerInnen="CREATE TABLE TeilnehmerInnen\n" +
                    "(\n" +
                    "    TeilInNr INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Vorname VARCHAR(20),\n" +
                    "    Nachname VARCHAR(20),\n" +
                    "    Bonuspunkte decimal(10,2)\n" +
                    ")";

            Statement ddlCreateTeilnehmerInnenStmt = conn.createStatement();

            ddlCreateTeilnehmerInnenStmt.execute(ddlToCreateTeilnehmerInnen);

            System.out.println("Table TeilnehmerInnen succesfully created");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableNoten(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String ddlToCreateNoten="CREATE TABLE Noten\n" +
                    "(\n" +
                    "    NotenId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    TeilInNr int, -- Foreign Key Constrain wird nachträglich hinzugefügt\n" +
                    "    Fach varchar(50),\n" +
                    "    Note int\n" +
                    ")";

            Statement ddlCreateNotenStmt = conn.createStatement();

            ddlCreateNotenStmt.execute(ddlToCreateNoten);

            System.out.println("Table Noten succesfully created");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTeilnehmerIn(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String insertTeilnehmerIn="INSERT INTO TeilnehmerInnen(Vorname, Nachname, Bonuspunkte) ";
            insertTeilnehmerIn += " VALUES('Karloina','Wasalska',120)";

            Statement teilInsStmt = conn.createStatement();

            teilInsStmt.executeUpdate(insertTeilnehmerIn); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("TeilnehmerIn inserted");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTeilnehmerIn(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            String teilUpd="UPDATE TeilnehmerInnen SET Vorname='Karolina' WHERE Vorname='Karloina' AND TeilInNr=1  ";


            Statement teilUpdStmt = conn.createStatement();

            teilUpdStmt.executeUpdate(teilUpd);

            System.out.println("TeilnehmerIn inserted");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int deleteTeilnehmerIn(String dbName, int teilINr){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(url)) {

            String teilDel="DELETE FROM TeilnehmerInnen WHERE TeilInNr=" + teilINr;
            //DELETE TeilnehmerInnen WHERE TeilInNr=7
            Statement teilDelStmt = conn.createStatement();
            rowCount= teilDelStmt.executeUpdate(teilDel);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowCount;
    }

    public void insertTeilnehmerInMitVornameNachnameUndBonuspunkten(
                String dbName,
                String vorname,
                String nachname,
                int bonuspunkte){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println(dbName + " succesfully created or connected");

            //insertTeilnehmerIn += " VALUES('Karloina','Wasalska',120)";
            String insertTeilnehmerIn="INSERT INTO TeilnehmerInnen(Vorname, Nachname, Bonuspunkte) ";
            insertTeilnehmerIn += " VALUES(";
            insertTeilnehmerIn += " '";
            insertTeilnehmerIn += vorname;
            insertTeilnehmerIn += "',";
            insertTeilnehmerIn += "'" + nachname + "', " + bonuspunkte + ")";

            Statement teilInsStmt = conn.createStatement();

            teilInsStmt.executeUpdate(insertTeilnehmerIn); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate

            System.out.println("TeilnehmerIn inserted");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int updateTeilnehmerInMitVornameNachnameUndBonuspunkten(
            String dbName,
            int teilInNr,
            String neuerVorname,
            String neuerNachname,
            int neueBonuspunkte){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;

        int rowsAffected=0;
        try (Connection conn = DriverManager.getConnection(url)) {
            //Update TeilnehmerInnen SET Vorname=neuerVorname, Nachname=neuerNachname, Bonuspunkte=neueBonuspunkte WHERE TeilInNr=teilInNr

            String updString = "UPDATE TeilnehmerInnen SET ";
            updString += " Vorname='" + neuerVorname + "', ";
            updString += " Nachname='" + neuerNachname + "', ";
            updString += " bonuspunkte=" + neueBonuspunkte + " ";
            updString += " WHERE TeilInNr=" + teilInNr;



            Statement teilUpdStmt = conn.createStatement();

            rowsAffected= teilUpdStmt.executeUpdate(updString); //bei DML-Statements (INSERT; UPDATE, DELETE) --> executeUpdate



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    //Aufgaben
    //Annahme Tabelle Note existiert bereits
    //1. insertNoten(teilInNr, fach, note)
    //2. updateNote(notenId, neuesFach, neueNote) ---> Ausgabe - Datensatz wurde geändert oder NICHT gefunden   7, 'DBP mit Java', 1
    //updateNote(7, 'DBP mit Java', 1)
    //2. deleteNote(notenId) ---> Note wurde gelöscht oder wurde nicht gefunden
    //Zeitabschätzung? - gemeinsamer Treffpunkt 14:30 Uhr -


}
