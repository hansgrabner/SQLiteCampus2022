package at.campus02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Good Morning Campus2");
        createGameTable("MyGameDB.db");
        //Init - DBHelper
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
        try (Connection conn = DriverManager.getConnection(url)){
             //2 Statement über die Connection holen - createStatement
            Statement stmt = conn.createStatement();
            //3 Statement abschicken --- execute
            stmt.execute(ddlStatementToCreateATable);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
