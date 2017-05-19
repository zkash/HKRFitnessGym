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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Helper class
 *
 * @author shameer
 */
public class Helper {
    private final DBHandler dbHandler = new DBHandler();
    
    /**
     * Loads the properties file that has system email and password to handle forgot password
     * @return Properties object
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        
        try (FileInputStream fis = new FileInputStream("src/com/Project/Properties/hash.properties")) {
            properties.load(fis);
        }
        catch (Exception ex) {
        }
        
        return properties;
    }
    
    
    /**
     * Checks if a string is empty
     * @param str String to check if it is empty
     * @return True if string is empty; false if string is not empty
     */
    public boolean isEmpty(String str) {
        return str  == null || str.trim().isEmpty();
    }
    
    
    /**
     * Checks if a string has digit
     * @param str String to check if it has digit
     * @return True if string has digit; false if string does not have digit
     */
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
    
    
    /**
     * Checks if a string has character
     * @param str String to check if it has character
     * @return True if string has character; false if string does not have character
     */
    public boolean hasChar(String str) {
        if(!str.isEmpty()) {
            char[] charArray;
            charArray = str.toCharArray();  //convert string to character array
            
            for(char ch: charArray) {
                if (Character.isLetter(ch)) {
                    return true;
                }
            }
        }
        return false;
    }
    

    /**
     * Show a warning or a confirmation dialog box 
     * @param errorCondition Error condition to determine which dialog box (warning or confirmation) to show
     * @param message Warning or confirmation message to show in the dialog box
     */
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
    

