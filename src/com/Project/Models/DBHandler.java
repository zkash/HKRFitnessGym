package com.Project.Models;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Database handler class
 *
 * @author shameer
 */
public class DBHandler {
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final String connectionURL;

    /**
     * Initializer constructor
     */
    public DBHandler() {
        Properties properties = loadProperties();
        databaseName = properties.getProperty("databaseName");
        databaseUsername = properties.getProperty("databaseUsername");
        databasePassword = properties.getProperty("databasePassword");
        connectionURL = "jdbc:mysql://localhost/" + databaseName + "?user=" + databaseUsername + "&password=" + databasePassword + "&useSSL=false";
    }

      
    /**
     * Establishes connection with database
     * @return Connection object
     */
    public Connection establishConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(connectionURL);
            if (conn != null) {
                System.out.println("connected to database successfully");
                return conn;
            }
        } 
        catch (SQLException ex) {
            System.out.println("Not connected to database");
        }
        return null;
    }
    
    
    /**
     * Loads properties
     * @return Properties object
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/com/Project/Properties/hkrFitnessGym.properties")) {
            properties.load(fis);
        }
        catch (Exception e) {
        }
        return properties;
    }
    
    
    /**
     * Views administrator accounts while administrator is logged in 
     * @return ObservableList of Admin objects
     * @throws SQLException 
     */
    public ObservableList<Admin> adminViewAdminAccounts() throws SQLException {
        ObservableList<Admin> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        try {
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn1, ssn2, phoneNumber, address, email, gender, dateOfBirth FROM Admin");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                data.add(new Admin(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            }
            return data;
        } 
        catch (SQLException ex) {
        }
        return null;
    }

    
    /**
     * Views member accounts while administrator is logged in 
     * @return ObservableList of Member objects
     * @throws SQLException 
     */
    public ObservableList<Member> adminViewMemberAccounts() throws SQLException {
        ObservableList<Member> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        try {
            String query = String.format("SELECT firstName, middleName, lastName, username, ssn1, ssn2, phoneNumber, address, email, gender, dateOfBirth FROM Member");
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                data.add(new Member(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            }
            
            for(Member member : data) {
                String query2 = "SELECT a.firstName, a.middleName, a.lastName"
                        + " From Admin as a"
                        + " INNER JOIN Member as m"
                        + " ON a.adminId = m.Admin_adminId";
                PreparedStatement statement2 = conn.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                
                while(rs2.next()) {
                    if (rs2.getString(2).equals("")) {       //middle name empty or not
                        member.setAdminFullName(rs2.getString(1) + " " + rs2.getString(3)); //firstName + lastName
                    }
                    else {
                        member.setAdminFullName(rs2.getString(1) + " " + rs2.getString(2) + " " + rs2.getString(3));
                    }  
                }
            }
            return data;
        }
        catch (SQLException ex) {
        }
        return null;  
    }

    
    /**
     * Creates an administrator account
     * @param admin Admin object
     */
    public void createAdminAccount(Admin admin) {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Admin (firstName, middleName, lastName, "
                    + "gender, dateOfBirth, address, phoneNumber, email, ssn1, "
                    + "ssn2, username, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getMiddleName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getGender());
            statement.setDate(5, admin.getDateOfBirth());
            statement.setString(6, admin.getAddress());
            statement.setInt(7, admin.getPhoneNumber());
            statement.setString(8, admin.getEmail());
            statement.setInt(9, admin.getSSN1());
            statement.setInt(10, admin.getSSN2());
            statement.setString(11, admin.getUsername());
            statement.setString(12, admin.getPassword());
            statement.execute();
            conn.close();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /**
     * Creates member accounts
     * @param member Member object
     * @param adminId Id of administrator creating the member account
     * @throws SQLException 
     */
    public void createMemberAccount(Member member, int adminId) throws SQLException {
        Connection conn = establishConnection();
        try {
            String query = "INSERT INTO Member (firstName, middleName, lastName, "
                    + "gender, dateOfBirth, address, phoneNumber, email, ssn1, "
                    + "ssn2, username, password, Admin_adminId) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getMiddleName());
            statement.setString(3, member.getLastName());
            statement.setString(4, member.getGender());
            statement.setDate(5, member.getDateOfBirth());
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
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Searches for administrator accounts as per user query and filter
     * @param firstName First name of administrator
     * @param middleName Middle name of administrator
     * @param lastName Last name of administrator
     * @param address Address of administrator
     * @param username Username of administrator
     * @param email Email of administrator
     * @param phoneNumber Phone number of administrator
     * @param ssn1 First part (before -) of social security number of administrator
     * @param ssn2 Second part (after -) of social security number of administrator
     * @param table Name of database table
     * @return ObservableList of Admin objects
     * @throws SQLException 
     */
    public ObservableList<Admin> searchInAdminViewAdminAccounts(String firstName, 
            String middleName, String lastName, String address, String username, 
            String email, int phoneNumber, int ssn1, int ssn2, String table) throws SQLException {
        ObservableList<Admin> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "SELECT * FROM $tableName WHERE firstName LIKE \"%" + firstName + "%\""
                + " OR middleName LIKE \"%" + middleName + "%\""
                + " OR lastName LIKE \"%" + lastName + "%\""
                + " OR address LIKE \"%" + address + "%\""
                + " OR username LIKE \"%" + username + "%\""
                + " OR email = \"" + email + "\""
                + " OR phoneNumber = " + phoneNumber
                + " OR (ssn1 = " + ssn1
                + " AND ssn2 = " + ssn2 + ")";
        
        query = query.replace("$tableName", table);
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            if (rs.getString("middleName").equals("")) {
                searchData.add(new Admin(
                        rs.getString("firstName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            } 
            else {
                searchData.add(new Admin(
                        rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            }
        }
        return searchData;
    }

    
    /**
     * Searches for member accounts as per user query and filter
     * @param firstName First name of member
     * @param middleName Middle name of member
     * @param lastName Last name of member
     * @param address Address of member
     * @param username Username of member
     * @param email Email of member
     * @param phoneNumber Phone number of member
     * @param ssn1 First part (before -) of social security number of member
     * @param ssn2 Second part (after -) of social security number of member
     * @param table Name of database table
     * @return ObservableList of Member objects
     * @throws SQLException 
     */
    public ObservableList<Member> searchInAdminViewMemberAccounts(String firstName,
            String middleName, String lastName, String address, String username, 
            String email, int phoneNumber, int ssn1, int ssn2, String table) throws SQLException {
        ObservableList<Member> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "SELECT * FROM $tableName WHERE firstName LIKE \"%" + firstName + "%\""
                + " OR middleName LIKE \"%" + middleName + "%\""
                + " OR lastName LIKE \"%" + lastName + "%\""
                + " OR address LIKE \"%" + address + "%\""
                + " OR username LIKE \"%" + username + "%\""
                + " OR email = \"" + email + "\""
                + " OR phoneNumber = " + phoneNumber
                + " OR (ssn1 = " + ssn1
                + " AND ssn2 = " + ssn2 + ")";
        
        query = query.replace("$tableName", table);
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            if (rs.getString("middleName").equals("")) {
                searchData.add(new Member(
                        rs.getString("firstName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            } 
            else {
                searchData.add(new Member(
                        rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("address"),
                        rs.getInt("phoneNumber"),
                        rs.getString("email"),
                        Integer.toString(rs.getInt("ssn1")) + "-" + Integer.toString(rs.getInt("ssn2"))
                ));
            }
        }
        
        for(Member member : searchData) {
            String query2 = "SELECT a.firstName, a.middleName, a.lastName"
                    + " From Admin as a"
                    + " INNER JOIN Member as m"
                    + " ON a.adminId = m.Admin_adminId";
            
            PreparedStatement statement2 = conn.prepareStatement(query2);
            ResultSet rs2 = statement2.executeQuery();
            
            while(rs2.next()) {
                if (rs2.getString(2).equals("")) {       //middle name empty or not
                    member.setAdminFullName(rs2.getString(1) + " " + rs2.getString(3)); //firstName + lastName
                }
                else {
                    member.setAdminFullName(rs2.getString(1) + " " + rs2.getString(2) + " " + rs2.getString(3));
                }
            }
        }
        return searchData;
    }

    
    /**
     * Searches for packages as per query and filter while logged in as administrator
     * @param searchQuery Value to search 
     * @return ObservableList of Package objects
     * @throws SQLException 
     */
    public ObservableList<Package> searchInAdminViewPackage(String searchQuery) throws SQLException {
        ObservableList<Package> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "SELECT Package.*, Admin.firstName, Admin.middleName, "
                + "Admin.lastName FROM Package, Admin "
                + "WHERE Admin.adminId = Package.Admin_adminId "
                + "AND packageName LIKE '%" + searchQuery +  "%'";

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            if (rs.getString("middleName").equals("")) {
                searchData.add(new Package(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("firstName") + " " + rs.getString("lastName")
                ));
            }
            else {
                searchData.add(new Package(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName")
                ));
            }
        }
        
        for (Package pack : searchData) {
                String name = pack.getPackageName();
                String query2 = "SELECT COUNT(pk.packageName)"
                        + " From Package as pk"
                        + " INNER JOIN Subscription as sub"
                        + " ON pk.packageId = sub.Package_packageId"
                        + " WHERE packageName = \"" + name + "\""
                        + " AND subscriptionStatus = 'Active'";
                
                PreparedStatement statement2 = conn.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                String count = "";
                
                while (rs2.next()) {
                    count = rs2.getString(1);
                }
                
                pack.setNumberOfSubscriber(Integer.parseInt(count));       
        }
        return searchData;
    }
    
    
    /**
     * Searches for packages as per query and filter while logged in as member
     * @param searchQuery Value to search 
     * @return ObservableList of Package objects
     * @throws SQLException 
     */
    public ObservableList<Package> searchInMemberViewPackage(String searchQuery) throws SQLException {
        ObservableList<Package> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "SELECT Package.*, Admin.firstName, Admin.middleName, "
                + "Admin.lastName FROM Package, Admin "
                + "WHERE Admin.adminId = Package.Admin_adminId AND "
                + "packageName LIKE '%" + searchQuery +  "%'";

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
            if (rs.getString("middleName").equals("")) {
                searchData.add(new Package(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("firstName") + " " + rs.getString("lastName")
                ));
            }
            else {
                searchData.add(new Package(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName")
                ));
            }
        }
        
        for (Package pack : searchData) {
                String name = pack.getPackageName();
                String query2 = "SELECT COUNT(pk.packageName)"
                        + " From Package as pk"
                        + " INNER JOIN Subscription as sub"
                        + " ON pk.packageId = sub.Package_packageId"
                        + " WHERE packageName = \"" + name + "\""
                        + " AND subscriptionStatus = 'Active'";
                
                PreparedStatement statement2 = conn.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                String count = "";
                
                while (rs2.next()) {
                    count = rs2.getString(1);
                }
                
                pack.setNumberOfSubscriber(Integer.parseInt(count));       
        }
        return searchData;
    }
            
            
    /**
     * Verifies username and social security number of user 
     * @param table Name of table in database
     * @param username Username of user
     * @param ssn1 First part (before -) of social security number of member
     * @param ssn2 Second part (after -) of social security number of member
     * @return True if an account exists based on username and social security number; false if account does not exist
     * @throws SQLException 
     */
    public boolean checkUsernameAndSSN(String table, String username, int ssn1, int ssn2) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT count(*) FROM " + table + " WHERE username = ? "
                + "OR (ssn1 = ? AND ssn2 = ?)";
        
        PreparedStatement statement = conn.prepareStatement(query);
        
        statement.setString(1, username);
        statement.setInt(2, ssn1);
        statement.setInt(3, ssn2);
        
        ResultSet rs = statement.executeQuery();
        
        boolean alreadyExists = false;
        
        while (rs.next()) {
            alreadyExists = rs.getInt(1) != 0;
        }
        
        return alreadyExists;
    }

    
    /**
     * Updates administrator's personal information
     * @param table Name of table in database
     * @param admin Admin object
     * @param adminId Id of administrator
     * @param ssnOld1 First part (before -) of old social security number of member
     * @param ssnOld2 Second part (after -) of old social security number of member
     * @throws SQLException 
     */
    public void updateAdminPersonalInformation(String table, Admin admin, 
            int adminId, int ssnOld1, int ssnOld2) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "UPDATE $table_name SET"
                    + " firstName = ?,"
                    + " middleName = ?,"
                    + " lastName = ?,"
                    + " gender = ?,"
                    + " dateOfBirth = ?,"
                    + " address = ?,"
                    + " phoneNumber = ?,"
                    + " email = ?, "
                    + " ssn1 = ?,"
                    + " ssn2 = ?"
                    + " WHERE adminId = ? AND ssn1 = ? AND ssn2 = ?";
            
            query = query.replace("$table_name", table);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getMiddleName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getGender());
            statement.setDate(5, admin.getDateOfBirth());
            statement.setString(6, admin.getAddress());
            statement.setInt(7, admin.getPhoneNumber());
            statement.setString(8, admin.getEmail());
            statement.setInt(9, admin.getSSN1());
            statement.setInt(10, admin.getSSN2());
            statement.setInt(11, adminId);
            statement.setInt(12, ssnOld1);
            statement.setInt(13, ssnOld2);
            
            statement.executeUpdate();
        }
    }

    
    /**
     * Updates member's personal information
     * @param table Name of table in database
     * @param member Member object
     * @param memberId Id of member
     * @param ssnOld1 First part (before -) of old social security number of member
     * @param ssnOld2 Second part (after -) of old social security number of member
     * @throws SQLException 
     */
    public void updateMemberPersonalInformation(String table, Member member, 
            int memberId, int ssnOld1, int ssnOld2) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "UPDATE $table_name SET"
                    + " firstName = ?,"
                    + " middleName = ?,"
                    + " lastName = ?,"
                    + " gender = ?,"
                    + " dateOfBirth = ?,"
                    + " address = ?,"
                    + " phoneNumber = ?,"
                    + " email = ?, "
                    + " ssn1 = ?,"
                    + " ssn2 = ?"
                    + " WHERE memberId = ? AND ssn1 = ? AND ssn2 = ?";
            query = query.replace("$table_name", table);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getMiddleName());
            statement.setString(3, member.getLastName());
            statement.setString(4, member.getGender());
            statement.setDate(5, member.getDateOfBirth());
            statement.setString(6, member.getAddress());
            statement.setInt(7, member.getPhoneNumber());
            statement.setString(8, member.getEmail());
            statement.setInt(9, member.getSSN1());
            statement.setInt(10, member.getSSN2());
            statement.setInt(11, memberId);
            statement.setInt(12, ssnOld1);
            statement.setInt(13, ssnOld2);
            statement.executeUpdate();
        }
    }
    
    
    /**
     * Creates a package
     * @param pack Package object
     * @param adminId Id of administrator creating package
     * @throws SQLException 
     */
    public void createPackage(Package pack, int adminId) throws SQLException {
        try (Connection conn = establishConnection()) {
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
        }
    }

    
    /**
     * Verifies package name
     * @param packageName Name of package
     * @return Number of package if such package exist; 0 if it does not exist
     * @throws SQLException 
     */
    public int checkPackageName(String packageName) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT count(*) FROM Package WHERE packageName = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, packageName);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        
        while (rs.next()) {
            count = rs.getInt(1);
        }
        
        return count;
    }
    
    
    /**
     * Views packages while logged in as administrator
     * @return ObservableList of Package object
     * @throws SQLException 
     */
    public ObservableList<Package> adminViewPackages() throws SQLException {
        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT Package.*, Admin.firstName, "
                    + "Admin.middleName, Admin.lastName FROM Package, Admin "
                    + "WHERE Admin.adminId = Package.Admin_adminId");

            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if (rs.getString("middleName").equals("")) {
                    data.add(new Package(
                            rs.getString("packageName"),
                            rs.getFloat("price"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            rs.getString("firstName") + " " + rs.getString("lastName")
                    ));
                } 
                else {
                    data.add(new Package(
                            rs.getString("packageName"),
                            rs.getFloat("price"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName")
                    ));
                }
            }
            
            for (Package pack : data) {
                String name = pack.getPackageName();
                String query2 = "SELECT COUNT(pk.packageName)"
                        + " From Package as pk"
                        + " INNER JOIN Subscription as sub"
                        + " ON pk.packageId = sub.Package_packageId"
                        + " WHERE packageName = \"" + name + "\""
                        + " AND subscriptionStatus = 'Active'";
                
                PreparedStatement statement2 = conn.prepareStatement(query2);
                ResultSet rs2 = statement2.executeQuery();
                String count = "";
                
                while (rs2.next()) {
                    count = rs2.getString(1);
                }

                pack.setNumberOfSubscriber(Integer.parseInt(count));
            }
            return data;
        } 
        catch (NumberFormatException | SQLException ex) {
        }
        return null;
    }

    
    /**
     * Views packages while logged in as member
     * @return ObservableList of Package object
     * @throws SQLException 
     */
    public ObservableList<Package> memberViewPackages() throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT packageName, price, startDate, endDate, startTime, "
                + "endTime FROM Package";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Package> pack = FXCollections.observableArrayList();
        
        while(rs.next()) {
            pack.add(new Package(
                    rs.getString("packageName"),
                    rs.getFloat("price"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getString("startTime"),
                    rs.getString("endTime")
            ));
        }
        return pack;
    }
    
    
    /**
     * Deletes user account
     * @param ssn1 First part (before -) of social security number of user
     * @param ssn2 Second part (after -) of social security number of user
     * @param username Username of user
     * @param table Name of table in database
     * @throws SQLException 
     */
    public void deleteAccount(int ssn1, int ssn2, String username, String table) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "DELETE FROM $table_name WHERE ssn1 = ? AND ssn2 = ? AND username = ?";
            query = query.replace("$table_name", table);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setInt(1, ssn1);
            statement.setInt(2, ssn2);
            statement.setString(3, username);
            
            statement.execute();
        }
    }

    
    /**
     * Deletes a package
     * @param packageName Name of package
     * @throws SQLException 
     */
    public void deletePackage(String packageName) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "DELETE FROM Package WHERE packageName = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, packageName);
            statement.execute(); 
        }
    }

    
    /**
     * Updates package information
     * @param pack Package object
     * @param packageNameOld Old package name
     * @param adminId Id of administrator updating package information
     * @throws SQLException 
     */
    public void updatePackage(Package pack, String packageNameOld, int adminId) throws SQLException {
        try (Connection conn = establishConnection()) {
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
        }
    }

    
    /**
     * Gets package information while logged in as administrator
     * @param packageName Name of package
     * @return ObservableList of Package object
     * @throws SQLException 
     */
    public ObservableList<Package> getPackageInfoAdmin(String packageName) throws SQLException {
        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        try {
            String query = String.format("SELECT packageName, price, startDate, startTime, "
                    + "endDate, endTime FROM Package WHERE packageName = ?");
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, packageName);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
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
        catch (SQLException ex) {
        }
        return null;
    }

    
    /**
     * Gets administrator's personal information
     * @param adminId Id of administrator
     * @return ObservableList of Admin object
     * @throws SQLException 
     */
    public ObservableList<Admin> getAdminPersonalInformation(int adminId) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT firstName, middleName, lastName,"
                + " dateOfBirth, address, phoneNumber, email, gender, ssn1, ssn2"
                + " FROM Admin WHERE adminId = ?";
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, adminId);
        ResultSet rs = statement.executeQuery();
        ObservableList<Admin> admin = FXCollections.observableArrayList();
        
        while (rs.next()) {
            admin.add(new Admin(rs.getString("firstName"),
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
        return admin;
    }

    
    /**
     * Gets member's personal information
     * @param memberId Id of member
     * @return ObservableList of Member object
     * @throws SQLException 
     */
    public ObservableList<Member> getMemberPersonalInformation(int memberId) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT firstName, middleName, lastName,"
                + " dateOfBirth, address, phoneNumber, email, gender, ssn1, ssn2"
                + " FROM Member WHERE memberId = ?";
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, memberId);
        ResultSet rs = statement.executeQuery();
        ObservableList<Member> member = FXCollections.observableArrayList();
        
        while (rs.next()) {
            member.add(new Member(rs.getString("firstName"),
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
        return member;
    }
    
    
    /**
     * Gets user's personal information
     * @param accountType Type of user account - Admin or Member
     * @param id Id of user
     * @return ObservableList of Person object
     * @throws SQLException 
     */
     public ObservableList<Person> getPersonalInformation(String accountType, int id) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT firstName, middleName, lastName,"
                + " dateOfBirth, address, phoneNumber, email, gender, ssn1, ssn2"
                + " FROM Admin WHERE adminId = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT firstName, middleName, lastName,"
                + " dateOfBirth, address, phoneNumber, email, gender, ssn1, ssn2"
                + " FROM Member WHERE memberId = ?";
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        ObservableList<Person> person = FXCollections.observableArrayList();
        
        if(accountType.equals("Admin")) {
            while (rs.next()) {
                person.add(new Admin(rs.getString("firstName"),
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
        }
        else if(accountType.equals("Member")) {
            while (rs.next()) {
                person.add(new Member(rs.getString("firstName"),
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
        }
        return person;
    }
    
     
     /**
      * Views schedule while logged in as member
      * @return ObservableList of Schedule objects
      * @throws SQLException 
      */
    public ObservableList<Schedule> memberViewSchedule() throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT date, openingTime, closingTime, isHoliday FROM Schedule";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Schedule> schedule = FXCollections.observableArrayList();
        
        while (rs.next()) {
            schedule.add(new Schedule(rs.getDate("date"),
                    rs.getString("openingTime"),
                    rs.getString("closingTime"),
                    rs.getBoolean("isHoliday")
            ));
        }
        return schedule;
    }
    
    
    /**
     * Gets Id of package from its name
     * @param packageName Name of package
     * @return Id of package
     * @throws SQLException 
     */
    public int getPackageIdFromPackageName(String packageName) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT packageId FROM Package WHERE packageName = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, packageName);
        ResultSet rs = statement.executeQuery();
        int packageId = 0;
        
        while (rs.next()) {
            packageId = rs.getInt("packageId");
        }
        return packageId;
    }
    
    
    /**
     * Subscribes to a package
     * @param subscription Subscription object
     * @throws SQLException 
     */
    public void subscribeToPackage(Subscription subscription) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "INSERT INTO Subscription (startDate, endDate, "
                    + "Package_packageId, Member_memberId, subscriptionStatus)"
                    + " VALUES (?, ?, ?, ?, 'Requested')";
            
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDate(1, subscription.getSubscriptionStartDate());
            statement.setDate(2, subscription.getSubscriptionEndDate());
            statement.setInt(3, subscription.getPackageId());
            statement.setInt(4, subscription.getMemberId());
            
            statement.execute();
        }
    }
    
    
    /**
     * Views subscription while logged in as member
     * @param memberId Id of member
     * @param filter Table view filter string
     * @return ObservableList of Subscription object
     * @throws SQLException 
     */
    public ObservableList<Subscription> memberViewSubscription(int memberId, String filter) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT packageName, price, pk.startDate, pk.endDate, "
                + "startTime, endTime, sub.startDate, sub.endDate, subscriptionStatus, subscriptionId, offerPrice, declineMessage FROM Subscription AS sub "
                + "INNER JOIN package AS pk "
                + "ON pk.packageId = sub.Package_packageId "
                + " WHERE Member_memberId = ?";

        switch (filter) {
            case "Active":
                query = query + "  AND subscriptionStatus = 'Active'";
                break;
            case "Expired":
                query = query + "  AND subscriptionStatus = 'Expired'";
                break;
            case "Canceled":
                query = query + "  AND subscriptionStatus = 'Canceled'";
                break;
            case "Requested":
                query = query + " AND subscriptionStatus = 'Requested'";
                break;
            case "Declined":
                query = query + "  AND subscriptionStatus = 'Declined'";
                break;
            default:
                break;
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, memberId);
        ResultSet rs = statement.executeQuery();
        ObservableList<Subscription> subscription = FXCollections.observableArrayList();
        Subscription sub;
        
        while (rs.next()) {
            sub = new Subscription( 
                    rs.getString("packageName"),
                    rs.getFloat("price"),
                    rs.getString("startTime"),
                    rs.getString("startTime")
            );

            sub.setStartDate(rs.getDate("pk.startDate"));
            sub.setEndDate(rs.getDate("pk.endDate"));
            sub.setSubscriptionStartDate(rs.getDate("sub.startDate"));
            sub.setSubscriptionEndDate(rs.getDate("sub.endDate"));
            sub.setSubscriptionId(rs.getInt("subscriptionId"));
            sub.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            sub.setOfferPrice(rs.getFloat("offerPrice"));
            sub.setDeclineMessage(rs.getString("declineMessage"));
            
            subscription.add(sub);
        }
        return subscription;
    }
    
    
    /**
     * Cancels a subscription
     * @param subscriptionId Id of subscription
     * @throws SQLException 
     */
    public void cancelSubscription(int subscriptionId) throws SQLException {
        Connection conn = establishConnection();
        String query = "UPDATE Subscription SET subscriptionStatus = 'Canceled' WHERE subscriptionId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, subscriptionId);
        statement.executeUpdate();
    }
    
    
    /**
     * Verifies username for existence
     * @param username Username of user 
     * @param accountType Type of account of user - Admin or Member
     * @return True if account exists; false if account does not exist
     * @throws SQLException 
     */
    public Boolean verifyUsername(String username, String accountType) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT count(*) FROM Admin WHERE BINARY username = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT count(*) FROM Member WHERE BINARY username = ?";
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        
        while(rs.next()) {
            count = rs.getInt(1);
        }
        
        return count != 0;     
    }
    
    
    /**
     * Get Id of user account
     * @param username Username of user
     * @param password Password of user
     * @param accountType Type of account of user - Admin or Member
     * @return Id of user
     */
    public int getId(String username, String password, String accountType) {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT adminId FROM Admin WHERE BINARY username = ? AND BINARY password = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT memberId FROM Member WHERE BINARY username = ? AND BINARY password = ?";
        }
        
        int id = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch(SQLException ex) {
        }
        return id;
    }
    
    
    /**
     * Verifies email existence
     * @param table Name of table in database
     * @param email Email of user
     * @return True if the email exists; false if it does not exist
     * @throws SQLException 
     */
    public Boolean checkEmailExistence(String table, String email) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(table.equals("Admin")) {
            query = "SELECT COUNT(*) FROM Admin WHERE email = ? ";
        }
        else if(table.equals("Member")) {
            query = "SELECT COUNT(*) FROM Member WHERE email = ? ";
        }
        
        Boolean emailExists = false;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                emailExists = rs.getInt(1) > 0;
            }
        }
        catch(SQLException ex) {
        }
        return emailExists;
    }
    
    
    /**
     * Verifies username and password
     * @param username Username of user
     * @param password Password of user
     * @param accountType Type of account of user - Admin or Member
     * @return True if user account exists; false if it does not exist
     */
    public boolean verifyUsernamePassword(String username, String password, String accountType) {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT COUNT(*)FROM Admin WHERE BINARY username = ? AND BINARY password = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT COUNT(*) FROM Member WHERE BINARY username = ? AND BINARY password = ?";
        }
        
        boolean accountExists = false;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                accountExists = rs.getInt(1) == 1; 
            }
        }
        catch (SQLException ex) {
        }
        return accountExists;
    }
    
    
    /**
     * Stores forgot password request and gets its key
     * @param table Name of table in database
     * @param forgotPasswordRequest ForgotPasswordRequest object
     * @return Key of the newly-created forgot password request
     * @throws SQLException 
     */
    public int storeForgotPasswordRequestAndGetItsKey(String table, ForgotPasswordRequest forgotPasswordRequest) throws SQLException {
        int autoGeneratedKey = 0;
        try (Connection conn = establishConnection()) {
            String query = "";
            
            if(table.equals("Admin")) {
                query = "INSERT INTO AdminForgotPassword (date, time, code, Admin_adminId)"
                        + " VALUES (?, ?, ?, ?)";
            }
            else if (table.equals("Member")) {
                query = "INSERT INTO MemberForgotPassword (date, time, code, Member_MemberId)"
                        + " VALUES (?, ?, ?, ?)";
            }
            
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, forgotPasswordRequest.getDate());
            statement.setString(2, forgotPasswordRequest.getTime());
            statement.setString(3, forgotPasswordRequest.getCode());
            statement.setInt(4, forgotPasswordRequest.getId());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            
            while(rs.next()) {
                autoGeneratedKey = rs.getInt(1);
            }
        }
        return autoGeneratedKey;
    }
    
    
    /**
     * Verifies forgot password random code
     * @param accountType Type of user account - Admin or Member
     * @param code User-entered code
     * @param autoGeneratedId Id of the randomly generated code in the database
     * @param forgotPasswordRequest ForgotPasswordReqeust object
     * @return True if the user-entered code matches with randomly generated code in database; false if it does not match
     * @throws SQLException 
     */
    public boolean verifyCode(String accountType, String code, int autoGeneratedId, 
            ForgotPasswordRequest forgotPasswordRequest) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT COUNT(*) FROM AdminForgotPassword WHERE "
                    + "BINARY code = ? "
                    + "AND adminForgotPasswordId = ? "
                    + "AND date = ? "
                    + "AND time = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT COUNT(*) FROM MemberForgotPassword WHERE "
                    + "BINARY code = ? "
                    + "AND memberForgotPasswordId = ? "
                    + "AND date = ? "
                    + "AND time = ?";
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, code);
        statement.setInt(2, autoGeneratedId);
        statement.setDate(3, forgotPasswordRequest.getDate());
        statement.setString(4, forgotPasswordRequest.getTime());
        ResultSet rs = statement.executeQuery();
        boolean codeVerification = false;
        
        while(rs.next()) {
            codeVerification = rs.getInt(1) == 1;
        }
        return codeVerification;
    }
    
    
    /**
     * Updates password
     * @param accountType Types of user account - Admin or Member
     * @param id Id of user
     * @param password Password of user
     * @throws SQLException 
     */
    public void updatePassword(String accountType, int id, String password) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "UPDATE Admin SET password = ? WHERE adminId = ?";
        }
        else if(accountType.equals("Member")) {
            query = "UPDATE Member SET password = ? WHERE memberId = ?";
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, password);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
    
    
    /**
     * Gets user Id for email verification 
     * @param accountType Type of user account - Admin or Member
     * @param username Username of user
     * @param email Email of user
     * @return Id of user
     */
    public int getIdForVerification(String accountType, String username, String email) {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT adminId FROM Admin WHERE username = ? AND email = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT memberId FROM Member WHERE username = ? AND email = ?";
        }
        
        int id = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        }
        catch(SQLException ex) {   
        }
        return id;
    }
    
    
    /**
     * Gets password
     * @param id If of user
     * @param accountType Type of user account - Admin or Member
     * @return Old password
     * @throws SQLException 
     */
    public String getOldPassword(int id, String accountType) throws SQLException {
        String oldPassword;
        
        try (Connection conn = establishConnection()) {
            String query = "";
            
            if(accountType.equals("Admin")) {
                query = "SELECT password FROM Admin WHERE adminId = ?";
            }
            else if(accountType.equals("Member")) {
                query = "SELECT password FROM Member WHERE memberId = ?";
            }
            
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            oldPassword = "";
            
            while(rs.next()) {
                oldPassword = rs.getString(1);
            }
        }
        return oldPassword;
    }
    

    /**
     * Gets subscription requests
     * @return ObservableList of SubscriptionRequest object
     * @throws SQLException 
     */
    public ObservableList<SubscriptionRequest>  getSubscriptionRequest() throws SQLException {
        ObservableList<SubscriptionRequest> subscriptionRequestList;
        try (Connection conn = establishConnection()) {
            String query = "SELECT m.firstName, m.middleName, m.lastName,"
                    + " m.username, packageName, price, pk.startDate, pk.endDate,"
                    + " startTime, endTime, sub.startDate, sub.endDate, subscriptionStatus,"
                    + " subscriptionId FROM Subscription AS sub"
                    + " INNER JOIN package AS pk"
                    + " ON pk.packageId = sub.Package_packageId"
                    + " INNER JOIN member AS m"
                    + " ON sub.Member_memberId = m.memberId"
                    + " WHERE subscriptionStatus = 'Requested'";
            
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            subscriptionRequestList = FXCollections.observableArrayList();
            
            while(rs.next()) {
                SubscriptionRequest subscriptionRequest;
                subscriptionRequest = new SubscriptionRequest(
                        rs.getString("packageName"),
                        rs.getFloat("price"),
                        rs.getDate("pk.startDate"),
                        rs.getDate("pk.endDate"),
                        rs.getString("startTime"),
                        rs.getString("startTime"),
                        rs.getDate("sub.startDate"),
                        rs.getDate("sub.endDate"),
                        rs.getInt("subscriptionId")
                );
                
                if(rs.getString("m.middleName").equals("")) {
                    subscriptionRequest.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
                }
                else {
                    subscriptionRequest.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("lastName"));
                }
                
                subscriptionRequest.setMemberUsername(rs.getString("m.username"));
                subscriptionRequestList.add(subscriptionRequest);
            }
        }
        return subscriptionRequestList;
    }
    
    
    /**
     * Accepts subscription request
     * @param subscriptionId Id of subscription
     * @param offerPrice Price offered to the member
     * @param adminId If of administrator accepting the request
     * @throws SQLException 
     */
    public void acceptSubscriptionRequest(int subscriptionId, float offerPrice, int adminId) throws SQLException {
        try (Connection conn = establishConnection()) {
            String status = "Active";
            String query = "UPDATE Subscription SET subscriptionStatus =  ?, offerPrice = ?, Admin_adminId = ? WHERE subscriptionId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, status);
            statement.setFloat(2, offerPrice);
            statement.setInt(3, adminId);
            statement.setInt(4, subscriptionId);
            statement.executeUpdate();
        }
    }
    
    
    /**
     * Declines subscription request
     * @param subscriptionId Id of subscription
     * @param declineMessage Decline message sent to the member
     * @param adminId If of administrator declining the request
     * @throws SQLException 
     */
    public void declineSubscriptionRequest(int subscriptionId, String declineMessage, int adminId) throws SQLException {
        try (Connection conn = establishConnection()) {
            String status = "Declined";
            String query = "UPDATE Subscription SET subscriptionStatus =  ?, declineMessage = ?, Admin_adminId = ? WHERE subscriptionId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, status);
            statement.setString(2, declineMessage);
            statement.setInt(3, adminId);
            statement.setInt(4, subscriptionId);
            statement.executeUpdate();
        }
    }
    
    
    /**
     * Views subscription while logged in as administrator
     * @param filter Table view filter string
     * @return ObservableList of Subscription object
     * @throws SQLException 
     */
    public ObservableList<Subscription> adminViewSubscription(String filter) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT m.firstName, m.middleName, m.lastName,"
                    + " m.username, packageName, sub.startDate, sub.endDate, subscriptionStatus,"
                    + " subscriptionId, sub.Admin_adminId, sub.offerPrice, sub.declineMessage, a.firstName,"
                    + " a.middleName, a.lastName FROM Subscription AS sub "
                    + " INNER JOIN package AS pk"
                    + " ON pk.packageId = sub.Package_packageId"
                    + " INNER JOIN member AS m"
                    + " ON sub.Member_memberId = m.memberId"
                    + " INNER JOIN admin AS a"
                    + " ON sub.Admin_adminId = a.adminId";
        
        switch (filter) {
            case "All":
                query = query + "  WHERE NOT subscriptionStatus = 'Requested' AND NOT subscriptionStatus = 'Declined'";
                break;
            case "Active":
                query = query + "  WHERE subscriptionStatus = 'Active'";
                break;
            case "Expired":
                query = query + "  WHERE subscriptionStatus = 'Expired'";
                break;
            case "Canceled":
                query = query + "  WHERE subscriptionStatus = 'Canceled'";
                break;
            default:
                break;
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Subscription> subscription = FXCollections.observableArrayList();
        Subscription sub;
        
        while (rs.next()) {
            sub = new Subscription(rs.getString("packageName"));
            
            if(rs.getString("m.middleName").equals("")) {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
            }
            else {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("m.lastName"));
            }
            
            sub.setMemberUsername(rs.getString("m.username"));
            sub.setSubscriptionStartDate(rs.getDate("sub.startDate"));
            sub.setSubscriptionEndDate(rs.getDate("sub.endDate"));
            sub.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            sub.setSubscriptionId(rs.getInt("subscriptionId"));
            sub.setOfferPrice(rs.getFloat("sub.offerPrice"));
            sub.setDeclineMessage(rs.getString("sub.declineMessage"));
            
            if(rs.getString("a.middleName").equals("")) {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.lastName"));
            }
            else {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.middleName") + " " + rs.getString("a.lastName"));
            }
            
            subscription.add(sub);
        }
        return subscription;
    }
     
    
    /**
     * Searches for subscriptions as per query and filter while logged in as administrator
     * @param memberFirstName First name of member
     * @param memberMiddleName Middle name of member
     * @param memberLastName Last name of member
     * @param memberUsername Username of member
     * @param packageName Name of package
     * @param adminFirstName First name of administrator
     * @param adminMiddleName Middle name of administrator
     * @param adminLastName Last name of administrator
     * @return ObservableList of Subscription object
     * @throws SQLException 
     */
    public ObservableList<Subscription> searchInAdminViewSubscription(String memberFirstName, 
            String memberMiddleName, String memberLastName, String memberUsername, 
            String packageName, String adminFirstName, String adminMiddleName, 
            String adminLastName) throws SQLException {
        Connection conn = establishConnection();

        String query = "SELECT m.firstName, m.middleName, m.lastName,"
                + " m.username, packageName, sub.startDate, sub.endDate, subscriptionStatus,"
                + " subscriptionId, sub.Admin_adminId, sub.offerPrice, sub.declineMessage, a.firstName,"
                + " a.middleName, a.lastName FROM Subscription AS sub "
                + " INNER JOIN package AS pk"
                + " ON pk.packageId = sub.Package_packageId"
                + " INNER JOIN member AS m"
                + " ON sub.Member_memberId = m.memberId"
                + " INNER JOIN admin AS a"
                + " ON sub.Admin_adminId = a.adminId"
                + " WHERE m.firstName LIKE \"%" + memberFirstName + "%\""
                + " OR m.middleName LIKE \"%" + memberMiddleName + "%\""
                + " OR m.lastname LIKE \"%" + memberLastName + "%\""
                + " OR m.username LIKE \"%" + memberUsername + "%\""
                + " OR packageName LIKE \"%" + packageName + "%\""
                + " OR a.firstName LIKE \"%" + adminFirstName + "%\""
                + " OR a.middleName LIKE \"%" + adminMiddleName + "%\""
                + " OR a.lastname LIKE \"%" + adminLastName + "%\""
                        + " AND NOT subscriptionStatus = 'Requested'";

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Subscription> subscription = FXCollections.observableArrayList();
        Subscription sub;

        while (rs.next()) {
            sub = new Subscription(rs.getString("packageName"));

            if(rs.getString("m.middleName").equals("")) {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
            }
            else {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("m.lastName"));
            }
            
            sub.setMemberUsername(rs.getString("m.username"));
            sub.setSubscriptionStartDate(rs.getDate("sub.startDate"));
            sub.setSubscriptionEndDate(rs.getDate("sub.endDate"));
            sub.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            sub.setSubscriptionId(rs.getInt("subscriptionId"));
            sub.setOfferPrice(rs.getFloat("sub.offerPrice"));
            sub.setDeclineMessage(rs.getString("sub.declineMessage"));
            
            if(rs.getString("a.middleName").equals("")) {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.lastName"));
            }
            else {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.middleName") + " " + rs.getString("a.lastName"));
            }
            
            subscription.add(sub);
        }
        return subscription;
    }
     
    
    /**
     * Views declined subscriptions while logged in as administrator
     * @return ObservableList of Subscription object
     * @throws SQLException 
     */
    public ObservableList<Subscription> adminViewDeclinedSubscription() throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT m.firstName, m.middleName, m.lastName,"
                + " m.username, packageName, sub.startDate, sub.endDate, subscriptionStatus,"
                + " subscriptionId, sub.Admin_adminId, sub.declineMessage, a.firstName,"
                + " a.middleName, a.lastName FROM Subscription AS sub "
                + " INNER JOIN package AS pk"
                + " ON pk.packageId = sub.Package_packageId"
                + " INNER JOIN member AS m"
                + " ON sub.Member_memberId = m.memberId"
                + " INNER JOIN admin AS a"
                + " ON sub.Admin_adminId = a.adminId"
                + " WHERE subscriptionStatus = 'Declined'";
        
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Subscription> subscription = FXCollections.observableArrayList();
        Subscription sub;
        
        while (rs.next()) {
            sub = new Subscription(rs.getString("packageName"));
            
            if(rs.getString("m.middleName").equals("")) {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
            }
            else {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("m.lastName"));
            }
            
            sub.setMemberUsername(rs.getString("m.username"));
            sub.setSubscriptionStartDate(rs.getDate("sub.startDate"));
            sub.setSubscriptionEndDate(rs.getDate("sub.endDate"));
            sub.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            sub.setSubscriptionId(rs.getInt("subscriptionId"));
            sub.setDeclineMessage(rs.getString("sub.declineMessage"));

            if(rs.getString("a.middleName").equals("")) {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.lastName"));
            }
            else {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.middleName") + " " + rs.getString("a.lastName"));
            }
            
            subscription.add(sub);
        }
        return subscription;
    }
     
    
    /**
     * Searches for declined subscriptions as per query and filter while logged in as administrator
     * @param memberFirstName First name of member
     * @param memberMiddleName Middle name of member
     * @param memberLastName Last name of member
     * @param memberUsername Username of member
     * @param packageName Name of package
     * @param adminFirstName First name of administrator
     * @param adminMiddleName Middle name of administrator
     * @param adminLastName Last name of administrator
     * @return
     * @throws SQLException 
     */
    public ObservableList<Subscription> searchInAdminViewDeclinedSubscription(String memberFirstName, String memberMiddleName, String memberLastName, String memberUsername, String packageName, String adminFirstName, String adminMiddleName, String adminLastName) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT m.firstName, m.middleName, m.lastName,"
                + " m.username, packageName, sub.startDate, sub.endDate, subscriptionStatus,"
                + " subscriptionId, sub.Admin_adminId, sub.offerPrice, sub.declineMessage, a.firstName,"
                + " a.middleName, a.lastName FROM Subscription AS sub "
                + " INNER JOIN package AS pk"
                + " ON pk.packageId = sub.Package_packageId"
                + " INNER JOIN member AS m"
                + " ON sub.Member_memberId = m.memberId"
                + " INNER JOIN admin AS a"
                + " ON sub.Admin_adminId = a.adminId"
                + " WHERE subscriptionStatus = 'Declined'"
                + " AND (m.firstName LIKE \"%" + memberFirstName + "%\""
                + " OR m.middleName LIKE \"%" + memberMiddleName + "%\""
                + " OR m.lastname LIKE \"%" + memberLastName + "%\""
                + " OR m.username LIKE \"%" + memberUsername + "%\""
                + " OR packageName LIKE \"%" + packageName + "%\""
                + " OR a.firstName LIKE \"%" + adminFirstName + "%\""
                + " OR a.middleName LIKE \"%" + adminMiddleName + "%\""
                + " OR a.lastname LIKE \"%" + adminLastName + "%\")"
                + " AND NOT subscriptionStatus = 'Requested'";

        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<Subscription> subscription = FXCollections.observableArrayList();
        Subscription sub;
        
        while (rs.next()) {
            sub = new Subscription(rs.getString("packageName"));
            
            if(rs.getString("m.middleName").equals("")) {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
            }
            else {
                sub.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("m.lastName"));
            }
            
            sub.setMemberUsername(rs.getString("m.username"));
            sub.setSubscriptionStartDate(rs.getDate("sub.startDate"));
            sub.setSubscriptionEndDate(rs.getDate("sub.endDate"));
            sub.setSubscriptionStatus(rs.getString("subscriptionStatus"));
            sub.setSubscriptionId(rs.getInt("subscriptionId"));
            sub.setOfferPrice(rs.getFloat("sub.offerPrice"));
            sub.setDeclineMessage(rs.getString("sub.declineMessage"));
            
            if(rs.getString("a.middleName").equals("")) {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.lastName"));
            }
            else {
                sub.setSubscriptionAdminFullName(rs.getString("a.firstName") + " " + rs.getString("a.middleName") + " " + rs.getString("a.lastName"));
            }

            subscription.add(sub);
        }
        return subscription;
    }
     
    
    /**
     * Searches for subscription requests as per query and filter while logged in as administrator
     * @param memberFirstName First name of member
     * @param memberMiddleName Middle name of member
     * @param memberLastName Last name of member
     * @param memberUsername Username of member
     * @param packageName Name of package
     * @return
     * @throws SQLException 
     */
    public ObservableList<SubscriptionRequest> searchInAdminViewSubscriptionRequests(String memberFirstName, String memberMiddleName, String memberLastName, String memberUsername, String packageName) throws SQLException {
        Connection conn = establishConnection();
        String query = "SELECT m.firstName, m.middleName, m.lastName,"
                + " m.username, packageName, price, pk.startDate, pk.endDate,"
                + " startTime, endTime, sub.startDate, sub.endDate, subscriptionStatus,"
                + " subscriptionId FROM Subscription AS sub"
                + " INNER JOIN package AS pk"
                + " ON pk.packageId = sub.Package_packageId"
                + " INNER JOIN member AS m"
                + " ON sub.Member_memberId = m.memberId"
                + " WHERE (m.firstName LIKE \"%" + memberFirstName + "%\""
                 + " OR m.middleName LIKE \"%" + memberMiddleName + "%\""
                 + " OR m.lastname LIKE \"%" + memberLastName + "%\""
                 + " OR m.username LIKE \"%" + memberUsername + "%\""
                 + " OR packageName LIKE \"%" + packageName + "%\")"
                 + " AND subscriptionStatus = 'Requested'";
        
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        ObservableList<SubscriptionRequest> subscriptionRequestList = FXCollections.observableArrayList();
       
        while(rs.next()) {
            SubscriptionRequest subscriptionRequest;
            subscriptionRequest = new SubscriptionRequest(
                    rs.getString("packageName"),
                    rs.getFloat("price"),
                    rs.getDate("pk.startDate"),
                    rs.getDate("pk.endDate"),
                    rs.getString("startTime"),
                    rs.getString("startTime"),
                    rs.getDate("sub.startDate"),
                    rs.getDate("sub.endDate"),
                    rs.getInt("subscriptionId")
            );
            
            if(rs.getString("m.middleName").equals("")) {
                 subscriptionRequest.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.lastName"));
            }
            else {
                 subscriptionRequest.setMemberFullName(rs.getString("m.firstName") + " " + rs.getString("m.middleName") + " " + rs.getString("lastName"));
            }
            subscriptionRequest.setMemberUsername(rs.getString("m.username"));
            subscriptionRequestList.add(subscriptionRequest);
        }
        return subscriptionRequestList;
    }
     
     
     
     
     
     
     
     
     public ResultSet searchSchedule(String date){
        Connection conn = establishConnection();
        try {
            String query = "SELECT * FROM Schedule WHERE date LIKE\"%" + date + "%\"";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs; 
        }   catch (SQLException e) {
            System.out.println("error. Not found.");
            return null;
        }
    }
     
     public ResultSet adminRitriveSchedule(){
        try {
            String statement = "select * from schedule";
            Connection conn = establishConnection();
            PreparedStatement prepStmt = conn.prepareStatement(statement);
            System.out.println("1");
            System.out.println("PS " + prepStmt);
            ResultSet rs = prepStmt.executeQuery();
            System.out.println("2");
            return rs;
        } catch (Exception e) {
            System.out.println("Cannot ritrive schedule.");
            return null;
        }
    }
     
     public static void adminCreateSchedule(Schedule s, int adminId) throws SQLException {
         try{
            DBHandler db = new DBHandler();
            Connection conn = db.establishConnection();
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
     
     public void deleteSchedule(Date date){
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
     
     public void fillComboBox(ObservableList option){
         Connection conn = null;
         try {
             String query = "SELECT date FROM schedule ORDER BY date DESC";
             conn = establishConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query);
             ResultSet rs = prepStmt.executeQuery();
             while(rs.next()){
                 option.add(rs.getDate("date"));
             }
         } catch (Exception e) {
             System.out.println("fill combox error: "+ e);
         }
     }
     
     public void updateSchedule(Schedule s){
         try{
            Connection conn = establishConnection();
            String selectStatement = "UPDATE schedule SET openingTime = ?, closingTime = ?, isHoliday = ? WHERE date = ?";
            PreparedStatement prepStmt = (PreparedStatement) conn.prepareStatement(selectStatement);
            prepStmt.setString(1, s.getOpeningTime());
            prepStmt.setString(2, s.getClosingTime());
            prepStmt.setBoolean(3, s.getIsHoliday());
            //prepStmt.setInt(5, s.getId());
            prepStmt.setDate(4, s.getDate());
            prepStmt.executeUpdate();
            
            System.out.println("the data has been updated");
        }
        catch(Exception e){
            e.printStackTrace();
        }
     }
     
     public void loadScheduleData(ComboBox schedule,DatePicker dp,TextField oh,TextField om,TextField ch,TextField cm,ComboBox openingTimeState,ComboBox closingTimeState,CheckBox cb){
         Connection conn = null;
         Helper help = new Helper();
         try {
             String query = "SELECT * FROM schedule WHERE date=?";
             conn = establishConnection();
             PreparedStatement prepStmt = conn.prepareStatement(query);
             Date date = Date.valueOf(schedule.getSelectionModel().getSelectedItem().toString());
             prepStmt.setDate(1, date);
             ResultSet rs = prepStmt.executeQuery();
             
             while(rs.next()){
                 dp.setValue(rs.getDate("date").toLocalDate());
                 openingTimeState.setValue("24Hour");
                 closingTimeState.setValue("24Hour");
                 oh.setText(getScheduleOpeningHour(rs.getString("openingTime")));
                 om.setText(getScheduleOpeningMin(rs.getString("openingTime")));
                 ch.setText(getScheduleClosingHour(rs.getString("closingTime")));
                 cm.setText(getScheduleClosingMin(rs.getString("closingTime")));
                 if(rs.getBoolean("isHoliday")){
                     cb.setSelected(true);
                 }else{
                     cb.setSelected(false);
                 }
             }
         } catch (Exception e) {
             System.out.println("fill combox error: "+ e);
         }
     }
     
     public String getScheduleOpeningHour(String time) throws ParseException{
         Helper help = new Helper();
         Time openingTime = help.toSqlTime(time);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(openingTime.getTime());
         
         String oh = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
         return help.hourFormat(help.hourFormat(oh));
     }
     
     public String getScheduleOpeningMin(String time) throws ParseException{
         Helper help = new Helper();
         Time openingTime = help.toSqlTime(time);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(openingTime.getTime());
         
         String om = Integer.toString(calendar.get(Calendar.MINUTE));
         return help.minuteFormat(help.minuteFormat(om));
     }
     public String getScheduleClosingHour(String time) throws ParseException{
         Helper help = new Helper();
         Time closingTime = help.toSqlTime(time);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(closingTime.getTime());
         
         String ch = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
         return help.hourFormat(help.hourFormat(ch));
     }
     public String getScheduleClosingMin(String time) throws ParseException{
         Helper help = new Helper();
         Time closingTime = help.toSqlTime(time);
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(closingTime.getTime());
         
         String cm = Integer.toString(calendar.get(Calendar.MINUTE));
         return help.minuteFormat(help.minuteFormat(cm));
     }
     
     public boolean exisitDate(DatePicker dp) throws SQLException{
         Connection conn = null;
         Date date = Date.valueOf(dp.getValue());
         String statement = "SELECT count(date) FROM schedule WHERE date = ?";
         conn = establishConnection();
         PreparedStatement prepStmt = conn.prepareStatement(statement);
         prepStmt.setDate(1,date);
         ResultSet rs = prepStmt.executeQuery();
         int count = 0;
         while(rs.next()){
             count = rs.getInt(1);
         }
         if(count==0) {
             return true;
         }
         else {
             return false;
         }
     }
     
     
    // To save announcements into database.
    public void saveAnnouncement(Announcement announcement) throws SQLException {
        try (Connection conn = establishConnection()) {
        String query = "Insert Into announcement"
                    + "(date, time, title, body, numberOfViews, Admin_adminId)"
                    + "Values (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDate(1, announcement.getDate());
        stmt.setString(2, announcement.getTime());
        stmt.setString(3, announcement.getTitle());
        stmt.setString(4, announcement.getBody());
        stmt.setInt(5, 1);
        stmt.setInt(6, announcement.getAdminId());
        stmt.execute();
        }
    }
        
    public ObservableList<Announcement> adminViewAnnouncement() throws SQLException {
        ObservableList<Announcement> adminViewAnnouncement = FXCollections.observableArrayList();
       Connection conn = establishConnection();
        try {
            String query = String.format("select title, body, date, time, username from announcement" 
                    + " inner join admin on announcement.Admin_adminId = admin.adminId");
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setTime(rs.getString("time"));
                announcement.setDate(rs.getDate("date"));
                announcement.setBody(rs.getString("body"));
                announcement.setTitle(rs.getString("title"));
                announcement.setUsername(rs.getString("username"));
                adminViewAnnouncement.add(announcement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminViewAnnouncement;
    }
    
    public ObservableList<Announcement> memberViewAnnouncement() throws SQLException {
        ObservableList<Announcement> memberViewAnnouncement = FXCollections.observableArrayList();
       Connection conn = establishConnection();
        System.out.println("1");
        try {
            String query = String.format("select title, body, date, time, username from announcement" 
                    + " inner join admin on announcement.Admin_adminId = admin.adminId");
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setTime(rs.getString("time"));
                announcement.setDate(rs.getDate("date"));
                announcement.setBody(rs.getString("body"));
                announcement.setTitle(rs.getString("title"));
                announcement.setBody(rs.getString("body"));
                announcement.setUsername(rs.getString("username"));
                memberViewAnnouncement.add(announcement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberViewAnnouncement;
    }

     public ObservableList<Announcement> searchInAdminViewAnnouncement(String str) throws SQLException {
        ObservableList<Announcement> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "select title, body, date, time, username from announcement" 
                    + " inner join admin on announcement.Admin_adminId = admin.adminId"
                    + " WHERE username LIKE \"%" + str + "%\""
                    + " OR title LIKE \"%" + str + "%\""
                    + " OR body LIKE \"%" + str + "%\"";
        
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.println(statement);

      ResultSet rs = statement.executeQuery();
      
       while (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setTime(rs.getString("time"));
                announcement.setDate(rs.getDate("date"));
                announcement.setBody(rs.getString("body"));
                announcement.setTitle(rs.getString("title"));
                announcement.setUsername(rs.getString("username"));
                searchData.add(announcement);
            }
        return searchData;
     }   
     
      public ObservableList<Announcement> searchInMemeberViewAnnouncement(String str) throws SQLException {
        ObservableList<Announcement> searchData = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        
        String query = "select title, body, date, time, username from announcement" 
                    + " inner join admin on announcement.Admin_adminId = admin.adminId"
                    + " WHERE username LIKE \"%" + str + "%\""
                    + " OR title LIKE \"%" + str + "%\""
                    + " OR body LIKE \"%" + str + "%\"";
        
        PreparedStatement statement = conn.prepareStatement(query);
        System.out.println(statement);

      ResultSet rs = statement.executeQuery();
      
       while (rs.next()) {
                Announcement announcement = new Announcement();
                announcement.setTime(rs.getString("time"));
                announcement.setDate(rs.getDate("date"));
                announcement.setBody(rs.getString("body"));
                announcement.setTitle(rs.getString("title"));
                announcement.setUsername(rs.getString("username"));
                searchData.add(announcement);
            }
        return searchData;
     }            
    
     public boolean deleteAnnouncement(String title, String body, String time, Date date) throws SQLException {
        boolean deletionError;
        try (Connection conn = establishConnection()) {
            String query = "DELETE FROM Announcement WHERE title = ? AND body = ? AND time = ? AND date = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, body);
            statement.setString(3, time);
            statement.setDate(4, date);
            statement.execute();
            deletionError = false;
        }
        return deletionError;
    }
    }