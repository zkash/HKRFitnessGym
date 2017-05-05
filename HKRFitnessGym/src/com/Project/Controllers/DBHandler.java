/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author shameer
 */

public class DBHandler {
    private final String dbName = "hkrfitnessgymdb";
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
    
    public static void adminCreateSchedule(Date date, String op, String ct, boolean isHoliday, int adminId) throws SQLException {
        //String command = String.format("INSERT INTO schedule values (%b, %d, %d)", isHoliday, /*id,*/ ssn);
        //String c = "insert into schdeule (date, openningTime, closingTime, isHoliday, ssn) values ('"date + op + ct + isHoliday + ssn + ")'";
        try(Connection conn = establishConnection();){
            String selectStatement = "INSERT INTO schedule ( date, openingTime, closeTime, isHoliday, Admin_adminId) VALUES (?,?,?,?,?)";
            PreparedStatement prepStmt = (PreparedStatement) conn.prepareStatement(selectStatement);
            prepStmt.setDate(1, date); // remove ++ from here, do it in last
            prepStmt.setString(2, op);
            prepStmt.setString(3, ct);
            prepStmt.setBoolean(4, isHoliday);
            prepStmt.setInt(5, adminId);
            prepStmt.executeUpdate();
            
            System.out.println("the data has been moved into database.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static ResultSet adminRitriveSchedule(){
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            String statement = "select * frome schedule";
            conn = establishConnection();
            prepStmt = conn.prepareStatement(statement);
            rs = prepStmt.executeQuery();
            
        } catch (Exception e) {
            System.out.println("Cannot ritrive schedule.");
        }
        return rs;
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
