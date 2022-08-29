package at.campus02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Good Morning Campus2");
      // createGameTable("MyDonnerstag.db");
        //DBHelper myHelper =new DBHelper();
        //myHelper.createDB("V1.db");
       // myHelper.createKundenTable("V1.db");
        //Init - DBHelper
        //shouldThrowNoDriverNotFound("Test.db");
        //shouldThrowDirectoryNotFound("Egal.db");
       // shouldCreateADatabaseAndThrowSQLExceptionTableStatementIncorrect("Test1.db");
        //shouldCreateADatabaseAndATable("DonnerstagV5.db");
        DBNotenHelper myHelper =new DBNotenHelper();

        MyNotenOnlyDBHelper notenHelper=new MyNotenOnlyDBHelper();


        TeilnehmerIn teilnehmerIn =new TeilnehmerIn(6,"Elisabeth","Unger",200);
        notenHelper.updateTeilnehmerIn(teilnehmerIn);

        TeilnehmerIn teilnehmerIn3 =new TeilnehmerIn(3,"Karolina","Wasalska",250);
        notenHelper.updateTeilnehmerIn(teilnehmerIn3);

        TeilnehmerIn t5 = notenHelper.getTeilnehmerIn(5);
        System.out.println(t5);
        t5.setBonuspunkte(30);
        System.out.println(t5);
        notenHelper.updateTeilnehmerIn(t5);

        ArrayList<TeilnehmerIn> alleTeilnehmerInnen = notenHelper.getAlleTeilnehmerInnen();
        System.out.println("Alle TN:\n" + alleTeilnehmerInnen);
        notenHelper.deleteNoten(3);




        TeilnehmerIn suche = notenHelper.getTeilnehmerIn(6);

        TeilnehmerIn tNeu =new TeilnehmerIn(-1, "Fatima","Brugger",170);
        //notenHelper.insertTeilnehmerIn(tNeu);

        System.out.println("Mehr als 100 Bonuspunkte: " + notenHelper.getAlleTeilnehmerInnenMitFilter(100));


       // notenHelper.createTableNoten("MeineNoten.db");
        //notenHelper.insertNoten("MeineNoten.db",3,"DBP",3);
        //notenHelper.deleteTeilnehmerIn("MeineNoten.db",3);
       // notenHelper.selectTeilnehmerInnen("MeineNoten.db");
       // notenHelper.printAllNoten("MeineNoten.db");
      //  notenHelper.insertNotenPrepared("MeineNoten.db",3,"Web",1);
        //myHelper.createDB("MeineNoten.db");
        //myHelper.createTableTeilnehmerInnen("MeineNoten.db"); // würde zu Fehler bei meherfacher Ausführung führen
       // myHelper.createTableNoten("MeineNoten.db");
       // myHelper.insertTeilnehmerIn("MeineNoten.db");
       // myHelper.updateTeilnehmerIn("MeineNoten.db");
       // myHelper.updateTeilnehmerIn("MeineNoten.db",7,"Hans","Grabner",120);

        /*
        int affectedRows = myHelper.deleteTeilnehmerIn("MeineNoten.db",1);

        if (affectedRows==0){
            System.out.println("TeilInNr wurde nicht gefunden");
        } else {
            System.out.println("TeilnehmerIn wurde gelöscht");
        }
    //    myHelper.insertTeilnehmerInMitVornameNachnameUndBonuspunkten
       //         ("MeineNoten.db","Hans","Grabner",70);
        affectedRows = myHelper.updateTeilnehmerInMitVornameNachnameUndBonuspunkten("MeineNoten.db",2,"Johann","Grabner",220);

        if (affectedRows==0){
            System.out.println("TeilInNr wurde nicht gefunden");
        } else {
            System.out.println("TeilnehmerIn wurde geändert");
        }
        */
        /*
         MyNotenOnlyDBHelper notenOnlyDBHelper =new MyNotenOnlyDBHelper();
        // notenOnlyDBHelper.insertNoten("MeineNoten.db",2,"Web Design",3);
        int rowsAffected = notenOnlyDBHelper.updateNoten("MeineNoten.db",1,"Web Design",2);

        if (rowsAffected==0){
            System.out.println("Eintrag nicht gefunden");
        } else {
            System.out.println("note wurde geändert");
        }
        rowsAffected = notenOnlyDBHelper.deleteNote("MeineNoten.db",1);

        if (rowsAffected==0){
            System.out.println("Eintrag nicht gefunden");
        } else {
            System.out.println("note wurde gelöscht");
        }*/

    }

        /*
        Auflockerungs-Übung - Simulieren Sie folgende Fehler - Debug und Doku:
    -- no suitable Driver found
    -- Directory for Database not found
    -- Syntax Error in SQL-Statement
    --Tabelle zum Verwalten von "tagesaktuellen Menüs erzeugen" - "CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))"
                --"prüfen" und SQLite Studio neue Menüs hinzufügen
     */

    public  static void shouldThrowNoDriverNotFound(String fileName ) {
        String url = "jdbc:sqliteDummy:C:\\LVs\\DBP2022\\db\\" +fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            boolean warErfolgreich =  stmt.execute("CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))");
        } catch (SQLException e) {
            //No sutiable driver found
            //1. Grund: Driver (richtiges JAR wurde nicht gefunden oder nicht referenziert -- c:\meineDriver\sqlitev17.jar
            //2. Grund: falschen Driver angegeben --- url="jdbc:einDummy:c:\
            System.out.println(e.getMessage());
        }
    }

    public  static void shouldThrowDirectoryNotFound(String fileName ) {
        String url = "jdbc:sqlite:C:\\xyzDirDoesNotExist\\DBP2022\\db\\" +fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            boolean warErfolgreich =  stmt.execute("CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public  static void shouldCreateADatabaseAndThrowSQLExceptionTableStatementIncorrect(String fileName ) {
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +fileName;
        try (Connection conn = DriverManager.getConnection(url)) { //Connects or creates a Database
            Statement stmt = conn.createStatement();
            boolean warErfolgreich =  stmt.execute("CREAtE TABLE  Menue(MenueId int17 : Bezeichnung varchar(20))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  static void shouldCreateADatabaseAndATable(String fileName ) {
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +fileName;
        try (Connection conn = DriverManager.getConnection(url)) { //Connects or creates a Database
            Statement stmt = conn.createStatement();
            boolean warErfolgreich =  stmt.execute("CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  static void createGameTable(String fileName ) {
        // SQLite connection string
        //url "jdbc:
        //jdbc:sqllite
        //jdbc:oracle
        //jdbc:access
        //jdbc:sqlite:PFAD





        /*
        Connection conn =null;

        try{
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

        }
        catch (SQLException ex)
        {

        }
        finally {
            if (conn!=null){
                try    {
                    conn.close();
                }
                catch(SQLException ex){

                }

            }
        }


         */
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +fileName;

        // SQL statement for creating a new table
        String ddlStatementToCreateATable = "CREATE TABLE Game (\n"
                + "	GameId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	GameName VARCHAR(255),\n"
                + "	GameGenre VARCHAR(255), \n"
                + "	MaxLevel INTEGER \n"
                + ");";
        //1. Connection aufbauen -- Driver muss vorhanden sein - Project Structure - Modules - Dependeny - sqllite-jdbc-3.36.0.1.jar (Moodle)
        try (Connection conn = DriverManager.getConnection(url)) {
             //2 Statement über die Connection holen - createStatement
            Statement stmt = conn.createStatement();
            //3 Statement abschicken --- execute
            boolean warErfolgreich =  stmt.execute("CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        /*
        Auflockerungs-Übung - Simulieren Sie folgende Fehler - Debug und Doku:
        -- no suitable Driver found
        -- Directory for Database not found
        -- Syntax Error in SQL-Statement
        --Tabelle zum Verwalten von "tagesaktuellen Menüs erzeugen" - "CREAtE TABLE Menue(MenueId int, Bezeichnung varchar(20))"
        --"prüfen" und SQLite Studio neue Menüs hinzufügen
         */

        //finally conn.close();
        //connection
    }
}
