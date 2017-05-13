/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DAO;

import com.Project.JDBC.DTO.Announcement;
import com.Project.Models.Admin;
import com.Project.JDBC.DTO.Chat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author KN
 */

public class DBhandler {
    private static Connection c;
    private static String currentUser;
    private static String position;
    private static int idMember;
    
    // Connection to database.
    public static void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //String URL
            String URL = "jdbc:mysql://127.0.0.1:3306/hkrfitnessgymdb?user=root&password=sunday1";
            c = DriverManager.getConnection(URL);
            if (c != null)
                System.out.println("Connected to the database");
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex){
                        
        }
    }
    
    // Disconnect the database.
        public static void shutDown(){
            try{
                c.close();
            }
            catch (SQLException ex){
        }
   }
        
         
    public static Connection establishConnection() {
        Connection conn;

        //Get connection to database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HKRFitnessGymDB", "root", "sunday1");
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

    public static Iterable<Announcement> getAnnouncementList(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
        //Returns User id.
      public int getId(String accountType) {
        Connection conn = establishConnection();
        String query = "";
        if(accountType.equals("Admin")) {
            query = "SELECT adminId FROM Admin = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT memberId FROM Member = ?";
        }
        int id = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, accountType);
            ResultSet rs = statement.executeQuery();
            System.out.println(statement);
            System.out.println(rs);
            while(rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch(SQLException e) {
            
        }
        return id;
    }
     
      public static int getLoggedUserId(){
          return idMember;
     }
        // Returns logged user.
        public static String getLoggedUser(){
            return currentUser;
    }
        // Returns positon of user.
        public static String getLoggedUserPosition(){
            return position;
    }        
   
     // Saves message into database.
    public static void saveMessage(String time, String name, String message){
        try {  
            PreparedStatement newMessage = c.prepareStatement("INSERT INTO message"
                    + "(time, name, message)"
                    + "VALUES (?, ?, ?)");
            newMessage.setString(1, time);
            newMessage.setString(2, name);
            newMessage.setString(3, message);
            newMessage.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Gets list of messages from database.
    public ObservableList<Chat> getMessageList() throws SQLException {
       ObservableList<Chat> messageList = FXCollections.observableArrayList();
       Connection conn = establishConnection();
        try {
            String query = String.format("SELECT * FROM message");
            PreparedStatement check = c.prepareStatement(query);
            ResultSet rs = check.executeQuery();
            
            while (rs.next()) {
                Chat message = new Chat();
                message.setMessageId(rs.getInt("messageId"));
                message.setTime(rs.getString("time"));
                message.setName(rs.getString("name"));
                message.setMessage(rs.getString("message"));
                messageList.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
    }
        
   // Update password for a particular member.
    public static void updatePassword(String password) {
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement("UPDATE person"
                    + " SET Password = ?"
                    + " WHERE idPerson = ?");
            stmt.setString(1, password);
            stmt.setInt(2, getLoggedUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // To save announcements into database.
    public static void saveAnnouncement(String time, String message) {
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement("Insert Into announcement"
                    + "(time, message)"
                    + "Values (?, ?, ?)");
            stmt.setString(1, time);
            stmt.setString(2, message);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
   
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

   
    //To get message list from database.
    public ObservableList<Announcement> getAnnouncementList() throws SQLException {
       ObservableList<Announcement> messageList = FXCollections.observableArrayList();
       Connection conn = establishConnection();
        try {
            String query = String.format("SELECT * FROM announcement");
            PreparedStatement check = c.prepareStatement(query);
            ResultSet rs = check.executeQuery();
            
            while (rs.next()) {
                Announcement announcements = new Announcement();
                announcements.setMessageId(rs.getInt("announcementId"));
                announcements.setTime(rs.getString("time"));
                announcements.setMessage(rs.getString("message"));
                messageList.add(announcements);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
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
}