    /**
     * Converts LocalDate to SQL Date format
     * @param localDate LocalDate to convert
     * @return Date in SQL date format
     */ 
    public Date convertLocalDateToSQLDate(LocalDate localDate) {
        Date sqlDate = java.sql.Date.valueOf(localDate);
        return sqlDate;
    }
    
    
    /**
     * Clears text fields in an array
     * @param fields An array of text fields
     */
    public void clearTextField(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
    
    
    /**
     * Removes the selection of radio buttons in an array
     * @param radioButtons An array of radio buttons
     */
    public void clearRadioButton(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
    }
    
    
    /**
     * Converts 12-hour time format to 24-hour time format
     * @param timeIn12HourFormat Time in 12-hour format
     * @return Time in 24-hour format
     */
    public String convertTimeTo24HourFormat(String timeIn12HourFormat) {
        String[] timeDivided = timeIn12HourFormat.split(":");
        int hour = Integer.parseInt(timeDivided[0]);
        String minute = timeDivided[1];
        hour = hour + 12;
        String timeIn24HourFormat = Integer.toString(hour) + ":" + minute;
        return timeIn24HourFormat;
    }
    
    
    /**
     * Converts 24-hour time format to 12-hour time format
     * @param timeIn24HourFormat Time in 24-hour format
     * @return 12-hour time format array with the first element being the hour and the second member being time format - AM or PM
     */
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
    
    
    /**
     * Converts SQL date to LocalDate format
     * @param sqlDate Date in SQL date format
     * @return Date in LocalDate format
     */
    public LocalDate convertSQLDateToLocalDate(Date sqlDate) {
        LocalDate ld = sqlDate.toLocalDate();
        return ld;
    }
    
    
    /**
     * Converts SQL date to string
     * @param date Date in SQL date format
     * @return String representation of date
     */
    public String convertDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = dateFormat.format(date);
        return dateInString;
    }
    
    
    /**
     * Shows a dialog box with choice
     * @param stage Current stage
     * @param header Header text to show in the dialog box
     * @param content Content text to show in the dialog box
     * @param nextScene Name of FXML file to redirect to 
     * @throws IOException 
     */
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
    
    
    /**
     * Converts string representation of date to LocalDate format
     * @param date String representation of date
     * @return Date in LocalDate format
     */
    public LocalDate convertStringToLocalDate(String date) {
         LocalDate localDate = LocalDate.parse(date);
         return localDate;
    }
    
    
    /**
     * Gets current date in Util date format
     * @return Current date in Util date format
     */
    public java.util.Date getCurrentDate() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = new java.util.Date();
        String dateStr = sdf.format(d);
        java.util.Date date = null;
        try {
            date = sdf.parse(dateStr);
        }
        catch(ParseException ex)
        {
            showDialogBox(true, "Cannot convert string representation of date to Util date");
        }
        return date;
    }
    
    
    /**
     * Navigates to a FXML scene
     * @param event ActionEvent
     * @param nextSceneLocation Location of FXML file to navigate to
     * @throws IOException 
     */
    public void navigateScene(ActionEvent event, String nextSceneLocation) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(Helper.class.getResource("/com/Project/Views/" + nextSceneLocation));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Gets current time
     * @return Current time in LocalTime format
     */
    public LocalTime getCurrentTime() {
        return java.time.LocalTime.now(); 
    }
    
    
    /**
     * Shows a text input dialog box
     * @param title Title of the dialog box
     * @param header Header text to show the dialog box
     * @return Text Field
     */
    public Optional<String> showTextInputDialog(String title, String header) {
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle(title);
        tid.setHeaderText(header);
        Optional<String> textField = tid.showAndWait();
        return textField;
    }
    
    
    /**
     * Puts text in a PDF file
     * @param contentStream ContentStream
     * @param fontSize Size of font
     * @param tx x-coordinate
     * @param ty y-coordinate
     * @param text Text to put in PDF file
     * @throws IOException 
     */
    public void putTextInPdf(PDPageContentStream contentStream, float fontSize, float tx, float ty, String text) throws IOException {
        //Begin the Content stream 
        contentStream.beginText(); 
       
        //Set the font to the Content stream  
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);

        //Set the position for the line 
        contentStream.newLineAtOffset(tx, ty);

        //Add text in the form of string 
        contentStream.showText(text);      

        //End the content stream
        contentStream.endText();
    }
     
    
    /**
     * Draws a rectangle in PDF file
     * @param contentStream ContentStream
     * @param tx x-coordinate
     * @param ty y-coordinate
     * @param width Width of rectangle
     * @param height Height of rectangle
     * @param color Color of rectangle
     * @param drawType Drawing type - fill or stroke
     * @throws IOException 
     */
    public void drawRectangleInPdf(PDPageContentStream contentStream, float tx, float ty, float width, float height, Color color, String drawType) throws IOException {
        //Set the non-stroking color
        contentStream.setNonStrokingColor(color);
        
        //Draw a rectangle
        contentStream.addRect(tx, ty, width, height);
        
        if(drawType.equals("Stroke")) {
            contentStream.stroke();
        }
        else if(drawType.equals("Fill")) {
            contentStream.fill();
        }
    }
    
    
    /**
     * Draws a line in PDF file
     * @param contentStream ContentStream
     * @param startX x-coordinate of initial location
     * @param startY y-coordinate of initial location
     * @param endX x-coordinate of final location
     * @param endY y-coordinate of final location
     * @param color Color of line
     * @param lineWidth Width of line
     * @throws IOException 
     */
    public void drawLineInPdf(PDPageContentStream contentStream, float startX, float startY, float endX, float endY, Color color, float lineWidth) throws IOException {
        contentStream.setLineWidth (lineWidth);
        contentStream.setStrokingColor(color);
        contentStream.moveTo(startX, startY);
        contentStream.lineTo(endX, endY);
        contentStream.closeAndStroke();
    }
    
    
    /**
     * Static method to resize the columns in table to fit the content
     */
    private static Method columnToFitMethod;
        static 
        {
            try 
            {
                columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
                columnToFitMethod.setAccessible(true);
            } 
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    /**
     * Resizes the columns in table to fit the content
     * @param tableView Table to fit the content in
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void fitColumns(TableView tableView) throws IllegalArgumentException, InvocationTargetException {
        tableView.getColumns().forEach((Object column) -> {
            try {  
                columnToFitMethod.invoke(tableView.getSkin(), column, -1); 
            } 
            catch (IllegalAccessException | InvocationTargetException ex) { 
                showDialogBox(true,  "Cannot fit table columns to content because of an error");
            }
        });
    }
        
        
    /**
     * Encrypts the password using a hashing algorithm
     * @param password Password in plaintext 
     * @return Encrypted password
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String hash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String generatedPassword;
        try {
            Properties senderProperties = loadProperties();
            String algorithm = senderProperties.getProperty("hashingAlgorithm");
            String salt = senderProperties.getProperty("salt");

            String passwordAndSalt = salt + password;

            MessageDigest hashingAlgorithm = MessageDigest.getInstance(algorithm);
            hashingAlgorithm.update(passwordAndSalt.getBytes());
            generatedPassword = bytesToString(hashingAlgorithm.digest());

            return generatedPassword;
        }
        catch(NoSuchAlgorithmException ex) {
            showDialogBox(true, "Could not hash password");
            return null;
        }
    }
        
        
    /**
     * Converts bytes to string
     * @param bytes Bytes
     * @return String 
     */
    public String bytesToString(byte[] bytes) {
        StringBuilder output = new StringBuilder();
        for(byte b : bytes) {
            output.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return output.toString();
    }
        
        
    /**
     * Converts Util date to SQL date format
     * @param utilDate Date in Util date format
     * @return Date in SQL date format
     */
    public java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
        return sqlDate;
    }
        
        
    /**
     * Gets current date in SQL date format
     * @return Current date in SQL date format
     */
    public java.sql.Date getCurrentDateInSqlDate() {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        return date;
    }

        
    /**
     * Verifies old password and changes it
     * @param id Id of the user trying to change password
     * @param accountType Account type of the user trying to change password
     * @param enteredOldPassword Old password entered by the user
     * @param enteredNewPassword New password entered by the user
     * @return True if the password has been changed; false if the password has not been changed
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public boolean checkOldPasswordAndChangePassword(int id, String accountType, String enteredOldPassword, String enteredNewPassword) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String oldPasswordFromDB = dbHandler.getOldPassword(id, accountType);
        String hashedEnteredOldPassword = hash(enteredOldPassword);

        boolean changedPassword = false;

        if (hashedEnteredOldPassword.equals(oldPasswordFromDB)) {
        String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars

            if (!enteredNewPassword.matches(pwRegex)) {
                showDialogBox(true, "New password must be at least 5 characters, a digit is required");
            } 
            else if (enteredNewPassword.equals(enteredOldPassword)) {
                showDialogBox(true, "New password must be different than old password");
            } 
            else {
                String hashedNewPassword = hash(enteredNewPassword);
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
        
        
    /**
     * Converts time to number of minutes since midnight
     * @param time Time to convert
     * @return Number of minutes since midnight
     */
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
        
        public boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }    
    
    
     //TODO use above one
    /**
     * 
     * @param ld
     * @return 
     */
    public static Date toSQLDate(LocalDate ld){
        Date date = Date.valueOf(ld);
        return date;
    }
}