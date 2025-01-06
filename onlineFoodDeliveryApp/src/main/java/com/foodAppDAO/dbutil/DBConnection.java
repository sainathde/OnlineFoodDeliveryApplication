package com.foodAppDAO.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private static Connection con;

    // Method to establish the database connection
    public static Connection connect() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish and return the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapplication", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
