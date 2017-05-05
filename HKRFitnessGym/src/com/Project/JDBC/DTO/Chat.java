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
    
    private final SimpleIntegerProperty messageId = new SimpleIntegerProperty();
    private final SimpleStringProperty time = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty message = new SimpleStringProperty();
        
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
    public void setMessageId(Integer msgId){
        messageId.set(msgId);
    }
    public void setTime(String format){
        time.set(format);
    }
    public void setName(String text){
        name.set(text);
    }
    public void setMessage(String text){
        message.set(text);
    }
    
}
