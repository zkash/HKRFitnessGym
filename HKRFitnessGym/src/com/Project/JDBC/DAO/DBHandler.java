/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DAO;

import com.Project.Controllers.Admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shameer
 */

public class DBHandler {
    private static Connection c;
    private final String dbName = "HKRFitnessGymDB";
    private final String user = "root" ;
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost/" + dbName  + "?user=" + user + "&password=" + password + "&useSSL=false";
    
    // Connection to database.
    public static void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //String URL
            String URL = "jdbc:mysql://127.0.0.1:3306/hkrfitnessgym?user=root&password=root";
            c = DriverManager.getConnection(URL);
            if (c != null)
                System.out.println("Connected to the database");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex){
                        
        }
    }
    
    // To Disconnect the database.
        public static void shutDown(){
            try{
                c.close();
            }
            catch (SQLException ex){
        }
    }
    
    
    
    
    /*public static void adminViewAccounts() throws SQLException {
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
    }*/
}
