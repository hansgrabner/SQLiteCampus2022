package at.campus02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyNotenOnlyDBHelper {
    public void createTableNoten(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlToCreateNoten="CREATE TABLE Noten\n" +
                    "(\n" +
                    "    NotenId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    TeilInNr int, -- Foreign Key Constrain wird nachträglich hinzugefügt\n" +
                    "    Fach varchar(50),\n" +
                    "    Note int\n" +
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

    public void insertNoten(
            String dbName,
            int teilNr,
            String fach,
            int note){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {


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

    public int updateNoten(String dbName, int notenId, String geaendertesFach,
            int geaenderdeNote){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        int rowCount=0;
        try (Connection conn = DriverManager.getConnection(url)) {

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

    public void selectAllteoten(String dbName, int teilNr){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;

        try (Connection conn = DriverManager.getConnection(url)) {

            String selectAlleNoten= "SELECT * FROM NOTEN WHERE TeilInNr="+teilNr;
            Statement notenSelectStmt = conn.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
