package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent subscription of package
 *
 * @author shameer
 */
public class Subscription extends Package {
    private String subscriptionStatus;
    private String declineMessage;
    private String subscriptionAdminFullName;
    private String memberFullName;
    private String memberUsername;
    
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    
    private int packageId;
    private int memberId;
    private int subscriptionId;
    
    private float offerPrice;
    
    /**
     * Initializer constructor
     */
    public Subscription() {
        this.subscriptionStatus = null;
        this.declineMessage = null;
        this.subscriptionAdminFullName = null;
        this.memberFullName = null;
        this.memberUsername = null;
        this.subscriptionStartDate = null;
        this.subscriptionEndDate = null;
        this.packageId = 0;
        this.memberId = 0;
        this.subscriptionId = 0;
        this.offerPrice = 0;
    }
        
    
    /**
     * Constructor for MemberViewSubscription
     * @param packageName Name of package
     * @param price Price of package
     * @param startTime Start time of package in a day
     * @param endTime End time of package in a day
     */
    public Subscription(String packageName, Float price, String startTime, String endTime) {
        super(packageName, price, startTime, endTime);
    }
    
    
    /**
     * Constructor for adminViewSubscription, searchInAdminViewSubscription, adminViewDeclinedSubscription, searchInAdminViewDeclinedSubscription
     * @param packageName Name of package
     */
    public Subscription(String packageName) {
        super(packageName);
    }

    
    /**
     * Construction for SubscriptionRequest
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
    public Subscription(String packageName, Float price, Date packageStartDate, Date packageEndDate, String startTime, String endTime, Date subscriptionStartDate, Date subscriptionEndDate, int subscriptionId) {
        super(packageName, price, packageStartDate, packageEndDate, startTime, endTime);
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
        this.subscriptionId = subscriptionId;

    }
    
    
    /**
     * Accessor method for subscriptionStartDate
     * @return Start date of subscription
     */
    public Date getSubscriptionStartDate() {
        return this.subscriptionStartDate;
    }
    
    
    /**
     * Accessor method for subscriptionEndDate
     * @return End date of subscription
     */
    public Date getSubscriptionEndDate() {
        return this.subscriptionEndDate;
    }
    
    
    /**
     * Accessor method for packageId
     * @return Id of package
     */
    @Override
    public int getPackageId() {
        return this.packageId;
    }
    
    
    /**
     * Accessor method for memberId
     * @return Id of member
     */
    public int getMemberId() {
        return this.memberId;
    }
    
    
    /**
     * Accessor method for subscriptionStatus
     * @return Subscription status - Active, Canceled, Expired, Requested, Declined
     */
    public String getSubscriptionStatus() {
        return this.subscriptionStatus;
    }
    
    
    /**
     * Accessor method for subscriptionId
     * @return Id of Subscription
     */
    public int getSubscriptionId() {
        return this.subscriptionId;
    }
    
    
    /**
     * Accessor method for memberFullName
     * @return Full name of member
     */
    public String getMemberFullName() {
        return this.memberFullName;
    }
    
    
    /**
     * Accessor method for memberUsername
     * @return Username of member
     */
    public String getMemberUsername() {
        return this.memberUsername;
    }
    
    
    /**
     * Accessor method for offerPrice
     * @return Offer price for package subscription request when accepted by an admin
     */
    public float getOfferPrice() {
        return this.offerPrice;
    }
    
    
    /**
     * Accessor method for declineMessage
     * @return Decline message for a package subscription request when declined by an admin
     */
    public String getDeclineMessage() {
        return this.declineMessage;
    }
    
    
    /**
     * Accessor method for subscriptionAdminFullName
     * @return Full name of admin who handled the subscription
     */
    public String getSubscriptionAdminFullName() {
        return this.subscriptionAdminFullName;
    }
    
    
    /**
     * Mutator method for subscriptionStartDate 
     * @param subscriptionStartDate Start date of subscription
     */
    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }
    
    
    /**
     * Mutator method for subscriptionEndDate
     * @param subscriptionEndDate End date of subscription
     */
    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }
    
    
    /**
     * Mutator method for packageId
     * @param packageId Id of package
     */
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
    
    
    /**
     * Mutator method for memberId 
     * @param memberId Id of member
     */
    public void setMemberId(int memberId ) {
        this.memberId = memberId ;
    }
    
    
    /**
     * Mutator method for subscriptionStatus 
     * @param subscriptionStatus Subscription status - Active, Canceled, Expired, Requested, Declined
     */
    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
    
    
    /**
     * Mutator method for subscriptionId
     * @param subscriptionId Id of subscription
     */
    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    
    
    /**
     * Mutator method for memberFullName
     * @param memberFullName Full name of member
     */
    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }
    
    
    /**
     * Mutator method for memberUsername
     * @param memberUsername Username of member
     */
    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }
    
    
    /**
     * Mutator method for offerPrice
     * @param offerPrice Offer price for package subscription request when accepted by an admin
     */
    public void setOfferPrice(float offerPrice) {
        this.offerPrice = offerPrice;
    }
    
    
    /**
     * Mutator method for declineMessage
     * @param declineMessage Decline message for a package subscription request when declined by an admin
     */
    public void setDeclineMessage(String declineMessage) {
        this.declineMessage = declineMessage;
    }
    
    
    /**
     * Mutator method for subscriptionAdminFullName
     * @param subscriptionAdminFullName Full name of admin who handled the subscription
     */
    public void setSubscriptionAdminFullName(String subscriptionAdminFullName) {
        this.subscriptionAdminFullName = subscriptionAdminFullName;
    } 
}