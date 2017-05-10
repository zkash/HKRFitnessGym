/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *
 * @author shameer
 */
public class Helper {
    
    //Check if string is empty
    public boolean isEmpty(String str) {
        if(str  == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    
    //Check if a string contains a digit
    public boolean hasDigit(String str) {
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
    public boolean hasChar(String str) {
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
    public String convertDate(LocalDate ld) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateStr = ld.format(formatter);
        System.out.println(dateStr);
        return dateStr;
    }
    
    //Dialog box
    public void showDialogBox(boolean errorCondition, String message) {
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
    
    public Date convertLocalDateToSQLDate(LocalDate ld) {
        Date date = java.sql.Date.valueOf(ld);
        return date;
    }
    
    public void clearTextField(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
    
    public void clearRadioButton(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
    }
    
    public String convertTimeTo24HourFormat(String timeIn12HourFormat) {
        String[] timeDivided = timeIn12HourFormat.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        String minute = timeDivided[1];
        hour = hour + 12;
        String timeIn24HourFormat = Integer.toString(hour) + ":" + minute;
        return timeIn24HourFormat;
    }
    
    public ArrayList<String> convertTimeTo12HourFormat(String timeIn24HourFormat) {
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
    
    public LocalDate convertSQLDateToLocalDate(Date date) {
       // LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ld = date.toLocalDate();
        return ld;
    }
    
    public String convertDateToString(Date date) {
        System.out.println("Date " + date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = dateFormat.format(date);
        System.out.println(dateInString);
        return dateInString;
    }
    
    public void showDialogBoxChoice(Stage stage, String header, String content, String nextScene) throws IOException {    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(content);
        alert.setHeaderText(header);
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == noBtn) {
            Parent root = FXMLLoader.load(Helper.class.getResource(nextScene));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();    
        }
    }
    
    public LocalDate convertStringToLocalDate(String date) {
         LocalDate localDate = LocalDate.parse(date);
         return localDate;
    }
    
    
    public java.util.Date getCurrentDate() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = new java.util.Date();
        String dateStr = sdf.format(d);
        java.util.Date date = null;
        try {
           date = sdf.parse(dateStr);
            System.out.println("Date " + date);
        }
        catch(ParseException e)
        {
        
        }
        return date;
    }
    
    public void navigateScene(ActionEvent event, String nextSceneLocation) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(Helper.class.getResource(nextSceneLocation));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public LocalTime getCurrentTime() {
        return java.time.LocalTime.now(); 
    }
    
    public static Date toSQLDate(LocalDate ld){
        Date date = Date.valueOf(ld);
        return date;
    }
    
    public static boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public Optional<String> showTextInputDialog(String title, String header) {
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle(title);
        tid.setHeaderText(header);
        Optional<String> textField = tid.showAndWait();
        return textField;
    }
}