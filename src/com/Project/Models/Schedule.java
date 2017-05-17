package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent schedule of gym 
 *
 * @author Xuantong
 */
public class Schedule {
    private Date date;
    private String openingTime;
    private String closingTime;
    private boolean isHoliday;
    private int id;
    
    /**
     * Initializer constructor
     */
    public Schedule(){
        this.date = null;
        this.openingTime = null;
        this.closingTime = null;
        this.isHoliday = false;
        this.id = 0;
    }
    
    
    /**
     * Constructor to handle schedule
     * @param date Schedule date
     * @param openingTime Opening time of gym in given date
     * @param closingTime Closing time of gym in given date
     * @param isHoliday Value to denote if the given date is holiday or not
     */
    public Schedule(Date date, String openingTime, String closingTime, Boolean isHoliday) {
        this.date = date;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.isHoliday = isHoliday;
    }
    
    
    /**
     * Accessor method for date
     * @return Schedule date
     */
    public Date getDate() {
        return this.date;
    }
    
    
    /**
     * Accessor method for openingTime
     * @return Opening time of gym in given date
     */
    public String getOpeningTime() {
        return this.openingTime;
    }
    
    
    /**
     * Accessor method for closingTime
     * @return Closing time of gym in given date
     */
    public String getClosingTime() {
        return this.closingTime;
    } 
    
    
    /**
     * Accessor method for isHoliday
     * @return Value to denote if the given date is holiday or not
     */
    public Boolean getIsHoliday() {
        return this.isHoliday;
    }
    
    
    /**
     * Accessor method for id
     * @return Id of administrator who created schedule
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Mutator method for date
     * @param date Schedule date
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    /**
     * Mutator method for openingTime
     * @param openingTime Opening time of gym in given date
     */
    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }
    
    
    /**
     * Mutator method for closingTime
     * @param closingTime Closing time of gym in given date
     */
    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
    
    
    /**
     * Mutator method for isHoliday
     * @param isHoliday Value to denote if the given date is holiday or not
     */
    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }
    
    
    /**
     * Mutator method for id
     * @param id Id of administrator who created schedule
     */
    public void setId(int id) {
        this.id = id;
    }
}