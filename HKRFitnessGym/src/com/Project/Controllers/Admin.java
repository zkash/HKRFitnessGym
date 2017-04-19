/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class Admin {

    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty lastName;
    private final StringProperty dateOfBirth;
    private final StringProperty gender;
    private final StringProperty address;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final IntegerProperty ssn;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty fullName;

    public Admin(String fullName, String uname, int ssn) {
        this.firstName = new SimpleStringProperty("");
        this.middleName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.dateOfBirth = new SimpleStringProperty("");
        this.gender = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.ssn = new SimpleIntegerProperty(ssn);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty(fullName);
    }
    
    public Admin(String dob, String fn, String mn, String ln, String add,
            String pnum, String uname, String pwd, String ead, String gen,
            int ssnum) {
        this.firstName = new SimpleStringProperty(fn);
        this.middleName = new SimpleStringProperty(mn);
        this.lastName = new SimpleStringProperty(ln);
        this.dateOfBirth = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gen);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleStringProperty(pnum);
        this.email = new SimpleStringProperty(ead);
        this.ssn = new SimpleIntegerProperty(ssnum);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pwd);
        this.fullName = (StringProperty) (this.firstName.concat(this.middleName)).concat(this.lastName);
    }

    // Getters
    public String getDOB() {
        return dateOfBirth.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }
    
    public String getFullName() {
        return fullName.get();
    }

    //Setters
    public void setDOB(String dob) {
        dateOfBirth.set(dob);
    }

    public void setFirstName(String fn) {
        firstName.set(fn);
    }

    public void setMiddleName(String mn) {
        middleName.set(mn);
    }

    public void setLastName(String ln) {
        lastName.set(ln);
    }

    public void setGender(String gen) {
        gender.set(gen);
    }

    public void setAddress(String add) {
        address.set(add);
    }

    public void setPhoneNumber(String pnum) {
        phoneNumber.set(pnum);
    }

    public void setEmail(String ead) {
        email.set(ead);
    }

    public void setUsername(String uname) {
        username.set(uname);
    }

    public void setPassword(String pwd) {
        password.set(pwd);
    }

    public void setSSN(int ssnum) {
        ssn.set(ssnum);
    }
    
    
    // Property values
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public StringProperty middleNameProperty() {
        return middleName;
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public StringProperty dobProperty() {
        return dateOfBirth;
    }
    
    public StringProperty genderProperty() {
        return gender;
    }
    
    public StringProperty addressProperty() {
        return address;
    }
    
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    public IntegerProperty ssnProperty() {
        return ssn;
    }
    
    public StringProperty usernameProperty() {
        return username;
    }
    
    public StringProperty passwordProperty() {
        return password;
    }
    
    public StringProperty fullNameProperty() {
        return fullName;
    }
}

