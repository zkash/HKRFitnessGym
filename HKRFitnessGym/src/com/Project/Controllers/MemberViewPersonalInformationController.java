/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

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
    
    private int ssn1 = 234567;
    private int ssn2 = 8901;
    private int memberId = LoginStorage.getInstance().getId();
    
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ObservableList<Member> member = dbHandler.getMemberPersonalInformation(memberId);
            if(member.size() == 0) {
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
            Logger.getLogger(AdminViewPersonalInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
