/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Models;

import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KN
 */
public class Announcement {
    private SimpleIntegerProperty announcementId;
    private Date date;
    private SimpleStringProperty time;
    private SimpleStringProperty title;
    private SimpleStringProperty body;
    private String username;
    private SimpleIntegerProperty adminId;
    
    public Announcement(){
        this.announcementId = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.body = new SimpleStringProperty();
        this.time = new SimpleStringProperty();
        this.adminId = new SimpleIntegerProperty();
        this.username = null;
    }
        
    public Integer getAnnouncementId() {
        return announcementId.get();
    }
    
    public String getTime() {
        return time.get();
    }
     public String getUsername() {
        return this.username;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getBody() {
        return body.get();
    }
    public String getTitle() {
        return title.get();
    }
    public Integer getAdminId() {
        return adminId.get();
    }
    
    
    public void setTime(String time) {
        this.time.set(time);
    }
     public void setAnnouncementId(Integer announcementId) {
        this.announcementId.set(announcementId);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void setBody(String message) {
        this.body.set(message);
    }  
    
    public void setTitle(String message) {
        this.title.set(message);
    } 
    public void setUsername(String username) {
        this.username = username;
    } 
    
     public void setAdminId(Integer adminId) {
        this.adminId.set(adminId);
    } 
    
     
   
    
}
