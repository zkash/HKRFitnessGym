/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DAO;

import com.Project.Models.Admin;
import com.Project.JDBC.DTO.Announcements;
import com.Project.JDBC.DTO.Chat;
import com.Project.JDBC.DTO.Schedule;
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
import javafx.collections.ObservableList;

/**
 *
 * @author KN
 */

public class DBHandler {
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
    
       // Checks user credentials if they are correct and returns memberId.
           public static int login(String userName, String password){
            PreparedStatement stmt;
            ResultSet rs;
            int memberId = 0;
            try{
                stmt = c.prepareStatement("SELECT * FROM person WHERE Username = ?");
                stmt.setString(1, userName);
                rs = stmt.executeQuery();
                
                while(rs.next()){
                    if(userName.equals(rs.getString("Username")) && password.equals(rs.getString("Password"))){
                    memberId = rs.getInt("idPerson");
                    currentUser = userName;
                    position = rs.getString("Position");
                    idMember = rs.getInt("idPerson");
                    
                }
              }
            } catch (Exception e){
                e.printStackTrace();
            }
            return memberId;
         }
      
        //Returns member id.
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
        
        //To get list of messages from database
        public static List<Chat> getMessageList(String query) {
            List<Chat> messageList = new LinkedList<>();
            PreparedStatement stmt;
            ResultSet rs;
            try{
                stmt = c.prepareStatement(query);
                rs = stmt.executeQuery();
                
                while (rs.next()){
                    Chat message = new Chat();
                    message.setMessageId(rs.getInt("idChat"));
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
         // save message into database.
    public static void saveMessage(String time, String name, String message) {
        PreparedStatement stmt;
        
        try{
            stmt = c.prepareStatement("Insert Into chat" + "(time, message, name)" 
                    + "Values (?, ?, ?)");
            stmt.setString(1, time);
            stmt.setString(2, message);
            stmt.setString(3, name);
            stmt.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
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
            stmt = c.prepareStatement("Insert Into announcements"
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
    //To get message list from database.
    public static List<Announcements> getAnnouncementsList(String query) {
        List<Announcements> messageList = new LinkedList<>();
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = c.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Announcements announcements = new Announcements();
                announcements.setMessageId(rs.getInt("idAnnouncements"));
                announcements.setTime(rs.getString("time"));
                announcements.setMessage(rs.getString("message"));
                messageList.add(announcements);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
    }
     
     public static void adminCreateSchedule(Schedule s, int adminId) throws SQLException {
        //String command = String.format("INSERT INTO schedule values (%b, %d, %d)", isHoliday, /*id,*/ ssn);
        //String c = "insert into schdeule (date, openningTime, closingTime, isHoliday, ssn) values ('"date + op + ct + isHoliday + ssn + ")'";
        try(Connection conn = establishConnection();){
            String selectStatement = "INSERT INTO schedule ( date, openingTime, closingTime, isHoliday, Admin_adminId) VALUES (?,?,?,?,?)";
            PreparedStatement prepStmt = (PreparedStatement) conn.prepareStatement(selectStatement);
            prepStmt.setDate(1, s.getDate()); // remove ++ from here, do it in last
            prepStmt.setString(2, s.getOpeningTime());
            prepStmt.setString(3, s.getClosingTime());
            prepStmt.setBoolean(4, s.getIsHoliday());
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
            String statement = "SELECT date, openingTime, closeTime, isHoliday, scheduleId FROM schedule";
            conn = establishConnection();
            prepStmt = conn.prepareStatement(statement);
            rs = prepStmt.executeQuery();
            
        } catch (Exception e) {
            System.out.println("Cannot ritrive schedule.");
        }
        return rs;
    }
    
    public static void deleteSchedule(Date date){
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            String statement = "DELETE FROM Schedule WHERE date = ?";
            conn = establishConnection();
            prepStmt = conn.prepareStatement(statement);
            prepStmt.setDate(1, date);
            prepStmt.execute();
            System.out.println("Success removed");
            
        } catch (Exception e) {
            System.out.println("error. Not delete.");
        }
    }
    
    public static ResultSet searchSchedule(String date){
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            String statement = "SELECT * FROM Schedule WHERE date LIKE\"%" + date + "%\"";
            conn = establishConnection();
            prepStmt = conn.prepareStatement(statement);
            rs = prepStmt.executeQuery();
            System.out.println("Success");
            
        } catch (Exception e) {
            System.out.println("error. Not found.");
        }
        return rs;
    }
}
