/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DTO;

/**
 *
 * @author Xuantong
 */
import java.sql.Date;
/**
 *
 * @author Xuantong
 */
public class Schedule {
    private Date date;
    private String openingTime;
    private String closingTime;
    private Boolean isHoliday;
    private int id;
    
    public Schedule(){
        
    }
    
    public Schedule(Date date, String openingTime, String closingTime, Boolean isHoliday) {
        this.date = date;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.isHoliday = isHoliday;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getOpeningTime() {
        return openingTime;
    }
    
    public String getClosingTime() {
        return closingTime;
    } 
    
    public Boolean getIsHoliday() {
        return isHoliday;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setOpeningTime(String ot) {
        openingTime = ot;
    }
    
    public void setClosingTime(String ct) {
        closingTime = ct;
    }
    
    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
