package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent announcement in gym 
 *
 * @author KN
 */
public class Announcement {
    private String time;
    private String title;
    private String body;
    private String username;
    
    private int adminId;
    private int announcementId;
    
    private Date date;
    
    /**
     * Initializer constructor
     */
    public Announcement(){
        this.title = null;
        this.body = null;
        this.time = null;
        this.username = null;
        this.adminId = 0;
        this.announcementId = 0;   
    }
        
    
    /**
     * Accessor method for announcementId
     * @return Id of announcement
     */
    public Integer getAnnouncementId() {
        return announcementId;
    }
    
    
    /**
     * Accessor method for time
     * @return Time in which announcement was posted
     */
    public String getTime() {
        return time;
    }
    
    
    /**
     * Accessor method for username
     * @return Username of administrator who posted announcement
     */
    public String getUsername() {
        return this.username;
    }
    
    
    /**
     * Accessor method for date
     * @return Date in which announcement was posted
     */
    public Date getDate() {
        return date;
    }
    
    
    /**
     * Accessor method for body
     * @return Content message of announcement
     */
    public String getBody() {
        return body;
    }
    
    
    /**
     * Accessor method for title
     * @return Title of announcement
     */
    public String getTitle() {
        return title;
    }
    
    
    /**
     * Accessor method for adminId
     * @return Id of administrator who posted announcement
     */
    public Integer getAdminId() {
        return adminId;
    }
    
    
    /**
     * Mutator method for time
     * @param time Time in which announcement was posted
     */
    public void setTime(String time) {
        this.time = time;
    }
    
    
    /**
     * Mutator method for announcementId
     * @param announcementId Id of announcement
     */
    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }
    
    
    /**
     * Mutator method for date
     * @param date Date in which announcement was posted
     */
    public void setDate(Date date) {
        this.date = date;
    }

    
    /**
     * Mutator method for body
     * @param body Content message of announcement
     */
    public void setBody(String body) {
        this.body = body;
    }  
    
    
    /**
     * Mutator method for title
     * @param title Title of announcement
     */
    public void setTitle(String title) {
        this.title = title;
    } 
    
    
    /**
     * Mutator method for username
     * @param username Username of administrator who posted announcement
     */
    public void setUsername(String username) {
        this.username = username;
    } 
    
    
    /**
     * Mutator method for adminId
     * @param adminId Id of administrator who posted announcement
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }   
}