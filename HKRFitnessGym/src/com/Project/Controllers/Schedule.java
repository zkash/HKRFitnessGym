/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author shameer
 */
public class Schedule {
    private Date date;
    private SimpleStringProperty openingTime;
    private SimpleStringProperty closingTime;
    private Boolean isHoliday;
    
    Schedule(Date date, String openingTime, String closingTime, Boolean isHoliday) {
        this.date = date;
        this.openingTime = new SimpleStringProperty(openingTime);
        this.closingTime = new SimpleStringProperty(closingTime);
        this.isHoliday = isHoliday;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getOpeningTime() {
        return openingTime.get();
    }
    
    public String getClosingTime() {
        return closingTime.get();
    } 
    
    public Boolean getIsHoliday() {
        return isHoliday;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setOpeningTime(String ot) {
        openingTime.set(ot);
    }
    
    public void setClosingTime(String ct) {
        closingTime.set(ct);
    }
    
    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }
}
