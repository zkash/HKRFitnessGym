package com.Project.Models;

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
    
    /**
     * Default constructor
     */
    private LoginStorage() {
    }
    
    
    /**
     * Gets the instance of the LoginStorage class
     * @return The instance of LoginStorage class
     */
    public static LoginStorage getInstance() {
        if (loginStorage == null) {
            loginStorage = new LoginStorage();
        }
        return loginStorage;
    }
    
    
    /**
     * Sets the id of the account
     * @param i The id of the account
     */
    public void setId(int i) {
        id.set(i);
    }
    
    
    /**
     * Sets the username of the account
     * @param un The username of the account
     */
    public void setUsername(String un) {
        username.set(un);
    }
    
    
    /**
     * Sets the type of the account
     * @param accType The type of the account
     */
    public void setAccountType(String accType) {
        accountType.set(accType);
    }
    
    
    /**
     * Gets the id of the account
     * @return The id of the account
     */
    public int getId() {
        return id.get();
    }
    
    
    /**
     * Gets the username of the account
     * @return The username of the account
     */
    public String getUsername() {
        return username.get();
    }
    
    
    /**
     * Gets the type of the account
     * @return The type of the account
     */
    public String getAccountType() {
        return accountType.get();
    }
    
    
    /**
     * Returns the username property
     * @return The username property
     */
    public StringProperty usernameProperty() {
        return username;
    }
}