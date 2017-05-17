package com.Project.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class to represent credential of user in logged-in state
 *
 * @author shameer
 */
public class LoginStorage {
    private static LoginStorage loginStorage;
    private int id = 0;
    private final StringProperty username = new SimpleStringProperty();
    private String accountType = null;
    
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
     * @param id The id of the account
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Sets the username of the account
     * @param username The username of the account
     */
    public void setUsername(String username) {
        this.username.set(username);
    }
    
    
    /**
     * Sets the type of the account
     * @param accountType The type of the account
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    
    /**
     * Gets the id of the account
     * @return The id of the account
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Gets the username of the account
     * @return The username of the account
     */
    public String getUsername() {
        return this.username.get();
    }
    
    
    /**
     * Gets the type of the account
     * @return The type of the account
     */
    public String getAccountType() {
        return this.accountType;
    }
    
    
    /**
     * Returns the username property
     * @return The username property
     */
    public StringProperty usernameProperty() {
        return this.username;
    }
}