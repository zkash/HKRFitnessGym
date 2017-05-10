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
import java.util.ArrayList;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author shameer
 */

public class CreateUserPageController implements Initializable {
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
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    private final AccountHelper accountHelper = new AccountHelper();
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(firstName);
        textFieldList.add(middleName);
        textFieldList.add(lastName);
        textFieldList.add(address);
        textFieldList.add(phoneNumber);
        textFieldList.add(email);
        textFieldList.add(ssn);
        textFieldList.add(username);
        textFieldList.add(password);
        
        accountHelper.changeFocus(firstName, textFieldList, invalidMsgFirstName);
        accountHelper.changeFocus(middleName, textFieldList, invalidMsgMiddleName);
        accountHelper.changeFocus(lastName, textFieldList, invalidMsgLastName);
        accountHelper.changeFocus(address, textFieldList, invalidMsgAddress);
        accountHelper.changeFocus(phoneNumber, textFieldList, invalidMsgPhoneNumber);
        accountHelper.changeFocus(email, textFieldList, invalidMsgEmail);
        accountHelper.changeFocus(ssn, textFieldList, invalidMsgSSN);
        accountHelper.changeFocus(username, textFieldList, invalidMsgUsername);
        accountHelper.changeFocus(password, textFieldList, invalidMsgPassword);
    }     

    
    /**
     * Handles the create user button click
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
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
            helper.showDialogBox(true, "Enter all data");
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
                helper.isEmpty(invalidMsgSSN.getText()) &&
                helper.isEmpty(invalidMsgUsername.getText()) &&
                helper.isEmpty(invalidMsgPassword.getText())) {
                boolean alreadyExists;
                
                if (isAdmin.isSelected()) {
                    alreadyExists = dbHandler.checkUsernameAndSSN("Admin", un, ssn1, ssn2);
                }
                else {
                    alreadyExists = dbHandler.checkUsernameAndSSN("Member", un, ssn1, ssn2);
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