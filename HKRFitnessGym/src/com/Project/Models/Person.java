package com.Project.Models;

import java.sql.Date;

/**
 *
 * @author shameer
 */
public class Person {
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String gender = null;
    private String address = null;
    private String email = null;
    private String username = null;
    private String password = null;
    private String fullName = null;
    private String fullSSN = null;
    
    private Date dateOfBirth = null;
    
    private int phoneNumber = 0;
    private int ssn1 = 0;
    private int ssn2 = 0;
    

    /**
     * Constructor for AdminViewAdminAccounts, AdminViewMemberAccounts
     * @param fullName Full name of user
     * @param username Username of user
     * @param gender Gender of user
     * @param dateOfBirth Date of birth of user
     * @param address Address of user
     * @param phoneNumber Phone number of user
     * @param email Email of user
     * @param fullSSN Full social security number of user
     */ 
    public Person(String fullName, String username, String gender, Date dateOfBirth,
            String address, int phoneNumber, String email, String fullSSN) {
        this.fullName = fullName;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullSSN = fullSSN;
    }
    
    
    /**
     * Constructor for CreateAdminAccount, CreateMemberAccount
     * @param firstName First name of user
     * @param middleName Middle name of user
     * @param lastName Last name of user
     * @param gender Gender of user
     * @param dateOfBirth Date of birth of user
     * @param address Address of user
     * @param phoneNumber Phone number of user
     * @param email Email address of user
     * @param ssn1 First part (before -) of social security number of user
     * @param ssn2 Second part (after -) of social security number of user
     * @param username Username of user
     * @param password Password of user
     */
    public Person(String firstName, String middleName, String lastName, 
            String gender, Date dateOfBirth, String address, int phoneNumber, 
            String email, int ssn1, int ssn2, String username, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.username = username;
        this.password = password;
    }
    
    
    /**
     * Constructor for AdminViewPersonalInformationController, UpdateAdminPersonalInformationPageController
     * @param firstName First name of user
     * @param middleName Middle name of user
     * @param lastName Last name of user
     * @param dateOfBirth Date of birth of user
     * @param address Address of user
     * @param phoneNumber Phone number  of user
     * @param email Email address of user
     * @param gender Gender of user
     * @param ssn1 First part (before -) of social security number of user
     * @param ssn2 Second part (after -) of social security number of user
     */
    public Person(String firstName, String middleName, String lastName, 
            Date dateOfBirth, String address, int phoneNumber, String email, 
            String gender, int ssn1, int ssn2) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ssn1 = ssn1;
        this.ssn2 = ssn2;
        this.fullSSN = Integer.toString(ssn1) + "-" + Integer.toString(ssn2);
    }

    
    /**
     * Accessor method for dateOfBirth
     * @return Date of birth of user
     */
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    
    /**
     * Accessor method for firstName
     * @return First name of user
     */
    public String getFirstName() {
        return this.firstName;
    }

    
    /**
     * Accessor method for middleName
     * @return Middle name of user
     */
    public String getMiddleName() {
        return this.middleName;
    }

    
    /**
     * Accessor method for lastName
     * @return Last name of user
     */
    public String getLastName() {
        return this.lastName;
    }

    
    /**
     * Accessor method for gender
     * @return Gender of user
     */
    public String getGender() {
        return this.gender;
    }

    
    /**
     * Accessor method for address
     * @return Address of user
     */
    public String getAddress() {
        return this.address;
    }

    
    /**
     * Accessor method for phoneNumber
     * @return Phone number of user
     */
    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    
    /**
     * Accessor method for email
     * @return Email address of user
     */
    public String getEmail() {
        return this.email;
    }

    
    /**
     * Accessor method for ssn1
     * @return First part (before -) of social security number of user
     */
    public int getSSN1() {
        return this.ssn1;
    }
    
    
    /**
     * Accessor method for ssn2
     * @return Second part (after -) of social security number of user
     */
    public int getSSN2() {
        return this.ssn2;
    }
    
    
    /**
     * Accessor method for username
     * @return Username of user
     */
    public String getUsername() {
        return this.username;
    }

    
    /**
     * Accessor method for password
     * @return Password of user
     */
    public String getPassword() {
        return this.password;
    }
    
    
    /**
     * Accessor method for fullName
     * @return Full name of user
     */
    public String getFullName() {
        return this.fullName;
    }

    
    /**
     * Accessor method for fullSSN
     * @return Full social security number of user
     */
    public String getFullSSN() {
        return this.fullSSN;
    }
    
    
    /**
     * Mutator method for dateOfBirth
     * @param dateOfBirth Date of birth of user
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    /**
     * Mutator method for firstName
     * @param firstName First name of user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /**
     * Mutator method for middleName
     * @param middleName Middle name of user
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    
    /**
     * Mutator method for lastName
     * @param lastName Last name of user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /**
     * Mutator method for gender
     * @param gender Gender of user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    /**
     * Mutator method for address
     * @param address Address of user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    
    /**
     * Mutator method for phoneNumber
     * @param phoneNumber Phone number of user
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
    /**
     * Mutator method for email
     * @param email Email address of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    /**
     * Mutator method for username
     * @param username Username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /**
     * Mutator method for password
     * @param password Password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /**
     * Mutator method for ssn1
     * @param ssn1 First part (before -) of social security number of user
     */
    public void setSSN1(int ssn1) {
        this.ssn1 = ssn1;
    }
    
    
    /**
     * Mutator method for ssn2
     * @param ssn2 Second part (after -) of social security number of user
     */
    public void setSSN2(int ssn2) {
        this.ssn2 = ssn2;
    }
    
    
    /**
     * Mutator method for fullSSN
     * @param fullSSN Full social security number of user
     */
    public void setFullSSN(String fullSSN) {
        this.fullSSN = fullSSN;
    }
}