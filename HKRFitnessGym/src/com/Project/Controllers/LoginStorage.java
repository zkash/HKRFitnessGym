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
public class LoginStorage {
    private static LoginStorage loginStorage;
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty accountType = new SimpleStringProperty();
    
    private LoginStorage() {
        
    }
    
    public static LoginStorage getInstance() {
        if (loginStorage == null) {
            loginStorage = new LoginStorage();
        }
        return loginStorage;
    }
    
    public void setId(int i) {
        id.set(i);
    }
    
    public void setUsername(String un) {
        username.set(un);
    }
    
    public void setAccountType(String accType) {
        accountType.set(accType);
    }
    
    public int getId() {
        return id.get();
    }
    
    public String getUsername() {
        return username.get();
    }
    
    public String getAccountType() {
        return accountType.get();
    }
    
    public StringProperty usernameProperty() {
        return username;
    }
}
