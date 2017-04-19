/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.Alert;

/**
 *
 * @author shameer
 */
public class Helper {
    
    //Check if string is empty
    public static boolean isEmpty(String str) {
        if(str  == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    
    //Check if a string contains a digit
    public static boolean hasDigit(String str) {
        if(!str.isEmpty()) {
            char[] charArray;
            charArray = str.toCharArray(); //convert string to character array
            for(char ch: charArray) {   
                if (Character.isDigit(ch)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Check if a string contains a character
    public static boolean hasChar(String str) {
        if(!str.isEmpty()) {
            char[] charArray;
            charArray = str.toCharArray();
            for(char ch: charArray) {
                if (Character.isLetter(ch)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Convert LocalDate format to SQL Date format 
    public static String convertDate(LocalDate ld) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateStr = ld.format(formatter);
        System.out.println(dateStr);
        return dateStr;
    }
    
    //Dialog box
    public static void DialogBox(boolean alreadyExists) {
        if(alreadyExists) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Data insertion failed");
            alert.setHeaderText(null);
            alert.showAndWait();  
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Data inserted in the database");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    public static String DateToString(Date d) {
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String stringDate = sdf.format(date );
        System.out.println(stringDate);
        return stringDate;
    }
}
