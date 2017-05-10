package com.Project.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class UpdateMemberPersonalInformationPageController implements Initializable {
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
    
    private ArrayList<TextField> textFieldList;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    private final AccountHelper accountHelper = new AccountHelper();
    
    private int ssnOld1, ssnOld2;

    private final int memberId = LoginStorage.getInstance().getId();
    private final String accountType = LoginStorage.getInstance().getAccountType();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldList = new ArrayList<>();
        textFieldList.add(firstName);
        textFieldList.add(middleName);
        textFieldList.add(lastName);
        textFieldList.add(address);
        textFieldList.add(phoneNumber);
        textFieldList.add(email);
        textFieldList.add(ssn);
        
        accountHelper.changeFocusInUpdateInfo(firstName, textFieldList, invalidMsgFirstName);
        accountHelper.changeFocusInUpdateInfo(middleName, textFieldList, invalidMsgMiddleName);
        accountHelper.changeFocusInUpdateInfo(lastName, textFieldList, invalidMsgLastName);
        accountHelper.changeFocusInUpdateInfo(address, textFieldList, invalidMsgAddress);
        accountHelper.changeFocusInUpdateInfo(phoneNumber, textFieldList, invalidMsgPhoneNumber);
        accountHelper.changeFocusInUpdateInfo(email, textFieldList, invalidMsgEmail);
        accountHelper.changeFocusInUpdateInfo(ssn, textFieldList, invalidMsgSSN);
        
        ObservableList<Member> data = null;

        try {
            data = dbHandler.getMemberPersonalInformation(memberId);
            if(data.isEmpty()) {
                helper.showDialogBox(true, "There is no such user to view personal details about");
            }
        } catch (SQLException ex) {
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
   
    /**
     * Handles update button click
     * @param event
     * @throws SQLException 
     */
    @FXML
    public void updateBtnClick(ActionEvent event) throws SQLException {
        ArrayList<RadioButton> radioButtonList = new ArrayList<>();
        radioButtonList.add(genderMale);
        radioButtonList.add(genderFemale);
        radioButtonList.add(genderOther);

        ArrayList<Label> labelList = new ArrayList<>();
        labelList.add(invalidMsgAllData);
        labelList.add(invalidMsgFirstName);
        labelList.add(invalidMsgMiddleName);
        labelList.add(invalidMsgLastName);
        labelList.add(invalidMsgAddress);
        labelList.add(invalidMsgPhoneNumber);
        labelList.add(invalidMsgEmail);
        labelList.add(invalidMsgSSN);
        
        accountHelper.updateBtnClick(accountType, textFieldList, radioButtonList, labelList, dateOfBirth, memberId, ssnOld1, ssnOld2);
    }
}