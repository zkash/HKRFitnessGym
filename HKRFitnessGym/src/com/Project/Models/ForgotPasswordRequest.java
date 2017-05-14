package com.Project.Models;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shameer
 */
public class ForgotPasswordRequest {
    private Date date = null;
    private String time = null;
    private String code = null;
    private int id = 0;
    
    /**
     * Gets the date of password request
     * @return The date of password request
     */
    public Date getDate() {
        return date;
    }
    
    
    /**
     * Gets the time of password request
     * @return The time of password request
     */
    public String getTime() {
        return time;
    }
    
    
    /**
     * Gets the randomly generated code
     * @return The randomly generated code
     */
    public String getCode() {
        return code;
    }
    
    
    /**
     * Gets the id of the user requests the password
     * @return The id of the user requests the password
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Sets the date of password request
     * @param d The date of password request
     */
    public void setDate(Date d) {
        this.date = d;
    }
    
    
    /**
     * Sets the time of password request
     * @param t The time of password request
     */
    public void setTime(String t) {
        System.out.println("T " + t);
        this.time = t;
    }
    
    
    /**
     * Sets the randomly generated code
     * @param c The randomly generated code
     */
    public void setCode(String c) {
        this.code = c;
    }
    
    
    /**
     * Sets the id of the user requests the password
     * @param i The id of the user requests the password
     */
    public void setId(int i) {
        this.id = i;
    }
}



//package com.Project.Models;
//
//import java.sql.Date;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
///**
// *
// * @author shameer
// */
//public class ForgotPasswordRequest {
//    private Date date;
//    private StringProperty time;
//    private StringProperty code;
//    private IntegerProperty id;
//    
//    /**
//     * Gets the date of password request
//     * @return The date of password request
//     */
//    public Date getDate() {
//        return date;
//    }
//    
//    
//    /**
//     * Gets the time of password request
//     * @return The time of password request
//     */
//    public String getTime() {
//        return time.get();
//    }
//    
//    
//    /**
//     * Gets the randomly generated code
//     * @return The randomly generated code
//     */
//    public String getCode() {
//        return code.get();
//    }
//    
//    
//    /**
//     * Gets the id of the user requests the password
//     * @return The id of the user requests the password
//     */
//    public int getId() {
//        return id.get();
//    }
//    
//    
//    /**
//     * Sets the date of password request
//     * @param d The date of password request
//     */
//    public void setDate(Date d) {
//        date = d;
//    }
//    
//    
//    /**
//     * Sets the time of password request
//     * @param t The time of password request
//     */
//    public void setTime(String t) {
//        System.out.println("T " + t);
//        time = new SimpleStringProperty(t);
//    }
//    
//    
//    /**
//     * Sets the randomly generated code
//     * @param c The randomly generated code
//     */
//    public void setCode(String c) {
//        code = new SimpleStringProperty(c);
//    }
//    
//    
//    /**
//     * Sets the id of the user requests the password
//     * @param i The id of the user requests the password
//     */
//    public void setId(int i) {
//        id = new SimpleIntegerProperty(i);
//    }
//}