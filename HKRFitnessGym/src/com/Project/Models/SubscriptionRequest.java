package com.Project.Models;

import java.sql.Date;

/**
 *
 * @author shameer
 */
public class SubscriptionRequest extends Subscription {
    private String memberFullName;
    private String memberUsername;
 
    /**
     * Initializer constructor
     */
    public SubscriptionRequest() {
        this.memberFullName = null;
        this.memberUsername = null;
    }
    
    
    /**
     * Constructor for getSubscriptionRequest, searchInAdminViewSubscriptionRequests
     * @param packageName Name of package
     * @param price Price of package
     * @param packageStartDate Start date of package
     * @param packageEndDate End date of package
     * @param startTime Start time of package in a day
     * @param endTime End time of package in a day
     * @param subscriptionStartDate Start date of subscription
     * @param subscriptionEndDate End date of subscription
     * @param subscriptionId Id of subscription
     */
    public SubscriptionRequest(String packageName, Float price, Date packageStartDate, Date packageEndDate, String startTime, String endTime, Date subscriptionStartDate, Date subscriptionEndDate, int subscriptionId) {
            super(packageName, price, packageStartDate, packageEndDate, startTime, endTime, subscriptionStartDate, subscriptionEndDate, subscriptionId);
    }
    
    
    /**
     * Accessor method for memberFullName
     * @return Full name of member
     */
    @Override
    public String getMemberFullName() {
        return this.memberFullName;
    }
    
    
    /**
     * Accessor method for memberUsername
     * @return Username of member
     */
    public String getSubscriberUsername() {
        return this.memberUsername;
    }
    

    /**
     * Mutator method for memberFullName
     * @param memberFullName Full name of member
     */
    @Override
    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }
    
    
    /**
     * Mutator method for memberUsername
     * @param memberUsername Username of member
     */
    @Override
    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }
}