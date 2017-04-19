/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.Project.Controllers.Helper;
import java.sql.Date;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
/**
 * FXML Controller class
 *
 * @author shameer
 */
public class CreateUserPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField firstName;
    @FXML private TextField middleName;
    @FXML private TextField lastName;
    @FXML private TextField address;
    @FXML private TextField phoneNumber;
    @FXML private TextField email;
    @FXML private TextField ssn;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private RadioButton genderMale;
    @FXML private RadioButton genderFemale;
    @FXML private RadioButton genderOther;
    @FXML private DatePicker dateOfBirth;
    @FXML private CheckBox isAdmin;
   
    @FXML private Label invalidMsgFirstName;
    @FXML private Label invalidMsgMiddleName;
    @FXML private Label invalidMsgLastName;
    @FXML private Label invalidMsgAddress;
    @FXML private Label invalidMsgPhoneNumber;
    @FXML private Label invalidMsgEmail;
    @FXML private Label invalidMsgSSN;
    @FXML private Label invalidMsgUsername;
    @FXML private Label invalidMsgPassword;
    @FXML private Label invalidMsgAllData;
    
    private boolean error;
    private String adminUsername;
    private int adminSSN;
    private boolean login;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeFocus(firstName, invalidMsgFirstName);
        changeFocus(middleName, invalidMsgMiddleName);
        changeFocus(lastName, invalidMsgLastName);
        changeFocus(address, invalidMsgAddress);
        changeFocus(phoneNumber, invalidMsgPhoneNumber);
        changeFocus(email, invalidMsgEmail);
        changeFocus(ssn, invalidMsgSSN);
        changeFocus(username, invalidMsgUsername);
        changeFocus(password, invalidMsgPassword);
        
        this.adminSSN = loginStatus.getSSN();
        this.login = loginStatus.getLogin();
        
    }     
    
    public void setAdminUsername(String uname) {
        this.adminUsername = uname;
    }
    
    public void setAdminSSN(int ssn) {
        this.adminSSN = ssn;
    }
    
    public void setTextOnCondition(boolean condition, Label lbl) {
        
        if(condition) {
            lbl.setText("Invalid Value"); 
//            error = true;
//            System.out.println(error);
        }
        else {
            lbl.setText("");
//            error = false;
//            System.out.println(error);
        }
    }
   
    public void changeFocus(TextField tf, Label lbl) {
        lbl.setText("");
        tf.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if(!newPropertyValue) {
                    if (tf == firstName || tf == middleName || tf == lastName) {
                        setTextOnCondition(Helper.hasDigit(tf.getText()), lbl);
                    }
                    else if (tf == address) {
                        String notAddressRegex = "[0-9]+";
                        setTextOnCondition(address.getText().matches(notAddressRegex), lbl);
                    }
                    else if (tf == phoneNumber) {
                        setTextOnCondition(Helper.hasChar(phoneNumber.getText()), lbl);
                    }
                    else if (tf == email) {
                        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
                        String ead = email.getText();
                        setTextOnCondition(!Helper.isEmpty(ead) && !ead.matches(emailRegex), lbl);
                    }
                    else if (tf == ssn) {
                        String ssnRegex = "[0-9]{6}-[0-9]{4}";
                        String ssnum = ssn.getText();
                        setTextOnCondition(!Helper.isEmpty(ssnum) && !ssnum.matches(ssnRegex), lbl);
                    }
                    else if (tf == username) {
                        String unRegex = "^[A-Za-z][A-za-z0-9]*";
                        String uname = username.getText();
                        setTextOnCondition(!Helper.isEmpty(uname) && !uname.matches(unRegex), lbl);
                    }
                    else if (tf == password) {
                        String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
                        String pwd = password.getText();
                        setTextOnCondition(!Helper.isEmpty(pwd) && !pwd.matches(pwRegex), lbl);
                    }
                }
            }  
        });
    }

    public void createUserBtnClick(ActionEvent event) throws SQLException {
        //Clear error messages
        invalidMsgAllData.setText("");

        String fn = firstName.getText();
        String ln = lastName.getText();
        
        String mn = "";
        if(!Helper.isEmpty(middleName.getText())) {
            mn = middleName.getText();
        }
        else {
            mn = "";
        }
        
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
        String un = username.getText();
        String pw = password.getText();
        
        if(Helper.isEmpty(fn) || Helper.isEmpty(ln) || Helper.isEmpty(gen) || dob == null || 
                Helper.isEmpty(add) || Helper.isEmpty(pnum) || Helper.isEmpty(ead) || 
                Helper.isEmpty(ssnum) || Helper.isEmpty(un) || Helper.isEmpty(pw)) {
            invalidMsgAllData.setText("Enter All Data");
        }
        else {
            String[] ssnParts = ssnum.split("-");
            String ssnumberStr = ssnParts[0] + ssnParts[1];
            int ssnumber = Integer.parseInt(ssnumberStr);
            int pnumber = Integer.parseInt(pnum);
            System.out.println(dob);
            Date birthDate = Date.valueOf(dob);
                    
            if(Helper.isEmpty(invalidMsgFirstName.getText()) &&
                Helper.isEmpty(invalidMsgMiddleName.getText()) &&  
                Helper.isEmpty(invalidMsgLastName.getText()) &&
                Helper.isEmpty(invalidMsgAddress.getText()) &&
                Helper.isEmpty(invalidMsgPhoneNumber.getText()) &&
                Helper.isEmpty(invalidMsgEmail.getText()) &&
                Helper.isEmpty(invalidMsgSSN.getText()) &&
                Helper.isEmpty(invalidMsgUsername.getText()) &&
                Helper.isEmpty(invalidMsgPassword.getText())) {
                    if (isAdmin.isSelected()) {
                        //admin = true;
                        DBHandler.createAdminAccount(fn, mn, ln, gen, birthDate, add, pnumber, ead, ssnumber, un, pw);
                       // DBHandler.conne(birthDate, pnumber, ssnumber);
                        System.out.println("Put in Admin table in database");
                    }
                    else {
                        //admin = false;
                        System.out.println(this.adminSSN);
                        DBHandler.createMemberAccount(fn, mn, ln, gen, birthDate, add, pnumber, ead, ssnumber, un, pw, this.adminSSN);
                
                        System.out.println("Put in Member table in database");
                    }
                }        
            }
        }
}
