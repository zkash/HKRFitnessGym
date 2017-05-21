package com.Project.Models;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Helper class to handle user account creation and update of personal information
 *
 * @author shameer
 */
public class AccountHelper {
    private final Helper helper = new Helper();
    private final DBHandler dbHandler = new DBHandler();
    
    /**
     * Sets text on label when given condition is true
     * @param condition Condition in which the text is set on label
     * @param lbl Label
     */
    public void setTextOnCondition(boolean condition, Label lbl) {
        if(condition) {
            lbl.setText("Invalid Value"); 
        }
        else {
            lbl.setText("");
        }
    }
    
    
    /**
     * Handles the change of focus in text fields in Create User page
     * @param tf Text Field
     * @param textFieldList List of text fields to handle focus on
     * @param lbl Label
     */
    public void changeFocusInCreateUser(TextField tf, ArrayList<TextField> textFieldList, Label lbl) {
        TextField firstName = textFieldList.get(0); 
        TextField middleName = textFieldList.get(1);
        TextField lastName = textFieldList.get(2);
        TextField address = textFieldList.get(3);
        TextField phoneNumber = textFieldList.get(4);
        TextField email = textFieldList.get(5);
        TextField ssn = textFieldList.get(6);
        TextField username = textFieldList.get(7);
        TextField password = textFieldList.get(8);
        lbl.setText("");
        tf.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(!newPropertyValue) {
                if (tf == firstName || tf == middleName || tf == lastName) {
                    setTextOnCondition(helper.hasDigit(tf.getText()), lbl);
                }
                else if (tf == address) {  
                    String notAddressRegex = "[0-9]+";
                    setTextOnCondition(address.getText().matches(notAddressRegex), lbl);
                }
                else if (tf == phoneNumber) {
                    setTextOnCondition(helper.hasChar(phoneNumber.getText()), lbl);
                }
                else if (tf == email) {
                    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
                    String ead = email.getText();
                    setTextOnCondition(!helper.isEmpty(ead) && !ead.matches(emailRegex), lbl);
                }
                else if (tf == ssn) {
                    String ssnRegex = "[0-9]{6}-[0-9]{4}";
                    String ssnum = ssn.getText();
                    setTextOnCondition(!helper.isEmpty(ssnum) && !ssnum.matches(ssnRegex), lbl);
                }
                else if (tf == username) {
                    String unRegex = "^[A-Za-z][A-za-z0-9]*";
                    String uname = username.getText();
                    setTextOnCondition(!helper.isEmpty(uname) && !uname.matches(unRegex), lbl);
                }
                else if (tf == password) {
                    String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
                    String pwd = password.getText();
                    setTextOnCondition(!helper.isEmpty(pwd) && !pwd.matches(pwRegex), lbl);
                }
            }
        });
    }
    
    
    /**
     * Handles the change of focus in text fields in Update Personal Information pages
     * @param tf Text Field
     * @param textFieldList List of text fields to handle focus on
     * @param lbl Label
     */
    public void changeFocusInUpdateInfo(TextField tf, ArrayList<TextField> textFieldList, Label lbl) {
        TextField firstName = textFieldList.get(0); 
        TextField middleName = textFieldList.get(1);
        TextField lastName = textFieldList.get(2);
        TextField address = textFieldList.get(3);
        TextField phoneNumber = textFieldList.get(4);
        TextField email = textFieldList.get(5);
        TextField ssn = textFieldList.get(6);
        lbl.setText("");
        tf.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(!newPropertyValue) {
                if (tf == firstName || tf == middleName || tf == lastName) {
                    setTextOnCondition(helper.hasDigit(tf.getText()), lbl);
                }
                else if (tf == address) {  
                    String notAddressRegex = "[0-9]+";
                    setTextOnCondition(address.getText().matches(notAddressRegex), lbl);
                }
                else if (tf == phoneNumber) {
                    setTextOnCondition(helper.hasChar(phoneNumber.getText()), lbl);
                }
                else if (tf == email) {
                    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
                    String ead = email.getText();
                    setTextOnCondition(!helper.isEmpty(ead) && !ead.matches(emailRegex), lbl);
                }
                else if (tf == ssn) {
                    String ssnRegex = "[0-9]{6}-[0-9]{4}";
                    String ssnum = ssn.getText();
                    setTextOnCondition(!helper.isEmpty(ssnum) && !ssnum.matches(ssnRegex), lbl);
                }
            }
        });
    }

    
    /**
     * Handles the update button click
     * @param accountType Type of account - Admin or Member
     * @param textFieldList List of text fields to handle focus on
     * @param radioButtonList List of radio buttons 
     * @param labelList List of invalid message labels
     * @param dateOfBirth Date value from DatePicker
     * @param id Id of Admin or Member
     * @param ssnOld1 First part of old or current social security number 
     * @param ssnOld2 Second part of old or current social security number
     * @return True if updated; false if not updated
     * @throws SQLException 
     */
     public boolean update(String accountType, ArrayList<TextField> textFieldList, ArrayList<RadioButton> radioButtonList, ArrayList<Label> labelList, DatePicker dateOfBirth, int id, int ssnOld1, int ssnOld2) throws SQLException {
        Label invalidMsgAllData = labelList.get(0);
        Label invalidMsgFirstName = labelList.get(1);
        Label invalidMsgMiddleName = labelList.get(2);
        Label invalidMsgLastName = labelList.get(3);
        Label invalidMsgAddress = labelList.get(4);
        Label invalidMsgPhoneNumber = labelList.get(5);
        Label invalidMsgEmail = labelList.get(6);
        Label invalidMsgSSN = labelList.get(7);
        
        invalidMsgAllData.setText("");
        TextField firstName = textFieldList.get(0); 
        TextField middleName = textFieldList.get(1);
        TextField lastName = textFieldList.get(2);
        TextField address = textFieldList.get(3);
        TextField phoneNumber = textFieldList.get(4);
        TextField email = textFieldList.get(5);
        TextField ssn = textFieldList.get(6);
        
        String fn = firstName.getText();
         
        String ln = lastName.getText();
        
        String mn;
        
        boolean updated = false;
        
        if(!helper.isEmpty(middleName.getText())) {
            mn = middleName.getText();
        }
        else {
            mn = "";
        }
        
        RadioButton genderMale = radioButtonList.get(0);
        RadioButton genderFemale = radioButtonList.get(1);
        RadioButton genderOther = radioButtonList.get(2);
        
        String gen = "";
        if(genderMale.isSelected()) {
            gen = genderMale.getText();
        }
        
        if(genderFemale.isSelected()) {
            gen = genderFemale.getText();
        }
        
        if(genderOther.isSelected()) {
            gen = genderOther.getText();
        }
        
        LocalDate dob = dateOfBirth.getValue();
        String add = address.getText();
        String pnum = phoneNumber.getText();
        String ead = email.getText();
        String ssnum = ssn.getText();
        
        if(helper.isEmpty(fn) || helper.isEmpty(ln) || helper.isEmpty(gen) || dob == null || 
                helper.isEmpty(add) || helper.isEmpty(pnum) || helper.isEmpty(ead) || 
                helper.isEmpty(ssnum)) {
            helper.showDialogBox(true, "Enter All Data");
        }
        else {
            String[] ssnParts = ssnum.split("-");
            int ssn1 = Integer.parseInt(ssnParts[0]);
            int ssn2 = Integer.parseInt(ssnParts[1]);
            int pnumber = Integer.parseInt(pnum);
            Date birthDate = Date.valueOf(dob);
                    
            if(helper.isEmpty(invalidMsgFirstName.getText()) &&
                    helper.isEmpty(invalidMsgMiddleName.getText()) &&  
                    helper.isEmpty(invalidMsgLastName.getText()) &&
                    helper.isEmpty(invalidMsgAddress.getText()) &&
                    helper.isEmpty(invalidMsgPhoneNumber.getText()) &&
                    helper.isEmpty(invalidMsgEmail.getText()) &&
                    helper.isEmpty(invalidMsgSSN.getText())) {
                if(accountType.equals("Admin")) {
                    Admin admin;
                    admin = new Admin(fn, mn, ln, birthDate, add, pnumber, ead, gen, ssn1, ssn2);
                    dbHandler.updateAdminPersonalInformation("Admin", admin, id, ssnOld1, ssnOld2);
                    updated = true;
                }
                else if(accountType.equals("Member")) {
                    Member member;
                    member = new Member(fn, mn, ln, birthDate, add, pnumber, ead, gen, ssn1, ssn2);
                    dbHandler.updateMemberPersonalInformation("Member", member, id, ssnOld1, ssnOld2);   
                    updated = true;
                }
                helper.showDialogBox(false, "Users details successfully updated");
            }
            else {
                helper.showDialogBox(true, "Could not update admin details");
            }
        }
        return updated;
     }
}