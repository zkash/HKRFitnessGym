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
    private StringProperty subscriberFullName = null;
    private StringProperty subscriberUsername = null;
    private StringProperty requestStatus = null;
    private StringProperty acceptanceStatus = null;
    
    
    public SubscriptionRequest(String packageName, Float price, Date packageStartDate, Date packageEndDate, String startTime, String endTime, Date subscriptionStartDate, Date subscriptionEndDate, int subscriptionId) {
            super(packageName, price, packageStartDate, packageEndDate, startTime, endTime, subscriptionStartDate, subscriptionEndDate, subscriptionId);
    }
    
    public String getSubscriberFullName() {
        return subscriberFullName.get();
    }
    
    public String getSubscriberUsername() {
        return subscriberUsername.get();
    }
    
    public String getRequestStatus() {
        return requestStatus.get();
    }
    
    public String getAcceptanceStatus() {
        return acceptanceStatus.get();
    }
    
 
    
    public void setSubscriberFullName(String sfn) {
        subscriberFullName = new SimpleStringProperty(sfn);
    }
    
    public void setSubscriberUsername(String sun) {
        subscriberUsername = new SimpleStringProperty(sun);
    }
    
    public void setRequestStatus(String rs) {
        requestStatus = new SimpleStringProperty(rs);
    }
    
    public void setAcceptanceStatus(String as) {
        acceptanceStatus = new SimpleStringProperty(as);
    }
    
  
}