//public class Admin {
//    
////    private final SimpleStringProperty dateOfBirth;
////    private final SimpleStringProperty adminFirstName;
////    private final SimpleStringProperty adminMiddleName;
////    private final SimpleStringProperty adminLastName;
////    private final SimpleStringProperty address;
////    private final SimpleStringProperty phoneNumber;
////    private final SimpleStringProperty username;
////    private final SimpleStringProperty password;
////    private final SimpleStringProperty email;
////    private final SimpleStringProperty gender;
////    private final SimpleStringProperty ssn;
//    
//    private static String dateOfBirth;
//    private static String adminFirstName;
//    private static String adminMiddleName;
//    private static String adminLastName;
//    private static String address;
//    private static String phoneNumber;
//    private static String username;
//    private static String password;
//    private static String email;
//    private static String gender;
//    private static String ssn;
//    
//    public Admin() {
//        
//    }
//    
//    public Admin(String fn, String mn, String ln, String un) {
//        this.adminFirstName = fn;
//        this.adminMiddleName = mn;
//        this.adminLastName = ln;
//        this.username = un;
//    }
//    
//    public Admin(String dob, String fn, String mn, String ln, String add, 
//            String pnum, String uname, String pwd, String ead, String gen,
//             String ssnum) {
////        this.dateOfBirth = new SimpleStringProperty(dob);
////        this.adminFirstName = new SimpleStringProperty(fn);
////        this.adminMiddleName = new SimpleStringProperty(mn);
////        this.adminLastName = new SimpleStringProperty(ln);
////        this.address = new SimpleStringProperty(add);
////        this.phoneNumber = new SimpleStringProperty(pnum);
////        this.username = new SimpleStringProperty(uname);
////        this.password = new SimpleStringProperty(pwd);
////        this.email = new SimpleStringProperty(ead);
////        this.gender = new SimpleStringProperty(gen);
////        this.ssn = new SimpleStringProperty(ssnum);
//
//        this.dateOfBirth = dob;
//        this.adminFirstName = fn;
//        this.adminMiddleName = mn;
//        this.adminLastName = ln;
//        this.address = add;
//        this.phoneNumber = pnum;
//        this.username = uname;
//        this.password = pwd;
//        this.email = ead;
//        this.gender = gen;
//        this.ssn = ssnum;
//    }
//
//    
////    public String getDOB() {
////        return dateOfBirth.get();
////    }
////    
////    public String getFirstName() {
////        return adminFirstName.get();
////    }
////    
////    public String getMiddleName() {
////        return adminMiddleName.get();
////    }
////    
////    public String getLastName() {
////        return adminLastName.get();
////    }
////    
////    public String getGender() {
////        return gender.get();
////    }
////    
////    public String getAddress() {
////        return address.get();
////    }
////    
////    public String getPhoneNumber() {
////        return phoneNumber.get();
////    }
////    
////    public String getEmail() {
////        return email.get();
////    }
////    
////    public String getUsername() {
////        return username.get();
////    }
////    
////    public String getPassword() {
////        return password.get();
////    }
////    
////    public String getSSN() {
////        return ssn.get();
////    }
////    
////    public void setDOB(String dob) {
////        dateOfBirth.set(dob);
////    }
////    
////    public void setFirstName(String fn) {
////        adminFirstName.set(fn);
////    }
////    
////    public void setMiddleName(String mn) {
////        adminMiddleName.set(mn);
////    }
////    
////    public void setLastName(String ln) {
////        dateOfBirth.set(ln);
////    }
////    
////    public void setGender(String gen) {
////        gender.set(gen);
////    }
////    
////    public void setAddress(String add) {
////        address.set(add);
////    }
////    
////    public void setPhoneNumber(String pnum) {
////        phoneNumber.set(pnum);
////    }
////    
////    public void setEmail(String ead) {
////        email.set(ead);
////    }
////    
////    public void setUsername(String uname) {
////        username.set(uname);
////    }
////    
////    public void setPassword(String pwd) {
////        password.set(pwd);
////    }
////    
////    public void setSSN(String ssnum) {
////        ssn.set(ssnum);
////    }}
////          
//    
//public String getDOB() {
//        return dateOfBirth;
//    }
//    
//    public static String getFirstName() {
//        return adminFirstName;
//    }
//    
//    public static String getMiddleName() {
//        return adminMiddleName;
//    }
//    
//    public static String getLastName() {
//        return adminLastName;
//    }
//    
//    public static String getGender() {
//        return gender;
//    }
//    
//    public static String getAddress() {
//        return address;
//    }
//    
//    public static String getPhoneNumber() {
//        return phoneNumber;
//    }
//    
//    public static String getEmail() {
//        return email;
//    }
//    
//    public static String getUsername() {
//        return username;
//    }
//    
//    public static String getPassword() {
//        return password;
//    }
//    
//    public static String getSSN() {
//        return ssn;
//    }
//    
//    public static void setDOB(String dob) {
//        dateOfBirth = dob;
//    }
//    
//    public static void setFirstName(String fn) {
//        adminFirstName = fn;
//    }
//    
//    public static void setMiddleName(String mn) {
//        adminMiddleName = mn;
//    }
//    
//    public static void setLastName(String ln) {
//        adminLastName = ln;
//    }
//    
//    public static void setGender(String gen) {
//        gender = gen;
//    }
//    
//    public static void setAddress(String add) {
//        address = add;
//    }
//    
//    public static void setPhoneNumber(String pnum) {
//        phoneNumber = pnum;
//    }
//    
//    public static void setEmail(String ead) {
//        email = ead;
//    }
//    
//    public static void setUsername(String uname) {
//        username = uname;
//    }
//    
//    public static void setPassword(String pwd) {
//        password = pwd;
//    }
//    
//    public static void setSSN(String ssnum) {
//        ssn = ssnum;
//    }}
//          
