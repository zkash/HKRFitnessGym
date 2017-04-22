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

    static void createPackage(String pn, String pc, LocalDate psd, LocalDate ped, String pst, String pd, int admin_ssn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final String dbName = "HKRFitnessGymDB";
    private final String user = "root";
    private final String password = "root";
    private final String connectionURL = "jdbc:mysql://localhost/" + dbName + "?user=" + user + "&password=" + password + "&useSSL=false";

    private static ArrayList<Person> persons;
    
    public static ObservableList<Admin> adminViewAdminAccounts() throws SQLException {
        ObservableList<Admin> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn, phoneNumber, address, email, gender, dateOfBirth FROM Admin");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                data.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getString("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
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
    
    public static ObservableList<Member> adminViewMemberAccounts() throws SQLException {
        ObservableList<Member> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn, phoneNumber, address, email, gender, dateOfBirth FROM Member");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                data.add(new Member(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getString("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
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

    public static ObservableList<Admin> searchInAdminViewAdminAccounts(String str, String table) throws SQLException {
        ObservableList<Admin> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
//        String query = "SELECT * FROM $tableName WHERE firstName LIKE '%" + str + "%'"
//                + "OR middleName LIKE '%" + str + "%'"
//                + "OR lastName LIKE '%" + str + "%'"
//                + "OR address LIKE '%" + str + "%'"
//                + "OR username LIKE '%" + str + "%'";
//        query = query.replace("$tableName", table);
//        System.out.println(query);
//        PreparedStatement statement = conn.prepareStatement(query);
//        //statement.setString(1, table);
//        //System.out.println(statement);
//        
//        ResultSet rs = statement.executeQuery();
//        //System.out.println(rs);
//        while (rs.next()) {
//           // System.out.println(rs.getString("address"));
//            
//            //System.out.println(rs.getString("firstName"));
////              searchData.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
////                rs.getString("username"),
////                rs.getInt("ssn")
////              ));  
//
//                searchData.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
//                    rs.getString("username"),
//                    rs.getString("gender"),
//                    rs.getString("dateOfBirth"),
//                    //Helper.DateToString(rs.getDate("dateOfBirth")),
//                    rs.getString("address"),
//                    rs.getInt("phoneNumber"),
//                    rs.getString("email"),
//                    rs.getInt("ssn")
//              ));   
//            }
        return searchData;
    }

    
    public static ObservableList<Member> searchInAdminViewMemberAccounts(String str, String table) throws SQLException {
        ObservableList<Member> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        String query = "SELECT * FROM $tableName WHERE firstName LIKE '%" + str + "%'"
                + "OR middleName LIKE '%" + str + "%'"
                + "OR lastName LIKE '%" + str + "%'"
                + "OR address LIKE '%" + str + "%'"
                + "OR username LIKE '%" + str + "%'";
        query = query.replace("$tableName", table);
        System.out.println(query);
        PreparedStatement statement = conn.prepareStatement(query);
        //statement.setString(1, table);
        //System.out.println(statement);
        
        ResultSet rs = statement.executeQuery();
        //System.out.println(rs);
        while (rs.next()) {
           // System.out.println(rs.getString("address"));
            
            //System.out.println(rs.getString("firstName"));
//              searchData.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
//                rs.getString("username"),
//                rs.getInt("ssn")
//              ));  

                searchData.add(new Member(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getString("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    rs.getInt("ssn")
              ));   
            }
        return searchData;
    }
    
    public static ObservableList<Package> searchInAdminViewPackage(String str) throws SQLException {
        ObservableList<Package> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        String query = "SELECT * FROM Package WHERE packageName LIKE '%" + str + "%'";
               // + "OR adminLIKE '%" + str + "%'";
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.println(statement);
        
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getFloat("price"));
            searchData.add(new Package(
                    rs.getString("packageName"),
                    rs.getFloat("price"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getString("startTime"),
                    rs.getString("endTime")
            ));
        }
        return searchData;
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
    
    public static void updatePersonalInformation(String table, String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssnum, String uname, String pwd) throws SQLException {
        Connection conn = establishConnection();
        String query = "UPDATE ? SET"
                + " firstName = ?,"
                + " middleName = ?,"
                + " lastName = ?,"
                + " gender = ?,"
                + " dateOfBirth = ?,"
                + " address = ?,"
                + " phoneNumber = ?,"
                + " email = ?, "
                + " ssn = ?,"
                + " username = ?,"
                + " password =?"
                + "WHERE ssn = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, table);
        statement.setString(2, fn);
        statement.setString(3, mn);
        statement.setString(4, ln);
        statement.setString(5, gen);
        statement.setDate(6, dob);
        statement.setString(7, add);
        statement.setInt(8, pnum);
        statement.setString(9, ead);
        statement.setInt(10, ssnum);
        statement.setString(11, uname);
        statement.setString(12, pwd);
        statement.execute();
        conn.close();
    }
    
    public static void createPackage(Package pack, int admin_ssn) throws SQLException {
        Connection conn = establishConnection();
        String query = "INSERT INTO Package (packageName, price, startDate, endDate, startTime, "
                + "endTime, Admin_ssn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, pack.getPackageName());
        statement.setFloat(2, pack.getPrice());
        statement.setDate(3, pack.getStartDate());
        statement.setDate(4, pack.getEndDate());
        statement.setString(5, pack.getStartTime());
        statement.setString(6, pack.getEndTime());
        statement.setInt(7, admin_ssn);
        statement.execute();
        conn.close();
    }
    
    public static int checkPackageName(String pn) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT count(*) FROM Package WHERE packageName = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, pn);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        while(rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }
    
    public static ObservableList<Package> adminViewPackages() throws SQLException {
        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT packageName, price, startDate, startTime, endDate, endTime FROM Package");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                data.add(new Package(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime")
                ));     
            }   
            return data;
        }
        catch (Exception e) {

        }
        return null;
    }
    
    public static boolean deleteAccount(int ssn, String table) throws SQLException {
        Connection conn = establishConnection();
        String query = "DELETE FROM $table_name WHERE ssn = ?";
        query = query.replace("$table_name", table);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ssn);
        System.out.println(statement);
        statement.execute();
        boolean deletionError = false;
        conn.close();
        return deletionError;
    }
    
    public static boolean deletePackage(String pn) throws SQLException {
        Connection conn = establishConnection();
        String query = "DELETE FROM Package WHERE packageName = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, pn);
        statement.execute();
        boolean deletionError = false;
        conn.close();
        return deletionError;
    }
    
    public static void updatePackage(String pn, Float price, Date sd, Date ed, String st, String et) throws SQLException {
        Connection conn = establishConnection();
        String query = "UPDATE Package SET "
                + "packageName = ?, "
                + "price = ?, "
                + "startDate = ?, "
                + "endDate = ?, "
                + "startTime = ?, "
                + "endTime = ? "
                + "WHERE packageName = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, pn);
    }
}
