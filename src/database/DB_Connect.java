package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB_Connect {
    public static String url = "jdbc:sqlite:src/resources/data/DataBase.db";

    //Method used to connect to the database
    static Connection Connect() {
        Connection connection = null;
        //Try to establish connection
        try {
            //Actual path to the database
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println("Could not connect to the database:" + e.getMessage());
        }

        return connection;
    }

}
