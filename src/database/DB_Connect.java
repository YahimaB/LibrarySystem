package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class DB_Connect {

    //Method used to connect to the database
    static Connection Connect() {
        Connection connection = null;
        //Try to establish connection
        try {
            //Actual path to the database
            String url = "jdbc:sqlite:src/resources/data/DataBase.db";
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            //If unsuccessful
            System.out.println("Could not connect to the database:" + e.getMessage());
        }

        return connection;
    }

}
