package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 *
 * @author shameer
 */
public class AdminChangePasswordController implements Initializable {
    @FXML private Label errorMessage;
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;

    private int id = LoginStorage.getInstance().getId();
    private String accountType = LoginStorage.getInstance().getAccountType();
    private DBHandler dbHandler = new DBHandler();
    private Helper helper = new Helper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void savePassword(ActionEvent event) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        
        String enteredOldPassword = oldPassword.getText();
        String enteredNewPassword = newPassword.getText();
        boolean changedPassword = helper.checkOldPasswordAndChangePassword(id, accountType, enteredOldPassword, enteredNewPassword);
        if(changedPassword) {
            helper.navigateScene(event, "AdminMainPage.fxml");
        }

    }

//    @FXML
//    private void goToMemberMainPage(ActionEvent event) throws IOException {
//      Node node = (Node) event.getSource();
//      Stage stage = (Stage) node.getScene().getWindow();
//      Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberMainPage.fxml"));
//
//       // if old password is belong to the logged user.
//        String oldPass = "";
//        for (Person person : DBHandler.getMemberList("SELECT * FROM person"
//                + " WHERE idPerson =" + DBHandler.getLoggedUserId())) {
//            oldPass = person.getPassword();
//        }
//        if (oldPassword.getText().equals(oldPass)) {
//            // If password length is correct.
//            if (newPassword.getText().length() < 5 || newPassword.getText().length() >= 10) {
//                errorMessage.setText("New password must be between 6 and 10 characters.");
//            }
//            else if (newPassword.getText().equals(oldPass)) {
//                errorMessage.setText("New password must be different than old password");
//            }
//            // Update password using DBHandler method.
//            else {
//                DBHandler.updatePassword(newPassword.getText());
//                errorMessage.setText("Your password has been changed.");
//            }
//        }
//        // If old password is incorrect error message will occure.
//        else {
//            errorMessage.setText("Old password is incorrect.");
//        }
//    }
}
