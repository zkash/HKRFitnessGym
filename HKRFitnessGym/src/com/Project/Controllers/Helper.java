/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    public static void DialogBox(boolean errorCondition, String message) {
        if(errorCondition) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText(message);
            alert.setHeaderText(null);
            alert.showAndWait();  
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText(message);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
//    public static String convertDateToString(Date d) {
//        String DATE_FORMAT_NOW = "yyyy-MM-dd";
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//        String stringDate = sdf.format(date );
//        System.out.println(stringDate);
//        return stringDate;
//    }
    
    public static Date convertLocalDateToSQLDate(LocalDate ld) {
        Date date = java.sql.Date.valueOf(ld);
        return date;
    }
    
    public static void clearTextField(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
    
    public static void clearRadioButton(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
    }
    
    public static String convertTimeTo24HourFormat(String timeIn12HourFormat) {
        String[] timeDivided = timeIn12HourFormat.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        String minute = timeDivided[1];
        hour = hour + 12;
        String timeIn24HourFormat = Integer.toString(hour) + ":" + minute;
        return timeIn24HourFormat;
    }
    
    public static ArrayList<String> convertTimeTo12HourFormat(String timeIn24HourFormat) {
        String[] timeDivided = timeIn24HourFormat.split(":");
        ArrayList<String> time = new ArrayList<>();
        int hour = Integer.parseInt(timeDivided[0]);
        String minute = timeDivided[1];
        String timeState = "AM";
        if (hour > 12) {
            hour = hour - 12;
            timeState = "PM";
        }
        String timeIn12HourFormat = Integer.toString(hour) + ":" + minute;
        time.add(timeIn12HourFormat);
        time.add(timeState);
        return time;
    }
    
    public static LocalDate convertSQLDateToLocalDate(Date date) {
       // LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ld = date.toLocalDate();
        return ld;
    }
}
