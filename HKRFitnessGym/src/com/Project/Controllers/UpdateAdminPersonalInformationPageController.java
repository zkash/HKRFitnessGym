/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    private int adminSSN1 = 123456;
    private final int adminSSN2 = 7890;
    private final int adminId = LoginStorage.getInstance().getId();
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
    
    private int ssnOld1;
    private int ssnOld2;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
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
            data = dbHandler.getAdminPersonalInformation(adminId);
            if(data.isEmpty()) {
                helper.showDialogBox(true, "There is no such user to view personal details about");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAdminPersonalInformationPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(data.get(0).getFirstName());
        firstName.setText(data.get(0).getFirstName());
        middleName.setText(data.get(0).getMiddleName());
        lastName.setText(data.get(0).getLastName());
        dateOfBirth.setValue(helper.convertSQLDateToLocalDate(data.get(0).getDateOfBirth())); 
        address.setText(data.get(0).getAddress());
        phoneNumber.setText(Integer.toString(data.get(0).getPhoneNumber()));
        email.setText(data.get(0).getEmail());
        int ssnumber1 = data.get(0).getSSN1();
        int ssnumber2 = data.get(0).getSSN2();
//        int lastFourDigitsOfSSN = 0;
//        int firstSixDigitsOfSSN = 0;
//        int multiplier = 1;
//        for (int i = 0; i < 4; i++) {
//            lastFourDigitsOfSSN = lastFourDigitsOfSSN + (ssnumber % 10) * multiplier;
//            multiplier *= 10;
//            ssnumber = ssnumber/10;
//        }
//        multiplier = 1;
//        for (int i = 0; i < 6; i++) {
//            firstSixDigitsOfSSN = firstSixDigitsOfSSN + (ssnumber % 10) * multiplier;
//            multiplier *= 10;
//            ssnumber = ssnumber/10;
//        }
        
        //ssn.setText(Integer.toString(firstSixDigitsOfSSN) + "-" + Integer.toString(lastFourDigitsOfSSN));
        ssn.setText(Integer.toString(ssnumber1) + "-" + Integer.toString(ssnumber2));
        
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
        
        ssnOld1 = data.get(0).getSSN1();
        ssnOld2 = data.get(0).getSSN2();
    }     
    
    public void setAdminUsername(String uname) {
        this.adminUsername = uname;
    }
    
    public void setAdminSSN(int ssn) {
        this.adminSSN1 = ssn;
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
            System.out.println("herefdsdsf");
            String[] ssnParts = ssnum.split("-");
            System.out.println(Arrays.toString(ssnParts));
            String ssnumberStr = ssnParts[0] + ssnParts[1];
            System.out.println(ssnumberStr);
            //int ssnumber = Integer.valueOf(ssnParts[0])*10000 + Integer.valueOf(ssnParts[1]);  //to get full SSN multiply first part by 10000 and add the second part
            int ssn1 = Integer.parseInt(ssnParts[0]);
            int ssn2 = Integer.parseInt(ssnParts[1]);
            int pnumber = Integer.parseInt(pnum);
           // System.out.println(ssnumber);
            System.out.println(dob);
            Date birthDate = Date.valueOf(dob);
                    
            if(helper.isEmpty(invalidMsgFirstName.getText()) &&
                helper.isEmpty(invalidMsgMiddleName.getText()) &&  
                helper.isEmpty(invalidMsgLastName.getText()) &&
                helper.isEmpty(invalidMsgAddress.getText()) &&
                helper.isEmpty(invalidMsgPhoneNumber.getText()) &&
                helper.isEmpty(invalidMsgEmail.getText()) &&
                helper.isEmpty(invalidMsgSSN.getText())) {
                System.out.println("reached here");
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