package com.Project.Models;

import java.sql.Date;

/**
 *
 * @author shameer
 */
public class Member extends Person {
    private String adminFullName = null;
    
    /**
     * Constructor for MemberViewPersonalInformationController, UpdateMemberPersonalInformationPageController
     * @param firstName First name of member
     * @param middleName Middle name of member
     * @param lastName Last name of member
     * @param dateOfBirth Date of birth of member
     * @param address Address of member
     * @param phoneNumber Phone number of member
     * @param email Email address of member
     * @param gender Gender of member
     * @param ssn1 First part (before -) of social security number of member
     * @param ssn2 Second part (after -) of social security number of member
     */
    public Member(String firstName, String middleName, String lastName, 
            Date dateOfBirth, String address, int phoneNumber, String email, 
            String gender, int ssn1, int ssn2) {
        super(firstName, middleName, lastName, dateOfBirth, address, 
                phoneNumber, email, gender, ssn1, ssn2);
       
    }
    

    /**
     * Constructor for adminViewMemberAccounts, searchInAdminViewMemberAccounts
     * @param fullName Full name of member
     * @param username Username of member
     * @param gender Gender of member
     * @param dateOfBirth Date of birth of member
     * @param address Address of member
     * @param phoneNumber Phone number of member
     * @param email Email address of member
     * @param fullSSN Full social security number of member
     */
    public Member(String fullName, String username, String gender, 
            Date dateOfBirth, String address, int phoneNumber, String email, 
            String fullSSN) {
        super(fullName, username, gender, dateOfBirth, address, phoneNumber, 
                email, fullSSN);
    }
    
    
    /**
     * Constructor for Create User
     * @param firstName First name of member
     * @param middleName Middle name of member
     * @param lastName Last name of member
     * @param gender Gender of member
     * @param dateOfBirth Date of birth of member
     * @param address Address of member
     * @param phoneNumber Phone number of member
     * @param email Email address of member
     * @param ssn1 First part (before -) of social security number of member
     * @param ssn2 Second part (after -) of social security number of member
     * @param username Username of member
     * @param password Password of member
     */
    public Member(String firstName, String middleName, String lastName, 
           String gender, Date dateOfBirth, String address, int phoneNumber, 
           String email, int ssn1, int ssn2, String username, String password) {
        super(firstName, middleName, lastName, gender, dateOfBirth, address, 
                phoneNumber, email, ssn1, ssn2, username, password);
  
    }
    
    
    /**
     * Accessor method for adminFullName
     * @return 
     */
    public String getAdminFullName() {
        return adminFullName;
    }
    
    
    /**
     * Mutator method for adminFullName
     * @param adminFullName 
     */
    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }
}