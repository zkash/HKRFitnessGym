/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Models;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class Person {

    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private Date dateOfBirth = null;
    private String gender = null;
    private String address = null;
    private int phoneNumber = 0;
    private String email = null;
    private int ssn1 = 0;
    private int ssn2 = 0;
    private String username = null;
    private String password = null;
    private String fullName = null;
    private String fullSSN = null;

    //For AdminViewAdminAccounts, AdminViewMemberAccounts
    public Person(String fullName, String uname, String gen, Date dob,
            String add, int pnum, String ead, String fullSSN) {
        this.fullName = fullName;
        this.username = uname;
        this.dateOfBirth = dob;
        this.gender = gen;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.fullSSN = fullSSN;
    }
    
    // For CreateAdminAccount, CreateMemberAccount
    public Person(String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssn1, int ssn2, String uname, String pwd) {
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.gender = gen;
        this.dateOfBirth = dob;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = uname;
        this.password = pwd;
    }
    
    // For AdminViewPersonalInformationController, UpdateAdminPersonalInformationPageController
    public Person(String fn, String mn, String ln, Date dob, String add,
            int pnum, String ead, String gen, int ssn1, int ssn2) {
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.dateOfBirth = dob;
        this.gender = gen;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    }
    
    
    
    public Person(String fullName, String uname, int ssn1, int ssn2) {
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = uname;
        this.fullName = fullName;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    }
    
    
    
    public Person(String fn, String mn, String ln, Date dob, String add,
            int pnum, String uname, String pwd, String ead, String gen,
            int ssn1, int ssn2) {
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.dateOfBirth = dob;
        this.gender = gen;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = uname;
        this.password = pwd;
        this.fullName = this.firstName + " " + this.middleName + " " + this.lastName;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    }

    
    
    // Getters
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getSSN1() {
        return ssn1;
    }
    
    public int getSSN2() {
        return ssn2;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getFullName() {
        return fullName;
    }

    public String getFullSSN() {
        return fullSSN;
    }
    
    //Setters
    public void setDateOfBirth(Date dob) {
        this.dateOfBirth = dob;
    }

    public void setFirstName(String fn) {
        this.firstName = fn;
    }

    public void setMiddleName(String mn) {
        this.middleName = mn;
    }

    public void setLastName(String ln) {
        this.lastName = ln;
    }

    public void setGender(String gen) {
        this.gender = gen;
    }

    public void setAddress(String add) {
        this.address = add;
    }

    public void setPhoneNumber(int pnum) {
        this.phoneNumber = pnum;
    }

    public void setEmail(String ead) {
        this.email = ead;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public void setSSN1(int ssnum1) {
        this.ssn1 = ssnum1;
    }
    
    public void setSSN2(int ssnum2) {
        this.ssn2 = ssnum2;
    }
    
    public void setFullSSN(String fullSSNum) {
        this.fullSSN = fullSSNum;
    }
    
    
//    // Property values
//    public StringProperty firstNameProperty() {
//        return firstName;
//    }
//    
//    public StringProperty middleNameProperty() {
//        return middleName;
//    }
//    
//    public StringProperty lastNameProperty() {
//        return lastName;
//    }
    
//    public StringProperty dobProperty() {
//        return dateOfBirth;
//    }
    
//    public StringProperty genderProperty() {
//        return gender;
//    }
//    
//    public StringProperty addressProperty() {
//        return address;
//    }
//    
//    public IntegerProperty phoneNumberProperty() {
//        return phoneNumber;
//    }
//    
//    public StringProperty emailProperty() {
//        return email;
//    }
//    
//    public IntegerProperty ssn1Property() {
//        return ssn1;
//    }
//    
//    public IntegerProperty ssn2Property() {
//        return ssn2;
//    }
//    
//    public StringProperty usernameProperty() {
//        return username;
//    }
//    
//    public StringProperty passwordProperty() {
//        return password;
//    }
//    
//    public StringProperty fullNameProperty() {
//        return fullName;
//    }
//    
//    public StringProperty fullSSNProperty() {
//        return fullSSN;
//    }
    
    public Person(String fn, String mn, String ln, String gen, String add, String ead, String uname, String pwd, int ssn1, int ssn2, int pnum) {
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.dateOfBirth = null;
        this.gender = gen;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = uname;
        this.password = pwd;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    }
    
//    public Person(String fn, String mn, String ln, String gen, Date dob, String add, String ead, int pnum, String uname, String pwd, int ssn1, int ssn2, String fullSSN) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.dateOfBirth = dob;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty(pwd);
//        this.fullName = null;
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    }
    
    public Person(String fn, String mn, String ln, String gen, Date dob, String add, String ead, int pnum, String uname, String pwd, int ssn1, int ssn2) {
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.dateOfBirth = dob;
        this.gender = gen;
        this.address = add;
        this.phoneNumber = pnum;
        this.email = ead;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = uname;
        this.password = pwd;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    } 
}


///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.Project.Models;
//
//import java.sql.Date;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
///**
// *
// * @author shameer
// */
//public class Person {
//
//    private final StringProperty firstName;
//    private final StringProperty middleName;
//    private final StringProperty lastName;
//    private Date dateOfBirth;
//    private final StringProperty gender;
//    private final StringProperty address;
//    private final IntegerProperty phoneNumber;
//    private final StringProperty email;
//    private final IntegerProperty ssn1;
//    private final IntegerProperty ssn2;
//    private final StringProperty username;
//    private final StringProperty password;
//    private final StringProperty fullName;
//    private final StringProperty fullSSN;
//
//    //For AdminViewAdminAccounts, AdminViewMemberAccounts
//    public Person(String fullName, String uname, String gen, Date dob,
//            String add, int pnum, String ead, String fullSSN) {
//        this.firstName = new SimpleStringProperty("");
//        this.middleName = new SimpleStringProperty("");
//        this.lastName = new SimpleStringProperty("");
//        this.dateOfBirth = dob;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = null;
//        this.ssn2 = null;
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty("");
//        this.fullName = new SimpleStringProperty(fullName);
//        this.fullSSN = new SimpleStringProperty(fullSSN);
//    }
//    
//    // For CreateAdminAccount, CreateMemberAccount
//    public Person(String fn, String mn, String ln, String gen, Date dob, String add, int pnum, String ead, int ssn1, int ssn2, String uname, String pwd) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.gender = new SimpleStringProperty(gen);
//        this.dateOfBirth = dob;
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty(pwd);
//        this.fullName = null;
//        this.fullSSN = null;
//    }
//    
//    // For AdminViewPersonalInformationController, UpdateAdminPersonalInformationPageController
//    public Person(String fn, String mn, String ln, Date dob, String add,
//            int pnum, String ead, String gen, int ssn1, int ssn2) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.dateOfBirth = dob;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = null;
//        this.password = null;
//        this.fullName = null;
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    }
//    
//    
//    
//    public Person(String fullName, String uname, int ssn1, int ssn2) {
//        this.firstName = new SimpleStringProperty("");
//        this.middleName = new SimpleStringProperty("");
//        this.lastName = new SimpleStringProperty("");
//        this.dateOfBirth = null;
//        this.gender = new SimpleStringProperty("");
//        this.address = new SimpleStringProperty("");
//        this.phoneNumber = new SimpleIntegerProperty();
//        this.email = new SimpleStringProperty("");
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty("");
//        this.fullName = new SimpleStringProperty(fullName);
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    }
//    
//    
//    
//    public Person(String fn, String mn, String ln, Date dob, String add,
//            int pnum, String uname, String pwd, String ead, String gen,
//            int ssn1, int ssn2) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.dateOfBirth = dob;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty(pwd);
//        this.fullName = (StringProperty) (this.firstName.concat(this.middleName)).concat(this.lastName);
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    }
//
//    
//    
//    // Getters
//    public Date getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public String getFirstName() {
//        return firstName.get();
//    }
//
//    public String getMiddleName() {
//        return middleName.get();
//    }
//
//    public String getLastName() {
//        return lastName.get();
//    }
//
//    public String getGender() {
//        return gender.get();
//    }
//
//    public String getAddress() {
//        return address.get();
//    }
//
//    public int getPhoneNumber() {
//        return phoneNumber.get();
//    }
//
//    public String getEmail() {
//        return email.get();
//    }
//
//    public int getSSN1() {
//        return ssn1.get();
//    }
//    
//    public int getSSN2() {
//        return ssn2.get();
//    }
//    
//    public String getUsername() {
//        return username.get();
//    }
//
//    public String getPassword() {
//        return password.get();
//    }
//    
//    public String getFullName() {
//        return fullName.get();
//    }
//
//    public String getFullSSN() {
//        return fullSSN.get();
//    }
//    
//    //Setters
//    public void setDateOfBirth(Date dob) {
//        dateOfBirth = dob;
//    }
//
//    public void setFirstName(String fn) {
//        firstName.set(fn);
//    }
//
//    public void setMiddleName(String mn) {
//        middleName.set(mn);
//    }
//
//    public void setLastName(String ln) {
//        lastName.set(ln);
//    }
//
//    public void setGender(String gen) {
//        gender.set(gen);
//    }
//
//    public void setAddress(String add) {
//        address.set(add);
//    }
//
//    public void setPhoneNumber(int pnum) {
//        phoneNumber.set(pnum);
//    }
//
//    public void setEmail(String ead) {
//        email.set(ead);
//    }
//
//    public void setUsername(String uname) {
//        username.set(uname);
//    }
//
//    public void setPassword(String pwd) {
//        password.set(pwd);
//    }
//
//    public void setSSN1(int ssnum1) {
//        ssn1.set(ssnum1);
//    }
//    
//    public void setSSN2(int ssnum2) {
//        ssn2.set(ssnum2);
//    }
//    
//    public void setFullSSN(String fullSSNum) {
//        fullSSN.set(fullSSNum);
//    }
//    
//    
//    // Property values
//    public StringProperty firstNameProperty() {
//        return firstName;
//    }
//    
//    public StringProperty middleNameProperty() {
//        return middleName;
//    }
//    
//    public StringProperty lastNameProperty() {
//        return lastName;
//    }
//    
////    public StringProperty dobProperty() {
////        return dateOfBirth;
////    }
//    
//    public StringProperty genderProperty() {
//        return gender;
//    }
//    
//    public StringProperty addressProperty() {
//        return address;
//    }
//    
//    public IntegerProperty phoneNumberProperty() {
//        return phoneNumber;
//    }
//    
//    public StringProperty emailProperty() {
//        return email;
//    }
//    
//    public IntegerProperty ssn1Property() {
//        return ssn1;
//    }
//    
//    public IntegerProperty ssn2Property() {
//        return ssn2;
//    }
//    
//    public StringProperty usernameProperty() {
//        return username;
//    }
//    
//    public StringProperty passwordProperty() {
//        return password;
//    }
//    
//    public StringProperty fullNameProperty() {
//        return fullName;
//    }
//    
//    public StringProperty fullSSNProperty() {
//        return fullSSN;
//    }
//    
//    public Person(String fn, String mn, String ln, String gen, String add, String ead, String uname, String pwd, int ssn1, int ssn2, int pnum) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.dateOfBirth = null;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty(pwd);
//        this.fullName = null;
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    }
//    
////    public Person(String fn, String mn, String ln, String gen, Date dob, String add, String ead, int pnum, String uname, String pwd, int ssn1, int ssn2, String fullSSN) {
////        this.firstName = new SimpleStringProperty(fn);
////        this.middleName = new SimpleStringProperty(mn);
////        this.lastName = new SimpleStringProperty(ln);
////        this.dateOfBirth = dob;
////        this.gender = new SimpleStringProperty(gen);
////        this.address = new SimpleStringProperty(add);
////        this.phoneNumber = new SimpleIntegerProperty(pnum);
////        this.email = new SimpleStringProperty(ead);
////        this.ssn1 = new SimpleIntegerProperty(ssn1);
////        this.ssn2 = new SimpleIntegerProperty(ssn2);
////        this.username = new SimpleStringProperty(uname);
////        this.password = new SimpleStringProperty(pwd);
////        this.fullName = null;
////        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
////    }
//    
//    public Person(String fn, String mn, String ln, String gen, Date dob, String add, String ead, int pnum, String uname, String pwd, int ssn1, int ssn2) {
//        this.firstName = new SimpleStringProperty(fn);
//        this.middleName = new SimpleStringProperty(mn);
//        this.lastName = new SimpleStringProperty(ln);
//        this.dateOfBirth = dob;
//        this.gender = new SimpleStringProperty(gen);
//        this.address = new SimpleStringProperty(add);
//        this.phoneNumber = new SimpleIntegerProperty(pnum);
//        this.email = new SimpleStringProperty(ead);
//        this.ssn1 = new SimpleIntegerProperty(ssn1);
//        this.ssn2 = new SimpleIntegerProperty(ssn2);
//        this.username = new SimpleStringProperty(uname);
//        this.password = new SimpleStringProperty(pwd);
//        this.fullName = null;
//        this.fullSSN = new SimpleStringProperty(Integer.toString(ssn1) + "-" + Integer.toString(ssn2));
//    } 
//}