/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberCreatePasswordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button createBtn;
    
    @FXML
    private TextField password1;
    
    @FXML
    private TextField password2;
    
    @FXML
    private Label invalidMsgPassword1;
    
    @FXML
    private Label invalidMsgPassword2;
    
    private List<TextField> textfields;
    private List<Label> labels;
    private List<String> validationChecks;
    
    private BooleanBinding validated;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        textfields = Arrays.asList(password1, password2);
//        labels = Arrays.asList(invalidMsgPassword1, invalidMsgPassword2);
//        validationChecks = Arrays.asList("(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$", "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$");
//    
//        //Add listeners to the textfields
//        IntStream.range(0, textfields.size()).forEach(i -> {
//            textfields.get(i).focusedProperty().addListener((observable, oldProperty, newProperty) -> {
//                if(!textfields.get(i).getText().isEmpty() && !textfields.get(i).getText().matches(validationChecks.get(i))) {
//                    labels.get(i).setText("Invalid Value");
//                }
//                else {
//                    labels.get(i).setText("");
//                }
//            });
//        });
//        
//        //Boolean binding true when textfields are filled and labels are empty
//        validated = new BooleanBinding() {
//            
//            //Bind TextProperty of labels and textfields to the boolean binding
//            {
//                super.bind(labels.stream().map(label -> label.textProperty()).toArray(Observable[]::new));
//                super.bind(textfields.stream().map(textField -> textField.textProperty()).toArray(Observable[]::new));
//            }
//            
//            @Override
//            protected boolean computeValue() {
//                //Get the value to return by checking textfields and labels
//                return textfields.stream().allMatch(textField -> !textField.getText().isEmpty()) && labels.stream().allMatch(label -> label.getText().isEmpty());
//          }
//        };
    }    
    
    public void createBtnClick(ActionEvent event) throws SQLException {
        String pwd1 = password1.getText();
        String pwd2 = password2.getText();
        String accountType = LoginStorage.getInstance().getAccountType();
        System.out.println("AT " + accountType);
        int id = LoginStorage.getInstance().getId();
        System.out.println("ID " + id);
        
        if(pwd1.equals(pwd2)) {
            System.out.println("here");
            try {
                dbHandler.updatePassword(accountType, id, pwd1);
                helper.showDialogBox(false, "Password updated");
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/LoginPage.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException | SQLException e) {
                helper.showDialogBox(true, "Cannot update password");
            }
        }
        else {
            helper.showDialogBox(true, "Both passwords do not match. Please enter new password again");
        }
        
    }
}