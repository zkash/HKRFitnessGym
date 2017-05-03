/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class ForgotPassword {
    private Date date;
    private StringProperty time;
    private StringProperty code;
    private IntegerProperty id;
    
    // Getters
    public Date getDate() {
        return date;
    }
    
    public String getTime() {
        return time.get();
    }
    
    public String getCode() {
        return code.get();
    }
    
    public int getId() {
        return id.get();
    }
    
    //Setters
    
    public void setDate(Date d) {
        date = d;
    }
    
    public void setTime(String t) {
        time.set(t);
    }
    
    public void setCode(String c) {
        code.set(c);
    }
    
    public void setId(int i) {
        id.set(i);
    }
}
