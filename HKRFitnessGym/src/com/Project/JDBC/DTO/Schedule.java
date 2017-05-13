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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Xuantong
 */
public class Schedule {
    private Date date;
    private SimpleStringProperty openingTime;
    private SimpleStringProperty closingTime;
    private boolean isHoliday;
    private SimpleIntegerProperty id;
    
    public Schedule(){
        this.date = null;
        this.openingTime = new SimpleStringProperty("");
        this.closingTime = new SimpleStringProperty("");
        this.isHoliday = isHoliday;
    }
    
    public Schedule(Date date, String openingTime, String closingTime, Boolean isHoliday) {
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

    /**
     * @return the id
     */
    public int getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }
    
    //property value
    public SimpleStringProperty openingTimeProperty(){
        return openingTime;
    }
    
    public SimpleStringProperty closingTimeProperty(){
        return closingTime;
    }
    
    public SimpleIntegerProperty idProperty(){
        return id;
    }
}
