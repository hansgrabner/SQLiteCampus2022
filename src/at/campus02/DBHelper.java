package at.campus02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

    public void createDB(String dbName){
        String url = "jdbc:sqlite:C:\\LVs\\DBP2022\\db\\" +dbName;
        try (Connection conn = DriverManager.getConnection(url)) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
