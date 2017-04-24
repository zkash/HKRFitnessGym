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
public class AdminViewPersonalInformationController implements Initializable {

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
    @FXML Label usernameLbl;
    @FXML Label passwordLbl;
    
    private int ssn1 = 123456;
    private int ssn2 = 7890;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ObservableList<Admin> admin = DBHandler.getAdminPersonalInformation(ssn1, ssn2);
            firstNameLbl.setText(admin.get(0).getFirstName());
            middleNameLbl.setText(admin.get(0).getMiddleName());
            lastNameLbl.setText(admin.get(0).getLastName());
            genderLbl.setText(admin.get(0).getGender());
            dobLbl.setText(Helper.convertDateToString(admin.get(0).getDOB()));
            addressLbl.setText(admin.get(0).getAddress());
            phoneNumberLbl.setText(Integer.toString(admin.get(0).getPhoneNumber()));
            emailLbl.setText(admin.get(0).getEmail());
            ssnLbl.setText(Integer.toString(admin.get(0).getSSN1()) + "-" + Integer.toString(admin.get(0).getSSN2()));
            usernameLbl.setText(admin.get(0).getUsername());
            passwordLbl.setText(admin.get(0).getPassword());
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewPersonalInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
