/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Models;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author shameer
 */
public class Helper {
    private DBHandler dbHandler = new DBHandler();
    /**
     * Loads the properties file that has system email and password to handle forgot password
     * @return 
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/com/Project/Properties/hash.properties")) {
            properties.load(fis);
        }
        catch (Exception e) {
        }
        return properties;
    }
    
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
            Parent root = FXMLLoader.load(Helper.class.getResource("/com/Project/Views/" + nextScene));
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
        Parent root = FXMLLoader.load(Helper.class.getResource("/com/Project/Views/" + nextSceneLocation));
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
    
    public void putTextInPdf(PDPageContentStream contentStream, float fontSize, float tx, float ty, String text) throws IOException {
        //Begin the Content stream 
                        contentStream.beginText(); 
       
                        //Setting the font to the Content stream  
                        
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);

                        //Setting the position for the line 
             
                        contentStream.newLineAtOffset(tx, ty);

                        

                        //Adding text in the form of string 
                        contentStream.showText(text);      

                        //Ending the content stream
                        contentStream.endText();
    }
    
    public void drawRectangleInPdf(PDPageContentStream contentStream, float tx, float ty, float width, float height, Color color, String drawType) throws IOException {
        //Setting the non stroking color
        contentStream.setNonStrokingColor(color);
        
        //Drawing a rectangle
        contentStream.addRect(tx, ty, width, height);
        
        //Drawing a rectangle
        if(drawType.equals("Stroke")) {
            contentStream.stroke();
        }
        else if(drawType.equals("Fill")) {
            contentStream.fill();
        }
    }
    
    public void drawLineInPdf(PDPageContentStream contentStream, float startX, float startY, float endX, float endY, Color color, float lineWidth) throws IOException {
        contentStream.setLineWidth (lineWidth);
        contentStream.setStrokingColor(color);
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(endX, endY);
        contentStream.closeAndStroke();
    }
    
    private static Method columnToFitMethod;

        static 
        {
            try 
            {
                columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
                columnToFitMethod.setAccessible(true);
            } 
            catch (NoSuchMethodException e) {e.printStackTrace();}
        }

        public void fitColumns(TableView tableView) throws IllegalArgumentException, InvocationTargetException {
            for (Object column : tableView.getColumns()) 
            {
                try {  columnToFitMethod.invoke(tableView.getSkin(), column, -1); } 
                catch (IllegalAccessException | InvocationTargetException e) { e.printStackTrace(); }
            }
        }
        
        public String hash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            String generatedPassword;
            try {
                Properties senderProperties = loadProperties();
                System.out.println("1");
                String algorithm = senderProperties.getProperty("hashingAlgorithm");
                String salt = senderProperties.getProperty("salt");
                System.out.println("ALGORITHM " + algorithm );
                MessageDigest hashingAlgorithm = MessageDigest.getInstance(algorithm);
                System.out.println("2");
                System.out.println("3");
                String passwordAndSalt = salt + password;
                System.out.println("HASHED " + passwordAndSalt);
                        hashingAlgorithm.update(passwordAndSalt.getBytes());
                generatedPassword = bytesToString(hashingAlgorithm.digest());
                System.out.println("gen " + generatedPassword);
                return generatedPassword;
            }
            catch(NoSuchAlgorithmException ex) {
                showDialogBox(true, "Could not hash password");
                return null;
            }
        }
        
        public String bytesToString(byte[] bytes) {
            StringBuilder output = new StringBuilder();
            for(byte b : bytes) {
                output.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("OUTPIT " + output.toString());
            return output.toString();
        }
        
        public java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
            return sqlDate;
        }
        
        public java.sql.Date getCurrentDateInSqlDate() {
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            return date;
        }
        
//         public java.util.Date getCurrentTime() {
//        DateFormat sdf = new SimpleDateFormat("H:mm");
//        java.util.Date d = new java.util.Date();
//        String dateStr = sdf.format(d);
//        java.util.Date date = null;
//        try {
//           date = sdf.parse(dateStr);
//            System.out.println("Date " + date);
//        }
//        catch(ParseException e)
//        {
//        
//        }
//        return date;
        
        public boolean checkOldPasswordAndChangePassword(int id, String accountType, String enteredOldPassword, String enteredNewPassword) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
            String oldPasswordFromDB = dbHandler.getPassword(id, accountType);
        System.out.println("heresave " + oldPasswordFromDB);
        
        String hashedEnteredOldPassword = hash(enteredOldPassword);
            System.out.println("fdsad " + hashedEnteredOldPassword);
            boolean changedPassword = false;
        if (hashedEnteredOldPassword.equals(oldPasswordFromDB)) {
            
            String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
            
            if (!enteredNewPassword.matches(pwRegex)) {
                showDialogBox(true, "New password must be at least 5 characters, a digit is required");
            } else if (enteredNewPassword.equals(enteredOldPassword)) {
                showDialogBox(true, "New password must be different than old password");
            } //  Update password using DBHandler method.
            else {
                String hashedNewPassword = hash(enteredNewPassword);
                System.out.println("has " + hashedNewPassword);
                dbHandler.updatePassword(accountType, id, hashedNewPassword);
                changedPassword = true;
                showDialogBox(false, "Your password has been changed.");
            }
        }
        else {
            showDialogBox(true, "Entered old password is not the correct old password");
        }
        return changedPassword;
        }
        
        public int convertTimeToMinuteSinceMidnight(String time) {
        String[] timeDivided = time.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        int minute = Integer.parseInt(timeDivided[1]);
        int minutesSinceMidnight = (hour * 60) + minute;
        return minutesSinceMidnight;
    }
        
        public String hourFormat(java.util.Date date){
            DateFormat sdf2 = new SimpleDateFormat("HH");
            String tFourH = sdf2.format(date);
            return tFourH;
        }
        
        public String minuteFormat(java.util.Date date){
            DateFormat sdf2 = new SimpleDateFormat("mm");
            String tFourH = sdf2.format(date);
            return tFourH;
        }
        
        public java.util.Date hourFormat(String time) throws ParseException{
            DateFormat sdf = new SimpleDateFormat("HH");
            java.util.Date d = sdf.parse(time);
            return d;
        }
        
        public java.util.Date minuteFormat(String time) throws ParseException{
            DateFormat sdf = new SimpleDateFormat("mm");
            java.util.Date d = sdf.parse(time);
            return d;
        }
        
        public int toInteger(String string){
            int number = Integer.parseInt(string);
            return number;
        }
        
        public Time toSqlTime(String string) throws ParseException{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            long ms = sdf.parse(string).getTime();
            Time t = new Time(ms);
            return t;
        }
}
    
    
