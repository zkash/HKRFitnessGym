/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author shameer
 */
public class DBHandler {

    private final String dbName = "HKRFitnessGymDB";
    private final String user = "root";
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost/" + dbName + "?user=" + user + "&password=" + password + "&useSSL=false";

    private static ArrayList<Admin> admins;
    private static ObservableList<Admin> data = FXCollections.observableArrayList();
    

    public static ObservableList<Admin> adminViewAccounts(String table) throws SQLException {
        Connection conn = establishConnection();
        try {
//            String query = String.format("SELECT firstName, middleName, lastName, username, ssn FROM `%s`", table.replace("`", "``"));
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn, phoneNumber, address, email, gender, dateOfBirth FROM `%s`", table.replace("`", "``"));
            PreparedStatement statement = conn.prepareStatement(query);
            //System.out.println(statement);
            ResultSet rs = statement.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                //System.out.println(rs.getDate("dateOfBirth"));
                
              data.add(new Admin(rs.getString("firstname") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
//                rs.getString("username"),
//                rs.getInt("ssn")
//              ));   
                      rs.getString("username"),
                    rs.getString("gender"),
                   // Helper.DateToString(rs.getDate("dateOfBirth")),
                      rs.getDate("dateOfBirth"),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    rs.getInt("ssn")
              ));   
            }
            
            return data;
        } 
        catch (Exception e) {

        }
        return null;
    }

    public static void createAdminAccount(String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssnum, String uname, String pwd) {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Admin (firstName, middleName, lastName, gender, "
                    + "dateOfBirth, address, phoneNumber, email, ssn, username, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, fn);
            statement.setString(2, mn);
            statement.setString(3, ln);
            statement.setString(4, gen);
            statement.setDate(5, dob);
            statement.setString(6, add);
            statement.setInt(7, pnum);
            statement.setString(8, ead);
            statement.setInt(9, ssnum);
            statement.setString(10, uname);
            statement.setString(11, pwd);
            statement.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createMemberAccount(String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssnum, String uname, String pwd, int adminSSN) throws SQLException {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Member (firstName, middleName, lastName, gender, "
                    + "dateOfBirth, address, phoneNumber, email, ssn, username, password, Admin_ssn) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, fn);
            statement.setString(2, mn);
            statement.setString(3, ln);
            statement.setString(4, gen);
            statement.setDate(5, dob);
            statement.setString(6, add);
            statement.setInt(7, pnum);
            statement.setString(8, ead);
            statement.setInt(9, ssnum);
            statement.setString(10, uname);
            statement.setString(11, pwd);
            statement.setInt(12, adminSSN);
            statement.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection establishConnection() {
        Connection conn;

        //Get connection to database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HKRFitnessGymDB", "root", "root");
            if (conn != null) {
                System.out.println("connected to database successfully");
                return conn;
            }
        } catch (Exception ex) {
            System.out.println("Not connected to database");
        }
        return null;
    }

    public static void getAdminUsername(int ssn) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT username FROM Admin WHERE ssn = " + ssn;
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("username"));
        }
    }

    public static ObservableList<Admin> searchInAdminViewAccounts(String str) throws SQLException {
        ObservableList<Admin> searchData = FXCollections.observableArrayList();
        System.out.println("hehrehh");
        Connection conn = establishConnection();
        
        String query = "SELECT * FROM Admin WHERE firstName LIKE '%" + str + "%'"
                + "OR middleName LIKE '%" + str + "%'"
                + "OR lastName LIKE '%" + str + "%'"
                + "OR address LIKE '%" + str + "%'"
                + "OR username LIKE '%" + str + "%'";
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.println(statement);
        
        ResultSet rs = statement.executeQuery();
        //System.out.println(rs);
        while (rs.next()) {
            //System.out.println(rs.getString("firstName"));
//              searchData.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
//                rs.getString("username"),
//                rs.getInt("ssn")
//              ));  

                searchData.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getDate("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    rs.getInt("ssn")
              ));   
            }
        return searchData;
    }

    public static void searchInAdminViewPackage(String str) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT * FROM Package WHERE nameOfPackage LIKE '%" + str + "%'";

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("nameOfPackage"));
        }
    }

    public static boolean checkUsernameAndSSN(String table, String uname, int ssn) throws SQLException {
        Connection conn = establishConnection();
        System.out.println(uname);
        System.out.println(ssn);
        String query = "SELECT count(*) FROM " + table + " WHERE username = ? OR ssn = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, uname);
        statement.setInt(2, ssn);
        System.out.println(statement);
        ResultSet rs = statement.executeQuery();
        boolean alreadyExists = false;
        System.out.println(rs);
        while (rs.next()) {
            alreadyExists = rs.getInt(1) != 0;
        }
        System.out.println(alreadyExists);
        return alreadyExists;
    }
}
