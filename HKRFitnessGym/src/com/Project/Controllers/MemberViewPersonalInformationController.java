package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.Person;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
    
    private final int memberId = LoginStorage.getInstance().getId();
    private final String accountType = LoginStorage.getInstance().getAccountType();
    
     /**
     * Initializes the controller class.
     * @param url Uniform Resource Locator
     * @param rb Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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
        } 
        catch (SQLException ex) {
            helper.showDialogBox(true, "Could not fetch data from database because of an error");
        }
    }       
}