package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent password request if it is forgotten
 *
 * @author shameer
 */
public class ForgotPasswordRequest {
    private Date date;
    private String time;
    private String code ;
    private int id;

    /**
     * Initializer constructor
     */
    public ForgotPasswordRequest() {
        this.date = null;
        this.time = null;
        this.code = null;
        this.id = 0;
    }
    
    /**
     * Accessor method for date
     * @return The date of password request
     */
    public Date getDate() {
        return this.date;
    }
    
    
    /**
     * Accessor method for time
     * @return The time of password request
     */
    public String getTime() {
        return this.time;
    }
    
    
    /**
     * Accessor method for code
     * @return The randomly generated code
     */
    public String getCode() {
        return this.code;
    }
    
    
    /**
     * Accessor method for id
     * @return The id of the user requests the password
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Mutator method for date
     * @param date The date of password request
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    /**
     * Mutator method for time
     * @param time The time of password request
     */
    public void setTime(String time) {
        this.time = time;
    }
    
    
    /**
     * Mutator method for code
     * @param code The randomly generated code
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    
    /**
     * Mutator method for id
     * @param id The id of the user requests the password
     */
    public void setId(int id) {
        this.id = id;
    }
}