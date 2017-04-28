/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author shameer
 */
public class Subscription extends Package {
    private Date subscriptionStartDate = null;
    private Date subscriptionEndDate = null;
    private IntegerProperty packageId = null;
    private IntegerProperty memberId = null;
    
    //public Subscription(Date ssd, Date sed, int packageId, int memberId) {
        public Subscription() {
//        this.subscriptionStartDate = ssd;
//        this.subscriptionEndDate = sed;
//        this.packageId = new SimpleIntegerProperty(packageId);
//        this.memberId = new SimpleIntegerProperty(memberId);
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
        System.out.println(" outside2 " + ssd);
        subscriptionStartDate = ssd;
        System.out.println(" outside ");
    }
    
    public void setSubscriptionEndDate(Date sed) {
        System.out.println(" inside2 " + sed);
        subscriptionEndDate = sed;
        System.out.println(" inside2 ");
    }
    
    public void setPackageId(int pid) {
        System.out.println("reached inside" + pid);
        packageId = new SimpleIntegerProperty(pid);
        System.out.println("reached inside2 " + pid);
        
        System.out.println("reached inside3332 ");
    }
    
    public void setMemberId(int mid) {
        memberId = new SimpleIntegerProperty(mid);
    }
}
