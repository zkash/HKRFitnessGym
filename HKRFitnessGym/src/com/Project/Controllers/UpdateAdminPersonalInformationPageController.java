package com.Project.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
public class UpdateAdminPersonalInformationPageController implements Initializable {
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
   
    @FXML private Label invalidMsgFirstName;
    @FXML private Label invalidMsgMiddleName;
    @FXML private Label invalidMsgLastName;
    @FXML private Label invalidMsgAddress;
    @FXML private Label invalidMsgPhoneNumber;
    @FXML private Label invalidMsgEmail;
    @FXML private Label invalidMsgSSN;
    @FXML private Label invalidMsgAllData;

    private List<TextField> fields;
    private List<RadioButton> radioButtons;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private int ssnOld1, ssnOld2;
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        changeFocus(firstName, invalidMsgFirstName);
//        changeFocus(middleName, invalidMsgMiddleName);
//        changeFocus(lastName, invalidMsgLastName);
//        changeFocus(address, invalidMsgAddress);
//        changeFocus(phoneNumber, invalidMsgPhoneNumber);
//        changeFocus(email, invalidMsgEmail);
//        changeFocus(ssn, invalidMsgSSN);
        
        ObservableList<Admin> data = null;

        try {
            data = dbHandler.getAdminPersonalInformation(adminId);
            if(data.isEmpty()) {
                helper.showDialogBox(true, "There is no such user to view personal details about");
            }
        } 
        catch (SQLException e) {
            helper.showDialogBox(true, "There is problem with SQL database");
        }
        
        //Set values in text fields
        firstName.setText(data.get(0).getFirstName());
        middleName.setText(data.get(0).getMiddleName());
        lastName.setText(data.get(0).getLastName());
        dateOfBirth.setValue(helper.convertSQLDateToLocalDate(data.get(0).getDateOfBirth())); 
        address.setText(data.get(0).getAddress());
        phoneNumber.setText(Integer.toString(data.get(0).getPhoneNumber()));
        email.setText(data.get(0).getEmail());
        
        ssnOld1 = data.get(0).getSSN1();
        ssnOld2 = data.get(0).getSSN2();
        ssn.setText(Integer.toString(ssnOld1) + "-" + Integer.toString(ssnOld2));
        
        String gen = data.get(0).getGender();

        switch (gen) {
            case "Male":
                genderMale.setSelected(true);
                break;
            case "Female":
                genderFemale.setSelected(true);
                break;
            case "Other":
                genderOther.setSelected(true);
                break;
            default:
                break;
        }
    }     
    
    
    public void setTextOnCondition(boolean condition, Label lbl) {
        if(condition) {
            lbl.setText("Invalid Value"); 
        }
        else {
            lbl.setText("");
        }
    }
   
    

    @FXML
    public void updateBtnClick(ActionEvent event) throws SQLException {
        //Clear error messages
        invalidMsgAllData.setText("");

        String fn = firstName.getText();
        String ln = lastName.getText();
        
        String mn;
        if(!helper.isEmpty(middleName.getText())) {
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
        
        if(helper.isEmpty(fn) || helper.isEmpty(ln) || helper.isEmpty(gen) || dob == null || 
                helper.isEmpty(add) || helper.isEmpty(pnum) || helper.isEmpty(ead) || 
                helper.isEmpty(ssnum)) {
            invalidMsgAllData.setText("Enter All Data");
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
                Admin admin;
                admin = new Admin(fn, mn, ln, birthDate, add, pnumber, ead, gen, ssn1, ssn2);
                dbHandler.updateAdminPersonalInformation("Admin", admin, ssnOld1, ssnOld2);
                helper.showDialogBox(false, "Admin details successfully updated");
            }
            else {
                helper.showDialogBox(true, "Could not update admin details");
            }
        }        
    }
    
    public void clearTextField() {
        fields = Arrays.asList(firstName, middleName, lastName, address, phoneNumber, email, ssn);
        fields.forEach((field) -> {
            field.clear();
        });
    }
    
    public void clearRadioButton() {
        radioButtons = Arrays.asList(genderMale, genderFemale, genderOther);
        radioButtons.forEach((radioButton) -> {
            radioButton.setSelected(false);
        });
    }
    
//    public void updateBtnClick(ActionEvent event) {
//        
//    }
//    
//    public void deleteBtnClick(ActionEvent event) {
//        
//    }
}