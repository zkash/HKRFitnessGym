/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.sql.Date;
import java.time.LocalDate;

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
    
    /*public static String convertDate(LocalDate ld){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateStr = ld.format(formatter);
        System.out.println(dateStr);
        return dateStr;
    }*/
    
    //convert localDate to sql date.
    public static Date toSQLDate(LocalDate ld){
        Date date = Date.valueOf(ld);
        return date;
    }
    
    //check if is a integer
    public static boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
