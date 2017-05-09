/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

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
    private StringProperty memberFullName = null;
    private StringProperty memberUsername = null;
 
    
    
    public SubscriptionRequest(String packageName, Float price, Date packageStartDate, Date packageEndDate, String startTime, String endTime, Date subscriptionStartDate, Date subscriptionEndDate, int subscriptionId) {
            super(packageName, price, packageStartDate, packageEndDate, startTime, endTime, subscriptionStartDate, subscriptionEndDate, subscriptionId);
    }
    
    public String getMemberFullName() {
        return memberFullName.get();
    }
    
    public String getSubscriberUsername() {
        return memberUsername.get();
    }
    


    public void setMemberFullName(String mfn) {
        memberFullName = new SimpleStringProperty(mfn);
    }
    
    public void setMemberUsername(String mun) {
        memberUsername = new SimpleStringProperty(mun);
    }
    

  
}
