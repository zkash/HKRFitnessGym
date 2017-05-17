package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent chat message communication between users
 *
 * @author KN
 */
public class Chat {
    private String time;
    private String message;
    private String senderUsername;
    
    private int messageId;
    private int senderId;
    
    private Date date;
    
    
    /**
     * Initializer constructor
     */
    public Chat() {
        this.time = null;
        this.message = null;
        this.senderUsername = null;
        this.messageId = 0;
        this.senderId = 0;
        this.date = null;   
    }
        
    
    /**
     * Accessor method for messageId
     * @return Id of text message content of chat 
     */
    public Integer getMessageId(){
        return this.messageId;
    }
    
    
    /**
     * Accessor method for date
     * @return Date in which message was posted
     */
    public Date getDate() {
        return this.date;
    }
    
    
    /**
     * Accessor method for time
     * @return Time in which message was posted
     */
    public String getTime(){
        return this.time;
    }
    
    
    /**
     * Accessor method for senderId
     * @return Id of user who sent the message
     */
    public int getSenderId(){
        return this.senderId;
    }
    
    
    /**
     * Accessor method for message
     * @return Text message content of chat 
     */
    public String getMessage(){
        return this.message;
    }
    
    
    /**
     * Accessor method for senderUsername 
     * @return Username of user who sent the message
     */
    public String getSenderUsername(){
        return this.senderUsername;
    }
    
    
    /**
     * Mutator method for messageId
     * @param messageId Id of text message content of chat 
     */
    public void setMessageId(Integer messageId){
        this.messageId = messageId;
    }
    
    
    /**
     * Mutator method for date
     * @param date Date in which message was posted
     */
    public void setDate(Date date) {
        this.date = date;
    }

    
    /**
     * Mutator method for time
     * @param time Time in which message was posted
     */
    public void setTime(String time){
        this.time = time;
    }
    
    
    /**
     * Mutator method for senderId
     * @param senderId Id of user who sent the message
     */
    public void setSenderId(int senderId){
        this.senderId = senderId;
    }
    
    
    /**
     * Mutator method for message
     * @param message Text message content of chat 
     */
    public void setMessage(String message){
        this.message = message;
    }
    
    
    /**
     * Mutator method for senderUsername 
     * @param senderUsername Username of user who sent the message
     */
    public void setSenderUsername(String senderUsername){
        this.senderUsername = senderUsername;
    }
}