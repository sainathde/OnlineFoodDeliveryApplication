package com.foodAppDAO.dbutil;

import java.sql.Connection;

public class TestDBConnection {

    public static void main(String[] args) {
        // Test the DBConnection
        Connection connection = DBConnection.connect();

        // Check if the connection is successful
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}
