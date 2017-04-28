/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author shameer
 */
public class Subscription {
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private SimpleIntegerProperty packageId;
    private SimpleIntegerProperty memberId;
    
    public Subscription(Date ssd, Date sed, int packageId, int memberId) {
        this.subscriptionStartDate = ssd;
        this.subscriptionEndDate = sed;
        this.packageId = new SimpleIntegerProperty(packageId);
        this.memberId = new SimpleIntegerProperty(memberId);
    }
    
    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }
    
    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }
    
    public int getPackageId() {
        return packageId.get();
    }
    
    public int getMemberId() {
        return memberId.get();
    }
    
    public void setSubscriptionStartDate(Date ssd) {
        subscriptionStartDate = ssd;
    }
    
    public void setSubscriptionEndDate(Date sed) {
        subscriptionEndDate = sed;
    }
    
    public void setPackageId(int pid) {
        
    }
}
