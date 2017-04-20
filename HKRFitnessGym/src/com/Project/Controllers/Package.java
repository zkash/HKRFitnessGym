/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

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

    private final StringProperty packageName;
    private final FloatProperty price;
    private final IntegerProperty duration;
    private final StringProperty startDate;
    private final StringProperty endDate;
    private final StringProperty startTime;
   
    public Package(String pn, float price, int duration, String sd, String ed, String st) {
        this.packageName = new SimpleStringProperty(pn);
        this.price = new SimpleFloatProperty(price);
        this.duration = new SimpleIntegerProperty(duration);
        this.startDate = new SimpleStringProperty(sd);
        this.endDate = new SimpleStringProperty(ed);
        this.startTime = new SimpleStringProperty(st);
    }

    // Getters
    public String getPackageName() {
        return packageName.get();
    }

    public float getPrice() {
        return price.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public String getStartDate() {
        return startDate.get();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public String getStartTime() {
        return startTime.get();
    }

    //Setters
    public void setPackageName(String pn) {
        packageName.set(pn);
    }

    public void setPrice(float pri) {
        price.set(pri);
    }

    public void setDuration(int dur) {
        duration.set(dur);
    }

    public void setStartDate(String sd) {
        startDate.set(sd);
    }

    public void setEndDate(String ed) {
        endDate.set(ed);
    }

    public void setStartTime(String st) {
        startTime.set(st);
    }
    
    // Property values
    public StringProperty packageNameProperty() {
        return packageName;
    }
    
    public FloatProperty priceProperty() {
        return price;
    }
    
    public IntegerProperty durationProperty() {
        return duration;
    }
    
    public StringProperty startDateProperty() {
        return startDate;
    }
    
    public StringProperty endDateProperty() {
        return endDate;
    }
    
    public StringProperty startTimeProperty() {
        return startTime;
    }
}