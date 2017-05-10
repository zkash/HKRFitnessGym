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
    @FXML Label firstNameLbl;
    @FXML Label middleNameLbl;
    @FXML Label lastNameLbl;
    @FXML Label genderLbl;
    @FXML Label dobLbl;
    @FXML Label addressLbl;
    @FXML Label phoneNumberLbl;
    @FXML Label emailLbl;
    @FXML Label ssnLbl;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private final int adminId = LoginStorage.getInstance().getId();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<Admin> admin = dbHandler.getAdminPersonalInformation(adminId);
            
            if(admin.isEmpty()) {
                helper.showDialogBox(true, "There is no such user to view personal details of");
            }
            
            firstNameLbl.setText(admin.get(0).getFirstName());
            middleNameLbl.setText(admin.get(0).getMiddleName());
            lastNameLbl.setText(admin.get(0).getLastName());
            genderLbl.setText(admin.get(0).getGender());
            dobLbl.setText(helper.convertDateToString(admin.get(0).getDateOfBirth()));
            addressLbl.setText(admin.get(0).getAddress());
            phoneNumberLbl.setText(Integer.toString(admin.get(0).getPhoneNumber()));
            emailLbl.setText(admin.get(0).getEmail());
            ssnLbl.setText(Integer.toString(admin.get(0).getSSN1()) + "-" + Integer.toString(admin.get(0).getSSN2()));
        } 
        catch (SQLException ex) {
            Logger.getLogger(AdminViewPersonalInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}