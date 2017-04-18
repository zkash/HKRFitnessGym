/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author shameer
 */
public class Admin {
    
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty adminFirstName;
    private final SimpleStringProperty adminMiddleName;
    private final SimpleStringProperty adminLastName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty email;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty ssn;
    
    public Admin(String dob, String fn, String mn, String ln, String add, 
            String pnum, String uname, String pwd, String ead, String gen,
             String ssnum) {
        this.dateOfBirth = new SimpleStringProperty(dob);
        this.adminFirstName = new SimpleStringProperty(fn);
        this.adminMiddleName = new SimpleStringProperty(mn);
        this.adminLastName = new SimpleStringProperty(ln);
        this.address = new SimpleStringProperty(add);
        this.phoneNumber = new SimpleStringProperty(pnum);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pwd);
        this.email = new SimpleStringProperty(ead);
        this.gender = new SimpleStringProperty(gen);
        this.ssn = new SimpleStringProperty(ssnum);
    }

    
    public String getDOB() {
        return dateOfBirth.get();
    }
    
    public String getFirstName() {
        return adminFirstName.get();
    }
    
    public String getMiddleName() {
        return adminMiddleName.get();
    }
    
    public String getLastName() {
        return adminLastName.get();
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
    
    public String getSSN() {
        return ssn.get();
    }
    
    public void setDOB(String dob) {
        dateOfBirth.set(dob);
    }
    
    public void setFirstName(String fn) {
        adminFirstName.set(fn);
    }
    
    public void setMiddleName(String mn) {
        adminMiddleName.set(mn);
    }
    
    public void setLastName(String ln) {
        dateOfBirth.set(ln);
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
    
    public void setSSN(String ssnum) {
        ssn.set(ssnum);
    }}
          