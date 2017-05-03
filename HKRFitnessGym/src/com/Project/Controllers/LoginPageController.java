/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

//import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
//import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author KN
 */
public class LoginPageController implements Initializable {
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private Button exit;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox accountTypeComboBox;

    private boolean isLoggedIn;
    private String uName;
    private int ssn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> accountTypeOptions = FXCollections.observableArrayList("Admin", "Member");
        accountTypeComboBox.setItems(accountTypeOptions);
    }    

   @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String uname = userNameField.getText();
        String pwd = passwordField.getText();
        String accountType = accountTypeComboBox.getValue().toString();
       
        //boolean accountExists = DBHandler.verifyUsernamePassword(uname, pwd, accountType);
        int id = DBHandler.getId(uname, pwd, accountType);
        if(id == 0) {
            System.out.println("Account doesn't exist");
        }
        else {
            if(accountType.equals("Admin")) {
                LoginStorage.getInstance().setUsername("ADMIN");
                LoginStorage.getInstance().setId(id);
                LoginStorage.getInstance().setAccountType("Admin");
                visitAdminPage(event);
            }
            else if (accountType.equals("Member")){
                LoginStorage.getInstance().setUsername("MEMBER");
                LoginStorage.getInstance().setId(id);
                LoginStorage.getInstance().setAccountType("Member");
                visitMemberPage(event);
            }
        }
        
//        boolean accountExists = false;
//        if(!accountExists) {
//            System.out.println("Account doesnt exist");
//        }
//        else {
//            if(accountType.equals("Admin")) {
//                LoginStorage.getInstance().setUsername("ADMIN");
//                LoginStorage.getInstance().setId(1);
//                LoginStorage.getInstance().setAccountType("Admin");
//                visitAdminPage(event);
//            }
//            else if (accountType.equals("Member")){
//                LoginStorage.getInstance().setUsername("MEMBER");
//                LoginStorage.getInstance().setId(1);
//                LoginStorage.getInstance().setAccountType("Member");
//                visitMemberPage(event);
//            }
//        }
    }
        
//   @FXML
//    private void handleLogin(ActionEvent event) throws IOException {
//        String uname = userNameField.getText();
//        String pwd = passwordField.getText();
//        
//        //Check if username and password belongs to admin or members
//        //TODO remove the hard-coded and change it from the database
//        System.out.println("at " + LoginStorage.getInstance().getAccountType());
//        if(uname.equals("a") && pwd.equals("a")) {
//            int adminSSN = 1234567890; //TODO get from database;
//           // visitAdminPage(event, uname, adminSSN);
//            
////            LoginStatus ls = new LoginStatus();
////            //setLogInStatus();
////            ls.setSSN(adminSSN);, 
////            ls.setLogin(true);
//            LoginStorage.getInstance().setUsername("ADMIN");
//            LoginStorage.getInstance().setId(1);
//            LoginStorage.getInstance().setAccountType("Admin");
//            visitAdminPage(event);
//        }
//        else {
//            LoginStorage.getInstance().setUsername("MEMBER");
//            LoginStorage.getInstance().setId(1);
//            LoginStorage.getInstance().setAccountType("Member");
//            visitMemberPage(event);
//        }
//        
//    }

    @FXML // Exit the application.
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
    
    private void setLogInStatus() {
        isLoggedIn = true;
        uName = "a"; //TODO change this
    }
    
    private String getUsername() {
        return this.uName;
    }
    
    private int getSSN() {
        return this.ssn;
    }
    
//    private void visitAdminPage(ActionEvent event, String userName, int adminSSN) throws IOException {
//        Node node = (Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader();
//        Parent root = loader.load(getClass().getResource("/com/Project/FXML/AdminMainPage.fxml"));
//        AdminMainPageController adminMainPageController = loader.<AdminMainPageController>getController();
//        adminMainPageController.setSSN(adminSSN);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
        
//        AdminMainPageController adminMainPageController = (AdminMainPageController)loader.getController();
//        System.out.println(adminMainPageController);
//        adminMainPageController.setUsername(userName);
//        AdminMainPageController ampc = (AdminMainPageController)loader.getController();
//        ampc.setSSN(adminSSN);
     //   adminMainPageController.initSession(userName);
        
//        stage.show();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/com/Project/FXML/AdminMainPage.fxml"));
//        try {
//            loader.load();
//        }
//        catch(IOException e) {
//            System.out.println(e.getMessage());
//        }
//        
//       AdminMainPageController ampc = loader.getController();
//       //MenuBarAdminController mbac = loader.getController();
//       // CreateUserPageController cupc = loader.<CreateUserPageController>getController();
//       // System.out.println("hoooo");
//        //ampc.setUsername(userName);
//        //ampc.setSSN(adminSSN);
//       // mbac.setSSN(adminSSN);
//       // cupc.setAdminUsername(userName);
//        //cupc.setAdminSSN(adminSSN);
//        System.out.println("hoo");
//        //AdminMainPageController ampc = new AdminMainPageController();
//        ampc.setSSN(adminSSN);
//        
//        Pane root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
    
    private void visitAdminPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminMainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void visitMemberPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/MemberMainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    // Change scene to menu page.
    private void goToMenuPage(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/BasicTemplate.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();      
    }
    
    @FXML
    private void forgotPasswordLinkClick(ActionEvent event) throws IOException, AddressException, MessagingException {
    }
}
