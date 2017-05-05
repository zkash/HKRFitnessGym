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
public class Announcements {
    private SimpleIntegerProperty announcementId = new SimpleIntegerProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty message = new SimpleStringProperty();
    private SimpleStringProperty position = new SimpleStringProperty();
        
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
        return position.get();
    }
    
    public void setTime(String format) {
        time.set(format);
    }

    public void setMessage(String text) {
        message.set(text);
    }  
    
    public void setMessageId(Integer mi) {
        announcementId.set(mi);
    }
    public void setPosition(String text) {
        position.set(text);
    }
    
}
