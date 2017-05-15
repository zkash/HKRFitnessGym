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
public class Chat {
    
    private SimpleIntegerProperty messageId;
    private SimpleStringProperty time;
    private SimpleStringProperty name;
    private SimpleStringProperty message;
    private SimpleStringProperty senderUsername;
    private SimpleStringProperty receiveUsername;
    private Date date;
    
    public Chat(){
        this.messageId = new SimpleIntegerProperty();
        this.time = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.message = new SimpleStringProperty();
        this.senderUsername = new SimpleStringProperty();
        this.receiveUsername = new SimpleStringProperty();
    }
        
    public Integer getMessageId(){
        return messageId.get();
    }
     public Date getDate() {
        return date;
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
    public String getSenderUsername(){
        return senderUsername.get();
    }
    public String getReceiveUsername(){
        return receiveUsername.get();
    }
    public void setMessageId(Integer messageId){
        this.messageId.set(messageId);
    }
     public void setDate(Date date) {
        this.date = date;
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
    public void setSenderUsername(String senderUsername){
        this.senderUsername.set(senderUsername);
    }
    public void setReceiveUsername(String receiveUsername){
        this.receiveUsername.set(receiveUsername);
    }

}
