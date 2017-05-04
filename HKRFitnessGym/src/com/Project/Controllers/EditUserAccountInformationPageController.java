/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class EditUserAccountInformationPageController implements Initializable {

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
    
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }      
    
    public void updateUserInfoBtnClick(ActionEvent event) {
        //Clear error messages
        invalidMsgFirstName.setText("");
        invalidMsgMiddleName.setText("");
        invalidMsgLastName.setText("");
        invalidMsgAddress.setText("");
        invalidMsgPhoneNumber.setText("");
        invalidMsgEmail.setText("");
        invalidMsgSSN.setText("");
        invalidMsgUsername.setText("");
        invalidMsgPassword.setText("");
        
        String fn = firstName.getText();
        
        String mn, gen = "";
        if(middleName.getText() == null || middleName.getText().trim().isEmpty()) {
            mn = "";
        }
        else {
            mn = middleName.getText(); 
        }
        
        String ln = lastName.getText();
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
    
        if(helper.isEmpty(fn) || helper.isEmpty(ln) || helper.isEmpty(gen) || dob == null || 
                helper.isEmpty(pnum) || helper.isEmpty(ead) || helper.isEmpty(ssnum) || 
                helper.isEmpty(un) || helper.isEmpty(pw)) {
            invalidMsgAllData.setText("Enter All Data");
        }
        else {
            if(helper.hasDigit(fn)) {
                invalidMsgFirstName.setText("Invalid Value");
            }
            
            if(!helper.isEmpty(mn)) {
                if(helper.hasDigit(mn)) {
                    invalidMsgMiddleName.setText("Invalid Value");
                }
            }
            
            if(helper.hasDigit(ln)) {
                invalidMsgFirstName.setText("Invalid Value");
            }
            
            String notAddRegex = "[0-9]+";
            if(add.matches(notAddRegex)) {
                invalidMsgAddress.setText("Invalid Value");
            }
            System.out.println(pnum.length());
            if(helper.hasChar(pnum) && pnum.length() < 5) {
                invalidMsgPhoneNumber.setText("Invalid Value");
            }
            
           String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(.+[a-zA-Z0-9-.]+)+$";
            if(!ead.matches(emailRegex)) {
                invalidMsgEmail.setText("Invalid Value");
            }
            
            String ssnRegex = "[0-9]{6}-[0-9]{4}";
            if(!ssnum.matches(ssnRegex)) {
                invalidMsgSSN.setText("Invalid Value");
            }
            
            String unRegex = "^[A-Za-z][A-za-z0-9]*";
            if(!un.matches(unRegex)) {
                invalidMsgUsername.setText("Invalid Value");
            }
            
            String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
            if(!pw.matches(pwRegex)) {
                invalidMsgPassword.setText("Invalid Value");
            }
        }
    }
}