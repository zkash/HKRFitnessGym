/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;

/**
 *
 * @author shameer
 */

public class DBHandler {
    private final String dbName = "HKRFitnessGymDB";
    private final String user = "root" ;
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost/" + dbName  + "?user=" + user + "&password=" + password + "&useSSL=false";
    
    public static void adminViewAccounts() throws SQLException {
        Connection conn = establishConnection();
        ResultSet rs = null;
        ArrayList<ArrayList<String>> finalArray = new ArrayList<>();
        
        List<Admin> admins = new ArrayList<>();
        
        if (conn != null) {
            
            
            //TODO change this to member table
            try ( //Create a statement
                    Statement statement = conn.createStatement()) {
                //Execute SQL query
                //TODO change this to member table
                String sql = "select adminFirstName, username from Admin";
                rs = statement.executeQuery(sql);
                ArrayList<String> array = new ArrayList<>();
                System.out.println(rs);

                System.out.println("All records have been selected");
                //finalArray.add(array);  
            }
            System.out.println("Statement closed");
            
            System.out.println("connection closed");
            conn.close();
        }

    }
    
    public static void createUserAccount(String fn, String mn, String ln, String gen, String dobStr, String add, String pnum, String ead, String ssnum, String uname, String pwd) throws SQLException {
        Connection conn = establishConnection();
        ResultSet rs = null;
                
        if(conn != null) {
            try {
                String query = "INSERT INTO Member (memberFirstName, memberMiddleName, memberLastName, "
                        + "gender, dateOfBirth, address, phoneNumber, email, ssn, username, password) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, fn);
                statement.setString(2, mn);
                statement.setString(3, ln);
                statement.setString(4, gen);
                statement.setString(5, dobStr);
                statement.setString(6, add);
                statement.setInt(7, Integer.parseInt(pnum));
                statement.setString(8, ead);
                statement.setInt(9, Integer.parseInt(ssnum));
                statement.setString(10, uname);
                statement.setString(11, pwd);

                statement.execute();
                conn.close();
        }
            catch(Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }   
        } 
    }
    
    public static Connection establishConnection() {
        Connection conn;

        //Get connection to database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HKRFitnessGymDB", "root", "root");
            if(conn!=null) {
                System.out.println("connected to database successfully");
                return conn;
            }
        }
        catch (Exception ex) {
            System.out.println("Not connected to database");
        }
        return null;
    }
}
