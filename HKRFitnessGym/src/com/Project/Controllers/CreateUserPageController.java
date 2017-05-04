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
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
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
  //  private int adminId = 1; //ToDO change this
    private int adminId = LoginStorage.getInstance().getId();
    private boolean login;
    
    
    @FXML
    private ToggleGroup gender;
    @FXML
    private Button createUserBtn;
    
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();
    
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
        
//        firstName.setText("John");
//        middleName.setText("James");
//        lastName.setText("Doerr");
//        address.setText("CA");
//        phoneNumber.setText("3423421");
//        email.setText("john@johndoerr.com");
//        ssn.setText("441233-1324");
//        username.setText("johnd");
//        password.setText("johnd1");
//        this.adminSSN = LoginStatus.getSSN();
//        this.login = LoginStatus.getLogin();
  

    }     
    
//    public void setAdminUsername(String uname) {
//        this.adminUsername = uname;
//    }
//    
//    public void setAdminSSN() {
//        this.adminSSN = 1234567890;
//    }
    
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
            }  
        });
    }

    @FXML
    public void createUserBtnClick(ActionEvent event) throws SQLException, IOException {
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
        String un = username.getText();
        String pw = password.getText();
        
        if(helper.isEmpty(fn) || helper.isEmpty(ln) || helper.isEmpty(gen) || dob == null || 
                helper.isEmpty(add) || helper.isEmpty(pnum) || helper.isEmpty(ead) || 
                helper.isEmpty(ssnum) || helper.isEmpty(un) || helper.isEmpty(pw)) {
            //invalidMsgAllData.setText("Enter All Data");
            helper.showDialogBox(true, "Enter all data");
        }
        else {
            String[] ssnParts = ssnum.split("-");
            System.out.println(ssnParts[0]);
            System.out.println(ssnParts[1]);
            String ssnumberStr = ssnParts[0] + ssnParts[1];
            System.out.println(ssnumberStr);
            int ssn1 = Integer.parseInt(ssnParts[0]);
            int ssn2 = Integer.parseInt(ssnParts[1]);
            //System.out.println("P1 " + p1);
            //System.out.println("P2 " + p2);
            //int ssnumber = p1*10000 + p2;
           // int ssnumber = Integer.valueOf(ssnParts[0])*10000 + Integer.valueOf(ssnParts[1]);  //to get full SSN multiply first part by 10000 and add the second part
            int pnumber = Integer.parseInt(pnum);
            
            //int ssnumber = Integer.parseInt(ssnumberStr);
          //  System.out.println(ssnumber);
            System.out.println(dob);
            
            Date birthDate = Date.valueOf(dob);
            System.out.println("DATE D " + birthDate.getClass().getName());
                    
            if(helper.isEmpty(invalidMsgFirstName.getText()) &&
                helper.isEmpty(invalidMsgMiddleName.getText()) &&  
                helper.isEmpty(invalidMsgLastName.getText()) &&
                helper.isEmpty(invalidMsgAddress.getText()) &&
                helper.isEmpty(invalidMsgPhoneNumber.getText()) &&
                helper.isEmpty(invalidMsgEmail.getText()) &&
                helper.isEmpty(invalidMsgSSN.getText()) &&
                helper.isEmpty(invalidMsgUsername.getText()) &&
                helper.isEmpty(invalidMsgPassword.getText())) {
                System.out.println("reached here");
                boolean alreadyExists;
                System.out.println(isAdmin.isSelected());
                if (isAdmin.isSelected()) {
                    alreadyExists = dbHandler.checkUsernameAndSSN("Admin", un, ssn1, ssn2);
                }
                else {
                    System.out.println("hell");
                    alreadyExists = dbHandler.checkUsernameAndSSN("Member", un, ssn1, ssn2);
                    System.out.println(alreadyExists);
                }
                
                if(alreadyExists) {
                    helper.showDialogBox(alreadyExists, "User already exists");
                }
                else {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    if (isAdmin.isSelected()) {
                        Admin admin = new Admin(fn,mn,ln, gen, birthDate, add, pnumber, ead, ssn1, ssn2, un, pw);
                        dbHandler.createAdminAccount(admin);
                        helper.showDialogBoxChoice(stage, "User account successfully created", "Do you want to create another account?", "/com/Project/FXML/AdminViewAdminAccounts.fxml");
                
                    }
                    else {
                        Member member = new Member(fn,mn,ln, gen, birthDate, add, pnumber, ead, ssn1, ssn2, un, pw);
                        dbHandler.createMemberAccount(member, adminId);
                        helper.showDialogBoxChoice(stage, "User account successfully created", "Do you want to create another account?", "/com/Project/FXML/AdminViewMemberAccounts.fxml");
                    }
                    helper.clearTextField(firstName, middleName, lastName, address, phoneNumber, email, ssn, username, password);
                    helper.clearRadioButton(genderMale, genderFemale, genderOther);
                    dateOfBirth.getEditor().clear();
                    isAdmin.setSelected(false);
                    } 
            }
        }        
    }
    
    
}

