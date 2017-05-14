package com.Project.Models;

import java.sql.Date;

/**
 *
 * @author shameer
 */
public class Package {
    private String packageName = null;
    private String startTime = null;
    private String endTime = null;
    private String adminFullName = null;
    
    private Date startDate = null;
    private Date endDate = null;
    
    private int packageId = 0;
    private int numberOfSubscriber = 0;
    
    private float price = 0;
    
    /**
     * Default constructor
     */
    public Package() {
    }
    
    
    /**
     * Constructor for Subscription class, memberViewPackages, getPackageInfoAdmin
     * @param packageName Name of package
     * @param price Price of package
     * @param startDate Start date of package
     * @param endDate End date of package
     * @param startTime Start time of package in a day
     * @param endTime End time of package in a day
     */
    public Package(String packageName, float price, Date startDate, Date endDate, String startTime, String endTime) {
        this.packageName = packageName;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    
    /**
     * Constructor for adminViewPackages
     * @param packageName Name of package
     * @param price Price of package
     * @param startDate Start date of package
     * @param endDate End date of package
     * @param startTime Start time of package in a day
     * @param endTime End time of package in a day
     * @param adminFullName Full name of administrator who created the package
     */
    public Package(String packageName, float price, Date startDate, Date endDate, String startTime, String endTime, String adminFullName) {
        this.packageName = packageName;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.adminFullName = adminFullName;
    }

    
    /**
     * Constructor for AdminViewSubscriptions
     * @param packageName Name of package
     */
    public Package(String packageName) {
        this.packageName = packageName;
    }
    
    
    /**
     * Constructor for MemberViewSubscriptions
     * @param packageName Name of package
     * @param price Price of package
     * @param startTime Start time of package in a day
     * @param endTime End time of package in a day
     */
    public Package(String packageName, float price, String startTime, String endTime) {
        this.packageName = packageName;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    
    /**
     * Accessor method for packageName
     * @return Name of package
     */
    public String getPackageName() {
        return packageName;
    }

    
    /**
     * Accessor method for price
     * @return Price of package
     */
    public float getPrice() {
        return price;
    }

    
    /**
     * Accessor method for startDate
     * @return Start date of package
     */
    public Date getStartDate() {
        return startDate;
    }

    
    /**
     * Accessor method for endDate
     * @return End date of package
     */
    public Date getEndDate() {
        return endDate;
    }

    
    /**
     * Accessor method for startTime
     * @return Start time of package in a day
     */
    public String getStartTime() {
        return startTime;
    }

    
    /**
     * Accessor method for endTime
     * @return End time of package in a day
     */
    public String getEndTime() {
        return endTime;
    }
    
    
    /**
     * Accessor method for packageId
     * @return Id of package
     */
    public int getPackageId() {
        return packageId;
    }
    
    
    /**
     * Accessor method for adminFullName
     * @return Full name of administrator who created the package
     */
    public String getAdminFullName() {
        return adminFullName;
    }
    
    
    /**
     * Accessor method for numberOfSubscriber
     * @return Number of subscribers in a package
     */
    public int getNumberOfSubscriber() {
        return numberOfSubscriber;
    }
    
    
    /**
     * Mutator method for packageName
     * @param packageName Name of package
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    
    /**
     * Mutator method for price
     * @param price Price of package
     */
    public void setPrice(float price) {
        this.price = price;
    }

    
    /**
     * Mutator method for startDate
     * @param startDate Start date of package
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    
    /**
     * Mutator method for endDate
     * @param endDate End date of package
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    /**
     * Mutator method for startTime
     * @param startTime Start time of package in a day
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    
    /**
     * Mutator method for endTime
     * @param endTime End time of package in a day
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    
    /**
     * Mutator method for adminFullName
     * @param adminFullName Full name of administrator who created the package
     */
    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }
    
    
    /**
     * Mutator method for numberOfSubscriber
     * @param numberOfSubscriber Number of subscribers in a package
     */
    public void setNumberOfSubscriber(int numberOfSubscriber) {
        this.numberOfSubscriber = numberOfSubscriber;
    }
}