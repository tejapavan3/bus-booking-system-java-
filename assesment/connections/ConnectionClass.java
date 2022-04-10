package com.ford.assesment.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    Connection con;
    public Connection getMyConnection() {
        String url = "jdbc:h2:tcp://localhost/~/test";
        String user = "sa";
        String password = "";
        try {

            //Step 1 : Load Driver
            Class.forName("org.h2.Driver");

            //Step2 Establish Connection
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return con;
    }
}
