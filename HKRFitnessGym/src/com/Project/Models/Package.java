/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Models;

import java.sql.Date;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class Package {

    private String packageName = null;
    private float price = 0;
    private Date startDate = null;
    private Date endDate = null;
    private String startTime = null;
    private String endTime = null;
    private int packageId = 0;
    private String adminFullName = null;
    private int numberOfSubscriber = 0;
    
    public Package() {
        
    }
    
    public Package(String pn, float price, Date sd, Date ed, String st, String et) {
        this.packageName = pn;
        this.price = price;
        this.startDate = sd;
        this.endDate = ed;
        this.startTime = st;
        this.endTime = et;
    }
    
    //For adminViewPackages
    public Package(String pn, float price, Date sd, Date ed, String st, String et, String afn) {
        this.packageName = pn;
        this.price = price;
        this.startDate = sd;
        this.endDate = ed;
        this.startTime = st;
        this.endTime = et;
        this.adminFullName = afn;
    }

    //For AdminViewSubscriptions
    public Package(String pn) {
        this.packageName = pn;
    }
    
    //For MemberViewSubscriptions
    public Package(String pn, float price, String st, String et) {
        this.packageName = pn;
        this.price = price;
        this.startTime = st;
        this.endTime = et;
    }
    
    // Getters
    public String getPackageName() {
        return packageName;
    }

    public float getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    
    public int getPackageId() {
        return packageId;
    }
    
    public String getAdminFullName() {
        return adminFullName;
    }
    
    public int getNumberOfSubscriber() {
        return numberOfSubscriber;
    }
    
    //Setters
    public void setPackageName(String pn) {
        this.packageName = pn;
    }

    public void setPrice(float pri) {
        this.price = pri;
    }

    public void setStartDate(Date sd) {
        this.startDate = sd;
    }

    public void setEndDate(Date ed) {
        this.endDate = ed;
    }

    public void setStartTime(String st) {
        this.startTime = st;
    }
    
    public void setEndTime(String et) {
        this.endTime = et;
    }
    
    public void setAdminFullName(String afn) {
        this.adminFullName = afn;
    }
    
    public void setNumberOfSubscriber(int nos) {
        this.numberOfSubscriber = nos;
    }
    
//    // Property values
//    public StringProperty packageNameProperty() {
//        return packageName;
//    }
//    
//    public FloatProperty priceProperty() {
//        return price;
//    }
//
////    public StringProperty startDateProperty() {
////        return startDate;
////    }
////    
////    public StringProperty endDateProperty() {
////        return endDate;
////    }
//    
//    public StringProperty startTimeProperty() {
//        return startTime;
//    }
//    
//    public StringProperty endTimeProperty() {
//        return endTime;
//    }
//    
//    public IntegerProperty packageIdProperty() {
//        return packageId;
//    }
//    
//    public StringProperty adminFullNameProperty() {
//        return adminFullName;
//    }
//    
//    public IntegerProperty numberOfSubscriberProperty() {
//        return numberOfSubscriber;
//    }
}





///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.Project.Models;
//
//import java.sql.Date;
//import javafx.beans.property.FloatProperty;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleFloatProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
///**
// *
// * @author shameer
// */
//public class Package {
//
//    private StringProperty packageName = null;
//    private FloatProperty price = null;
//    private Date startDate = null;
//    private Date endDate = null;
//    private StringProperty startTime = null;
//    private StringProperty endTime = null;
//    private IntegerProperty packageId = null;
//    private StringProperty adminFullName = null;
//    private IntegerProperty numberOfSubscriber = new SimpleIntegerProperty();
//    
//    public Package() {
//        
//    }
//    
//    public Package(String pn, float price, Date sd, Date ed, String st, String et) {
//        this.packageName = new SimpleStringProperty(pn);
//        this.price = new SimpleFloatProperty(price);
//        this.startDate = sd;
//        this.endDate = ed;
//        this.startTime = new SimpleStringProperty(st);
//        this.endTime = new SimpleStringProperty(et);
//        this.packageId = null;
//        this.adminFullName = null;
//    }
//    
//    //For adminViewPackages
//    public Package(String pn, float price, Date sd, Date ed, String st, String et, String afn) {
//        this.packageName = new SimpleStringProperty(pn);
//        this.price = new SimpleFloatProperty(price);
//        this.startDate = sd;
//        this.endDate = ed;
//        this.startTime = new SimpleStringProperty(st);
//        this.endTime = new SimpleStringProperty(et);
//        this.packageId = null;
//        this.adminFullName = new SimpleStringProperty(afn);
//    }
//
//    //For AdminViewSubscriptions
//    public Package(String pn) {
//        this.packageName = new SimpleStringProperty(pn);
//    }
//    
//    //For MemberViewSubscriptions
//    public Package(String pn, float price, String st, String et) {
//        this.packageName = new SimpleStringProperty(pn);
//        this.price = new SimpleFloatProperty(price);
//        this.startTime = new SimpleStringProperty(st);
//        this.endTime = new SimpleStringProperty(et);
//    }
//    
//    // Getters
//    public String getPackageName() {
//        return packageName.get();
//    }
//
//    public float getPrice() {
//        return price.get();
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public String getStartTime() {
//        return startTime.get();
//    }
//
//    public String getEndTime() {
//        return endTime.get();
//    }
//    
//    public int getPackageId() {
//        return packageId.get();
//    }
//    
//    public String getAdminFullName() {
//        return adminFullName.get();
//    }
//    
//    public int getNumberOfSubscriber() {
//        return numberOfSubscriber.get();
//    }
//    
//    //Setters
//    public void setPackageName(String pn) {
//        packageName.set(pn);
//    }
//
//    public void setPrice(float pri) {
//        price.set(pri);
//    }
//
//    public void setStartDate(Date sd) {
//        startDate = sd;
//    }
//
//    public void setEndDate(Date ed) {
//        endDate = ed;
//    }
//
//    public void setStartTime(String st) {
//        startTime.set(st);
//    }
//    
//    public void setEndTime(String et) {
//        endTime.set(et);
//    }
//    
//    public void setAdminFullName(String afn) {
//        adminFullName.set(afn);
//    }
//    
//    public void setNumberOfSubscriber(int nos) {
//    numberOfSubscriber.set(nos);
//    }
//    
//    // Property values
//    public StringProperty packageNameProperty() {
//        return packageName;
//    }
//    
//    public FloatProperty priceProperty() {
//        return price;
//    }
//
////    public StringProperty startDateProperty() {
////        return startDate;
////    }
////    
////    public StringProperty endDateProperty() {
////        return endDate;
////    }
//    
//    public StringProperty startTimeProperty() {
//        return startTime;
//    }
//    
//    public StringProperty endTimeProperty() {
//        return endTime;
//    }
//    
//    public IntegerProperty packageIdProperty() {
//        return packageId;
//    }
//    
//    public StringProperty adminFullNameProperty() {
//        return adminFullName;
//    }
//    
//    public IntegerProperty numberOfSubscriberProperty() {
//        return numberOfSubscriber;
//    }
//}