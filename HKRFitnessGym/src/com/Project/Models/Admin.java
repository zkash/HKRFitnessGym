package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent administrator of gym
 *
 * @author shameer
 */
public class Admin extends Person {
    /**
     * Constructor for AdminViewAdminAccounts, AdminViewMemberAccounts
     * @param fullName Full name of administrator
     * @param username Username of administrator
     * @param gender Gender of administrator
     * @param dateOfBirth Date of birth of administrator
     * @param address Address of administrator
     * @param phoneNumber Phone number of administrator
     * @param email Email address of administrator
     * @param fullSSN Full social security number of administrator
     */
    public Admin(String fullName, String username, String gender, Date dateOfBirth, 
            String address, int phoneNumber, String email, String fullSSN) {
        super(fullName, username, gender, dateOfBirth, address, phoneNumber, email, fullSSN);
    }
    
    
    /**
     * Constructor for CreateAdminAccount, CreateMemberAccount
     * @param firstName First name of administrator
     * @param middleName Middle name of administrator
     * @param lastName Last name of administrator
     * @param gender Gender of administrator
     * @param dateOfBirth Date of birth of administrator
     * @param address Address of administrator
     * @param phoneNumber Phone number of administrator
     * @param email Email address of administrator
     * @param ssn1 First part (before -) of social security number of administrator
     * @param ssn2 Second part (after -) of social security number of administrator
     * @param username Username of administrator
     * @param password Password of administrator
     */
    public Admin(String firstName, String middleName, String lastName, 
            String gender, Date dateOfBirth, String address, int phoneNumber, 
            String email, int ssn1, int ssn2, String username, String password) {
        super(firstName, middleName, lastName, gender, dateOfBirth, address, 
                phoneNumber, email, ssn1, ssn2, username, password);
    }
    
    
    /**
     * Constructor for AdminViewPersonalInformationController, UpdateAdminPersonalInformationPageController
     * @param firstName First name of administrator
     * @param middleName Middle name of administrator
     * @param lastName Last name of administrator
     * @param dateOfBirth Date of birth of administrator
     * @param address Address of administrator
     * @param phoneNumber Phone number of administrator
     * @param email Email address of administrator
     * @param gender Gender of administrator
     * @param ssn1 First part (before -) of social security number of administrator
     * @param ssn2 Second part (after -) of social security number of administrator
     */
    public Admin(String firstName, String middleName, String lastName, 
            Date dateOfBirth, String address, int phoneNumber, String email, 
            String gender, int ssn1, int ssn2) {
        super(firstName, middleName, lastName, dateOfBirth, address, 
                phoneNumber, email, gender, ssn1, ssn2);
    }
}