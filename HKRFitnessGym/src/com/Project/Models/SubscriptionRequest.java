/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Models;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class SubscriptionRequest extends Subscription {
    private String memberFullName = null;
    private String memberUsername = null;
 
    
    
    public SubscriptionRequest(String packageName, Float price, Date packageStartDate, Date packageEndDate, String startTime, String endTime, Date subscriptionStartDate, Date subscriptionEndDate, int subscriptionId) {
            super(packageName, price, packageStartDate, packageEndDate, startTime, endTime, subscriptionStartDate, subscriptionEndDate, subscriptionId);
    }
    
    public String getMemberFullName() {
        return memberFullName;
    }
    
    public String getSubscriberUsername() {
        return memberUsername;
    }
    


    public void setMemberFullName(String mfn) {
        this.memberFullName = mfn;
    }
    
    public void setMemberUsername(String mun) {
        this.memberUsername = mun;
    }
    

  
}
