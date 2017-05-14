package com.Project.Models;

import com.Project.JDBC.DTO.Schedule;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author shameer
 */
public class DBHandler {
    static Connection c;
    private String currentUser;
    private static int memberId;

    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final String connectionURL;

    public DBHandler() {
        Properties properties = loadProperties();
        databaseName = properties.getProperty("databaseName");
        databaseUsername = properties.getProperty("databaseUsername");
        databasePassword = properties.getProperty("databasePassword");
        connectionURL = "jdbc:mysql://localhost/" + databaseName + "?user=" + databaseUsername + "&password=" + databasePassword + "&useSSL=false";
   
        this.currentUser = null;
        this.memberId = 0;
    }

      
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
    
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/com/Project/Properties/hkrFitnessGym.properties")) {
            properties.load(fis);
        }
        catch (Exception e) {
        }
        return properties;
    }
    
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

    public ObservableList<Package> adminViewPackages() throws SQLException {
        ObservableList<Package> data = FXCollections.observableArrayList();
        Connection conn = establishConnection();
        try {
            String query = String.format("SELECT Package.*, Admin.firstName, "
                    + "Admin.middleName, Admin.lastName FROM Package, Admin "
                    + "WHERE Admin.adminId = Package.Admin_adminId ");

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

    public void deletePackage(String packageName) throws SQLException {
        try (Connection conn = establishConnection()) {
            String query = "DELETE FROM Package WHERE packageName = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, packageName);
            statement.execute(); 
        }
    }

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
    
    public void cancelSubscription(int subscriptionId) throws SQLException {
        Connection conn = establishConnection();
        String query = "UPDATE Subscription SET subscriptionStatus = 'Canceled' WHERE subscriptionId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, subscriptionId);
        statement.executeUpdate();
    }
    
    public Boolean verifyUsername(String uname, String accountType) throws SQLException {
        Connection conn = establishConnection();
        String query = "";
        
        if(accountType.equals("Admin")) {
            query = "SELECT count(*) FROM Admin WHERE BINARY username = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT count(*) FROM Member WHERE BINARY username = ?";
        }
        
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, uname);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        
        while(rs.next()) {
            count = rs.getInt(1);
        }
        
        return count != 0;     
    }
    
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
    
    
    public String getPassword(int id, String accountType) throws SQLException {
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
        Connection conn = null;
        PreparedStatement prepStmt = null;
        //ResultSet rs = null;
        try {
            DBHandler db = new DBHandler();
            String statement = "SELECT * FROM Schedule WHERE date LIKE\"%" + date + "%\"";
            conn = db.establishConnection();
            prepStmt = conn.prepareStatement(statement);
            ResultSet rs = prepStmt.executeQuery();
            System.out.println("Success");
            return rs;
            
        } catch (Exception e) {
            System.out.println("error. Not found.");
            return null;
        }
        
    }
     
     public ResultSet adminRitriveSchedule(){
        Connection conn = null;
       // PreparedStatement prepStmt = null;
        //ResultSet rs = null;
        try {
           // DBHandler db = new DBHandler();
            String statement = "select * from schedule";
            conn = establishConnection();
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
      //  return rs;
    }
     
     public static void adminCreateSchedule(Schedule s, int adminId) throws SQLException {
        //String command = String.format("INSERT INTO schedule values (%b, %d, %d)", isHoliday, /*id,*/ ssn);
        //String c = "insert into schdeule (date, openningTime, closingTime, isHoliday, ssn) values ('"date + op + ct + isHoliday + ssn + ")'";
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
          return memberId;
     }
//        // Returns logged user.
//        public static String getLoggedUser(){
//            return currentUser;
//    }
        // Returns positon of user.
//        public static String getLoggedUserPosition(){
//            return position;
//    }        
   
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
        
    
    // To save announcements into database.
    public void saveAnnouncement(Announcement announcement) throws SQLException {
        try (Connection conn = establishConnection()) {
        String query = "Insert Into accouncement"
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

    
   
 /*
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
    }*/
     
    
    
    }
    
    public int getId(String username, String accountType) {
       Connection conn = establishConnection();
        String query = "";
        if(accountType.equals("Admin")) {
            query = "SELECT adminId FROM Admin WHERE username = ?";
        }
        else if(accountType.equals("Member")) {
            query = "SELECT memberId FROM Member WHERE username = ?";
        }
        int id = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
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
    }