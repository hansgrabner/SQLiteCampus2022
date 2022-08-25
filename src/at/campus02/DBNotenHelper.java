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

            teilInsStmt.executeUpdate(insertTeilnehmerIn);

            System.out.println("TeilnehmerIn inserted");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
