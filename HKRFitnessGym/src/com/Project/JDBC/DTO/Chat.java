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
public class Chat {
    
    private SimpleIntegerProperty messageId;
    private SimpleStringProperty time;
    private SimpleStringProperty name;
    private SimpleStringProperty message;
    
    public Chat(){
        this.messageId = new SimpleIntegerProperty();
        this.time = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.message = new SimpleStringProperty();
    }
        
    public Integer getMessageId(){
        return messageId.get();
    }
    public String getTime(){
        return time.get();
    }
    public String getName(){
        return name.get();
    }
    public String getMessage(){
        return message.get();
    }
    public void setMessageId(Integer messageId){
        this.messageId.set(messageId);
    }
    public void setTime(String time){
        this.time.set(time);
    }
    public void setName(String name){
        this.name.set(name);
    }
    public void setMessage(String message){
        this.message.set(message);
    }

}
