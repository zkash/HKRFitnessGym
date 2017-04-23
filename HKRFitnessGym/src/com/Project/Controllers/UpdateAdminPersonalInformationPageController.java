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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
/**
 * FXML Controller class
 *
 * @author shameer
 */
public class UpdateAdminPersonalInformationPageController implements Initializable {

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
    @FXML private Label invalidMsgAllData;
    
    private boolean error;
    private String adminUsername;
    private int adminSSN = 1234567890;
    private boolean login;
    
    private List<TextField> fields;
    private List<RadioButton> radioButtons;
    @FXML
    private ToggleGroup gender;
    @FXML
    private Button createUserBtn;
    
    private List<TextField> textfields;
    private List<Label> labels;
    private List<String> validationChecks;
    private BooleanBinding validated;
    
    ObservableList<Admin> data;
    Admin admin;
    
    private int ssnOld;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        textfields = Arrays.asList(packageName, packageCost, packageStartTime, packageEndTime);
//        labels = Arrays.asList(invalidMsgPackageName, invalidMsgPackageCost, invalidMsgPackageStartTime, invalidMsgPackageEndTime);
//        validationChecks = Arrays.asList("[a-zA-Z0-9]*", "[0-9]*|([0-9]*\\.[0-9]{1,2})", "([1-9]|[1][0-2]):[0-5][0-9]", "([1-9]|[1][0-2]):[0-5][0-9]");
    
        changeFocus(firstName, invalidMsgFirstName);
        changeFocus(middleName, invalidMsgMiddleName);
        changeFocus(lastName, invalidMsgLastName);
        changeFocus(address, invalidMsgAddress);
        changeFocus(phoneNumber, invalidMsgPhoneNumber);
        changeFocus(email, invalidMsgEmail);
        changeFocus(ssn, invalidMsgSSN);
        
        
        //this.adminSSN = LoginStatus.getSSN();
        this.login = LoginStatus.getLogin();
        
        try {
            data = DBHandler.getAdminPersonalInformation(adminSSN);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAdminPersonalInformationPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        firstName.setText(data.get(0).getFirstName());
        middleName.setText(data.get(0).getMiddleName());
        lastName.setText(data.get(0).getLastName());
        dateOfBirth.setValue(Helper.convertSQLDateToLocalDate(data.get(0).getDOB())); 
        address.setText(data.get(0).getAddress());
        phoneNumber.setText(Integer.toString(data.get(0).getPhoneNumber()));
        email.setText(data.get(0).getEmail());
        int ssnumber = data.get(0).getSSN();
        int lastFourDigitsOfSSN = 0;
        int firstSixDigitsOfSSN = 0;
        int multiplier = 1;
        for (int i = 0; i < 4; i++) {
            lastFourDigitsOfSSN = lastFourDigitsOfSSN + (ssnumber % 10) * multiplier;
            multiplier *= 10;
            ssnumber = ssnumber/10;
        }
        multiplier = 1;
        for (int i = 0; i < 6; i++) {
            firstSixDigitsOfSSN = firstSixDigitsOfSSN + (ssnumber % 10) * multiplier;
            multiplier *= 10;
            ssnumber = ssnumber/10;
        }
        
        ssn.setText(Integer.toString(firstSixDigitsOfSSN) + "-" + Integer.toString(lastFourDigitsOfSSN));
        
        String gen = data.get(0).getGender();

        if(gen.equals("Male")) {
            genderMale.setSelected(true);
        }
        else if (gen.equals("Female")) {
            genderFemale.setSelected(true);
        }
        else if (gen.equals("Other")) {
            genderOther.setSelected(true);
        }
        
        ssnOld = data.get(0).getSSN();
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
        }
        else {
            lbl.setText("");
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
                }
            }  
        });
    }

    @FXML
    public void updateBtnClick(ActionEvent event) throws SQLException {
        //Clear error messages
        invalidMsgAllData.setText("");

        String fn = firstName.getText();
        String ln = lastName.getText();
        
        String mn;
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
        
        if(Helper.isEmpty(fn) || Helper.isEmpty(ln) || Helper.isEmpty(gen) || dob == null || 
                Helper.isEmpty(add) || Helper.isEmpty(pnum) || Helper.isEmpty(ead) || 
                Helper.isEmpty(ssnum)) {
            invalidMsgAllData.setText("Enter All Data");
        }
        else {
            System.out.println("herefdsdsf");
            String[] ssnParts = ssnum.split("-");
            System.out.println(ssnParts);
            String ssnumberStr = ssnParts[0] + ssnParts[1];
            System.out.println(ssnumberStr);
            int ssnumber = Integer.valueOf(ssnParts[0])*10000 + Integer.valueOf(ssnParts[1]);  //to get full SSN multiply first part by 10000 and add the second part
            int pnumber = Integer.valueOf(pnum);
            System.out.println(ssnumber);
            System.out.println(dob);
            Date birthDate = Date.valueOf(dob);
                    
            if(Helper.isEmpty(invalidMsgFirstName.getText()) &&
                Helper.isEmpty(invalidMsgMiddleName.getText()) &&  
                Helper.isEmpty(invalidMsgLastName.getText()) &&
                Helper.isEmpty(invalidMsgAddress.getText()) &&
                Helper.isEmpty(invalidMsgPhoneNumber.getText()) &&
                Helper.isEmpty(invalidMsgEmail.getText()) &&
                Helper.isEmpty(invalidMsgSSN.getText())) {
                System.out.println("reached here");
                admin = new Admin(fn, mn, ln, birthDate, add, pnumber, ead, gen, ssnumber);
                DBHandler.updatePersonalInformation("Admin", admin, ssnOld);
            }
        }        
    }
    
    public void clearTextField() {
        fields = Arrays.asList(firstName, middleName, lastName, address, phoneNumber, email, ssn);
        for (TextField field : fields) {
            field.clear();
        }
    }
    
    public void clearRadioButton() {
        radioButtons = Arrays.asList(genderMale, genderFemale, genderOther);
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(false);
        }
    }
    
//    public void updateBtnClick(ActionEvent event) {
//        
//    }
//    
//    public void deleteBtnClick(ActionEvent event) {
//        
//    }
}

