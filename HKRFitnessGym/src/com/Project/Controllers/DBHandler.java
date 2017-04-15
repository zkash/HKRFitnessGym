/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author shameer
 */

public class DBHandler {
    private final String dbName = "HKRFitnessGymDB";
    private final String user = "root" ;
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost/" + dbName  + "?user=" + user + "&password=" + password + "&useSSL=false";
    
    public void printAll() {
        try (Connection conn = (Connection) DriverManager.getConnection(connectionURL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELCT * FROM Admin");
            while(rs.next()) {
                System.out.printf("[%d] --> %s%n", rs.getInt("ssn"), rs.getString("adminFirstName"));
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
            
            //"jdbc:mysql://1270.0.0.1:3306/hkrfitnessgymdb?user=root&password=root";
    
//    //constructor will manage establishing a connection to the database
//    public DatabaseConnection() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            
//        }
//    }
    
}
