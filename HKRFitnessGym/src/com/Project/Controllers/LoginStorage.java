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
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty username = new SimpleStringProperty();
    
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
    
    public int getId() {
        return id.get();
    }
    
    public String getUsername() {
        return username.get();
    }
    
    public StringProperty usernameProperty() {
        return username;
    }
}
