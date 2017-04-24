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
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn1, ssn2, phoneNumber, address, email, gender, dateOfBirth FROM Admin");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("ssn2"));
                data.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getDate("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))  
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
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn1, ssn2, phoneNumber, address, email, gender, dateOfBirth FROM Member");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                data.add(new Member(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("gender"),
                    rs.getDate("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))  
                ));  
            }   
            return data;
        }
        catch (Exception e) {

        }
        return null;
    }

        public static void createAdminAccount(Admin admin) {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Admin (firstName, middleName, lastName, gender, "
                    + "dateOfBirth, address, phoneNumber, email, ssn1, ssn2, username, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println(admin.getFirstName());
            System.out.println(admin.getMiddleName());
            System.out.println(admin.getLastName());
            System.out.println(admin.getGender());
            System.out.println(admin.getDOB());
            System.out.println(admin.getAddress());
            System.out.println(admin.getPhoneNumber());
            System.out.println(admin.getEmail());
            System.out.println(admin.getSSN1());
            System.out.println(admin.getUsername());
            System.out.println(admin.getPassword());
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getMiddleName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getGender());
            statement.setDate(5, admin.getDOB());
            statement.setString(6, admin.getAddress());
            statement.setInt(7, admin.getPhoneNumber());
            statement.setString(8, admin.getEmail());
            statement.setInt(9, admin.getSSN1());
            statement.setInt(10, admin.getSSN2());
            statement.setString(11, admin.getUsername());
            statement.setString(12, admin.getPassword());
            statement.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        
//    public static void createAdminAccount(String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssnum, String uname, String pwd) {
//        Connection conn = establishConnection();
//        try {
//            String query = "INSERT INTO Admin (firstName, middleName, lastName, gender, "
//                    + "dateOfBirth, address, phoneNumber, email, ssn, username, password) "
//                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement statement = conn.prepareStatement(query);
//            statement.setString(1, fn);
//            statement.setString(2, mn);
//            statement.setString(3, ln);
//            statement.setString(4, gen);
//            statement.setDate(5, dob);
//            statement.setString(6, add);
//            statement.setInt(7, pnum);
//            statement.setString(8, ead);
//            statement.setInt(9, ssnum);
//            statement.setString(10, uname);
//            statement.setString(11, pwd);
//            statement.execute();
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public static void createMemberAccount(Member member, int adminId) throws SQLException {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Member (firstName, middleName, lastName, gender, "
                    + "dateOfBirth, address, phoneNumber, email, ssn1, ssn2, username, password, Admin_adminId) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getMiddleName());
            statement.setString(3, member.getLastName());
            statement.setString(4, member.getGender());
            statement.setDate(5, member.getDOB());
            statement.setString(6, member.getAddress());
            statement.setInt(7, member.getPhoneNumber());
            statement.setString(8, member.getEmail());
            statement.setInt(9, member.getSSN1());
            statement.setInt(10, member.getSSN2());
            statement.setString(11, member.getUsername());
            statement.setString(12, member.getPassword());
            statement.setInt(13, adminId);
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

    public static void getAdminUsername(int ssn1, int ssn2) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT username FROM Admin WHERE ssn1 = " + ssn1 + "AND ssn2 = " + ssn2;
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
                    rs.getDate("dateOfBirth"),
                    //Helper.DateToString(rs.getDate("dateOfBirth")),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))  
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
    
    

    public static boolean checkUsernameAndSSN(String table, String uname, int ssn1, int ssn2) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT count(*) FROM " + table + " WHERE username = ? OR (ssn1 = ? AND ssn2 = ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, uname);
        statement.setInt(2, ssn1);
        statement.setInt(3, ssn2);
        ResultSet rs = statement.executeQuery();
        boolean alreadyExists = false;
        while (rs.next()) {
            alreadyExists = rs.getInt(1) != 0;
        }
        System.out.println("kjsadflkjdfsklj");
        return alreadyExists;
    }
    
    public static void updatePersonalInformation(String table, Admin admin, int ssnOld1, int ssnOld2) throws SQLException {
        Connection conn = establishConnection();
        String query = "UPDATE $table_name SET"
                + " firstName = ?,"
                + " middleName = ?,"
                + " lastName = ?,"
                + " gender = ?,"
                + " dateOfBirth = ?,"
                + " address = ?,"
                + " phoneNumber = ?,"
                + " email = ?, "
                + " ssn1 = ?"
                + " ssn2 = ?"
                + " WHERE ssn1 = ? AND ssn2 = ?";
        query = query.replace("$table_name", table);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, admin.getFirstName());
        statement.setString(2, admin.getMiddleName());
        statement.setString(3, admin.getLastName());
        statement.setString(4, admin.getGender());
        statement.setDate(5, admin.getDOB());
        statement.setString(6, admin.getAddress());
        statement.setInt(7, admin.getPhoneNumber());
        statement.setString(8, admin.getEmail());
        statement.setInt(9, admin.getSSN1());
        statement.setInt(10, admin.getSSN2());
        statement.setInt(11, ssnOld1);
        statement.setInt(12, ssnOld2);
        System.out.println(statement);
        statement.executeUpdate();
        conn.close();
    }
    
    public static void createPackage(Package pack, int adminId) throws SQLException {
        Connection conn = establishConnection();
        String query = "INSERT INTO Package (packageName, price, startDate, endDate, startTime, "
                + "endTime, Admin_adminId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, pack.getPackageName());
        statement.setFloat(2, pack.getPrice());
        statement.setDate(3, pack.getStartDate());
        statement.setDate(4, pack.getEndDate());
        statement.setString(5, pack.getStartTime());
        statement.setString(6, pack.getEndTime());
        statement.setInt(7, adminId);
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
    
    public static boolean deleteAccount(int ssn1, int ssn2, String table) throws SQLException {
        Connection conn = establishConnection();
        String query = "DELETE FROM $table_name WHERE ssn1 = ? AND ssn2 = ?";
        query = query.replace("$table_name", table);
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ssn1);
        statement.setInt(2, ssn2);
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
    
    public static void updatePackage(Package pack, String packageNameOld, int adminId) throws SQLException {
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
        statement.setString(1, pack.getPackageName());
        statement.setFloat(2, pack.getPrice());
        statement.setDate(3, pack.getStartDate());
        statement.setDate(4, pack.getEndDate());
        statement.setString(5, pack.getStartTime());
        statement.setString(6, pack.getEndTime());
        statement.setString(7, packageNameOld);
        statement.executeUpdate();
        conn.close();
    }
    
        public static ObservableList<Package> getPackageInfoAdmin(String pn) throws SQLException {
        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT packageName, price, startDate, startTime, endDate, endTime FROM Package WHERE packageName = ?");
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, pn);
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
        
    public static ObservableList<Admin> getAdminPersonalInformation(int ssnum1, int ssnum2) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT firstName, middleName, lastName,"
                + " dateOfBirth, address, phoneNumber, email, gender, ssn"
                + " FROM Admin WHERE ssn1 = ? AND ssn2 = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ssnum1);
        statement.setInt(2, ssnum2);
        ResultSet rs = statement.executeQuery();
        
        ObservableList<Admin> data = FXCollections.observableArrayList();
        while(rs.next()) {
           try {
                data.add(new Admin(rs.getString("firstName"),
                    rs.getString("middleName"),
                    rs.getString("lastName"),
                    rs.getDate("dateOfBirth"),
                    rs.getString("address"),
                    rs.getInt("phoneNumber"),
                    rs.getString("email"),
                    rs.getString("gender"),
                    rs.getInt("ssn1"),
                        rs.getInt("ssn2")
                ));  
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
        System.out.println(data);
        return data;
    }
}
