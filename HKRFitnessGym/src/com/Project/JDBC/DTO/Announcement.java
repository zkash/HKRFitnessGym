/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.JDBC.DTO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KN
 */
public class Announcement {
    private SimpleIntegerProperty announcementId;
    private SimpleStringProperty time;
    private SimpleStringProperty message;
    private SimpleStringProperty type;
    
    public Announcement(){
        this.announcementId = new SimpleIntegerProperty();
        this.message = new SimpleStringProperty();
        this.time = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
    }
        
    public Integer getMessageId() {
        return announcementId.get();
    }
    
    public String getTime() {
        return time.get();
    }
    
    public String getMessage() {
        return message.get();
    }
    public String getPosition() {
        return type.get();
    }
    
    public void setTime(String time) {
        this.time.set(time);
    }

    public void setMessage(String message) {
        this.message.set(message);
    }  
    
    public void setMessageId(Integer announcementId) {
        this.announcementId.set(announcementId);
    }
    public void setUsername(String type) {
        this.type.set(type);
    }
    
}
