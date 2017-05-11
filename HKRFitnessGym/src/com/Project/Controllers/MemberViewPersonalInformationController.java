/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.Person;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberViewPersonalInformationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML Label firstNameLbl;
    @FXML Label middleNameLbl;
    @FXML Label lastNameLbl;
    @FXML Label genderLbl;
    @FXML Label dobLbl;
    @FXML Label addressLbl;
    @FXML Label phoneNumberLbl;
    @FXML Label emailLbl;
    @FXML Label ssnLbl;
    
    private final int ssn1 = 234567;
    private final int ssn2 = 8901;
    private final int memberId = LoginStorage.getInstance().getId();
    private final String accountType = LoginStorage.getInstance().getAccountType();
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // ObservableList<Member> member = dbHandler.getMemberPersonalInformation(memberId);
            ObservableList<Person> member = dbHandler.getPersonalInformation(accountType, memberId);
            if(member.isEmpty()) {
                helper.showDialogBox(true, "There is no such user to view personal details about");
            }
            firstNameLbl.setText(member.get(0).getFirstName());
            middleNameLbl.setText(member.get(0).getMiddleName());
            lastNameLbl.setText(member.get(0).getLastName());
            genderLbl.setText(member.get(0).getGender());
            dobLbl.setText(helper.convertDateToString(member.get(0).getDateOfBirth()));
            addressLbl.setText(member.get(0).getAddress());
            phoneNumberLbl.setText(Integer.toString(member.get(0).getPhoneNumber()));
            emailLbl.setText(member.get(0).getEmail());
            ssnLbl.setText(Integer.toString(member.get(0).getSSN1()) + "-" + Integer.toString(member.get(0).getSSN2()));
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewPersonalInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
}