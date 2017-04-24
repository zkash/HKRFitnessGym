/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty lastName;
    private Date dateOfBirth;
    private final StringProperty gender;
    private final StringProperty address;
    private final IntegerProperty phoneNumber;
    private final StringProperty email;
    private final LongProperty ssn;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty fullName;

    public Person(String fullName, String uname, long ssn) {
        this.firstName = new SimpleStringProperty("");
        this.middleName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.dateOfBirth = null;
        this.gender = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.phoneNumber = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty("");
        this.ssn = new SimpleLongProperty(ssn);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty(fullName);
    }
    
    public Person(String fullName, String uname, String gen, Date dob,
            String add, int pnum, String ead, long ssn) {
        this.firstName = new SimpleStringProperty("");
        this.middleName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.dateOfBirth = dob;
        this.gender = new SimpleStringProperty(gen);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleIntegerProperty(pnum);
        this.email = new SimpleStringProperty(ead);
        this.ssn = new SimpleLongProperty(ssn);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty(fullName);
    }
    
    public Person(String fn, String mn, String ln, Date dob, String add,
            int pnum, String uname, String pwd, String ead, String gen,
            long ssn) {
        this.firstName = new SimpleStringProperty(fn);
        this.middleName = new SimpleStringProperty(mn);
        this.lastName = new SimpleStringProperty(ln);
        this.dateOfBirth = dob;
        this.gender = new SimpleStringProperty(gen);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleIntegerProperty(pnum);
        this.email = new SimpleStringProperty(ead);
        this.ssn = new SimpleLongProperty(ssn);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pwd);
        this.fullName = (StringProperty) (this.firstName.concat(this.middleName)).concat(this.lastName);
    }

    public Person(String fn, String mn, String ln, Date dob, String add,
            int pnum, String ead, String gen, long ssn) {
        this.firstName = new SimpleStringProperty(fn);
        this.middleName = new SimpleStringProperty(mn);
        this.lastName = new SimpleStringProperty(ln);
        this.dateOfBirth = dob;
        this.gender = new SimpleStringProperty(gen);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleIntegerProperty(pnum);
        this.email = new SimpleStringProperty(ead);
        this.ssn = new SimpleLongProperty(ssn);
        this.username = null;
        this.password = null;
        this.fullName = null;
    }
    
    // Getters
    public Date getDOB() {
        return dateOfBirth;
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

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public long getSSN() {
        return ssn.get();
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
    public void setDOB(Date dob) {
        dateOfBirth = dob;
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

    public void setPhoneNumber(int pnum) {
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

    public void setSSN(long ssnum) {
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
    
//    public StringProperty dobProperty() {
//        return dateOfBirth;
//    }
    
    public StringProperty genderProperty() {
        return gender;
    }
    
    public StringProperty addressProperty() {
        return address;
    }
    
    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    public LongProperty ssnProperty() {
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
    
    public Person(String fn, String mn, String ln, String gen, String add, String ead, String uname, String pwd, long ssn, int pnum) {
        this.firstName = new SimpleStringProperty(fn);
        this.middleName = new SimpleStringProperty(mn);
        this.lastName = new SimpleStringProperty(ln);
        this.dateOfBirth = null;
        this.gender = new SimpleStringProperty(gen);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleIntegerProperty(pnum);
        this.email = new SimpleStringProperty(ead);
        this.ssn = new SimpleLongProperty(ssn);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pwd);
        this.fullName = null;
    }
}