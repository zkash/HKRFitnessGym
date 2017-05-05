/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DAO;

import com.Project.Controllers.Admin;
import com.Project.JDBC.DTO.Announcements;
import com.Project.JDBC.DTO.Chat;
import com.Project.JDBC.DTO.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author shameer
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
      
      // Retrieves information from database
    public static List<Person> getMemberList(String query) {
        List<Person> memberList = new LinkedList<>();
        ResultSet rs;
        try {
            Statement st = c.createStatement();
            rs = st.executeQuery(query);
            
            while (rs.next()) {
                Person member = new Person();
                member.setIdMember(rs.getInt("idPerson"));     
                member.setPosition(rs.getString("Position"));     
                member.setEmail(rs.getString("Email"));
                member.setUsername(rs.getString("Username"));
                member.setPassword(rs.getString("Password"));
                memberList.add(member);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return memberList;
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

}

