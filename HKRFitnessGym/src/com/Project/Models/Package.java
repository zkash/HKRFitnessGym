package com.Project.Models;

import java.sql.Date;

/**
 * Class to represent package offered by gym 
 *
 * @author shameer
 */
public class Package {
    private String packageName;
    private String startTime;
    private String endTime;
    private String adminFullName;
    
    private Date startDate;
    private Date endDate;
    
    private int packageId;
    private int numberOfSubscriber;
    
    private float price;
    
    /**
     * Initializer constructor
     */
    public Package() {
        this.packageName = null;
        this.startTime = null;
        this.endTime = null;
        this.adminFullName = null;
        this.startDate = null;
        this.endDate = null;
        this.packageId = 0;
        this.numberOfSubscriber = 0;
        this.price = 0;
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
        return this.packageName;
    }

    
    /**
     * Accessor method for price
     * @return Price of package
     */
    public float getPrice() {
        return this.price;
    }

    
    /**
     * Accessor method for startDate
     * @return Start date of package
     */
    public Date getStartDate() {
        return this.startDate;
    }

    
    /**
     * Accessor method for endDate
     * @return End date of package
     */
    public Date getEndDate() {
        return this.endDate;
    }

    
    /**
     * Accessor method for startTime
     * @return Start time of package in a day
     */
    public String getStartTime() {
        return this.startTime;
    }

    
    /**
     * Accessor method for endTime
     * @return End time of package in a day
     */
    public String getEndTime() {
        return this.endTime;
    }
    
    
    /**
     * Accessor method for packageId
     * @return Id of package
     */
    public int getPackageId() {
        return this.packageId;
    }
    
    
    /**
     * Accessor method for adminFullName
     * @return Full name of administrator who created the package
     */
    public String getAdminFullName() {
        return this.adminFullName;
    }
    
    
    /**
     * Accessor method for numberOfSubscriber
     * @return Number of subscribers in a package
     */
    public int getNumberOfSubscriber() {
        return this.numberOfSubscriber;
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