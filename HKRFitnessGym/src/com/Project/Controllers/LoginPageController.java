/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

//import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TextInputDialog;
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
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ComboBox accountTypeComboBox;

    private boolean isLoggedIn;
    private String uName;
    private int ssn;
    
    
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> accountTypeOptions = FXCollections.observableArrayList("Admin", "Member");
        accountTypeComboBox.setItems(accountTypeOptions);
        Platform.runLater(new Runnable() {
        @Override
        public void run() {
            usernameTextField.requestFocus();
        }
    });
    }    

   private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/com/Project/Models/hkrFitnessGymForgotPassword.properties")) {
            properties.load(fis);
        }
        catch (Exception e) {
        }
        return properties;
    }
   
   @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        String uname = usernameTextField.getText();
        String pwd = passwordTextField.getText();
        if(!helper.isEmpty(uname) && !helper.isEmpty(pwd) && accountTypeComboBox.getValue() != null) {
            String accountType = accountTypeComboBox.getValue().toString();
            int id;
            boolean usernameValid = dbHandler.verifyUsername(uname, accountType);
            if(!usernameValid) {
                helper.showDialogBox(true, "Account doesn't exists");
            }
            else {
            
                id = dbHandler.getId(uname, pwd, accountType);
                if(id == 0) {
                    helper.showDialogBox(true, "Wrong Password");

                }
                else {
                    if(accountType.equals("Admin")) {
                        LoginStorage.getInstance().setUsername(uname);
                        LoginStorage.getInstance().setId(id);
                        LoginStorage.getInstance().setAccountType("Admin");
                        visitAdminPage(event);
                    }
                    else if (accountType.equals("Member")){
                        LoginStorage.getInstance().setUsername(uname);
                        LoginStorage.getInstance().setId(id);
                        LoginStorage.getInstance().setAccountType("Member");
                        visitMemberPage(event);
                    }
                }
            }
            
        }
        else {
            helper.showDialogBox(true, "Enter username and password and select an account type before trying to log in");
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
//        String uname = usernameTextField.getText();
//        String pwd = passwordTextField.getText();
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
    private void forgotPasswordLinkClick(ActionEvent event) throws IOException, AddressException, MessagingException, SQLException {
        String username = usernameTextField.getText();
        if(!helper.isEmpty(username) && !(accountTypeComboBox.getValue()==null)) {
            
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Email Verification");
            tid.setHeaderText("Enter the email address that \nyou have connected with your account");
            Optional<String> email = tid.showAndWait();
            if(email.isPresent()) {
                String emailAddress = email.get();
                
                if(!emailAddress.isEmpty()) {
                    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
                    if(emailAddress.matches(emailRegex)) {
                        String accountType = accountTypeComboBox.getValue().toString(); 
                        LoginStorage.getInstance().setAccountType(accountType);
                        
                        System.out.println("EMAIL GET " + email.get());
                        Boolean emailExists = dbHandler.checkEmailExistence(accountType, email.get());
                        System.out.println("EXISTS " + emailExists);
                        if(emailExists) {
                           
                            // Set credentials
                            Properties senderProperties = loadProperties();
                            String senderEmail = senderProperties.getProperty("senderEmail");
                            String senderPassword = senderProperties.getProperty("senderPassword");
                            String recepientEmail = emailAddress;

                            //Set subject and message
                            String subject = "Reset Password";
                            String randomCode = getRandomCode();
                            String msg = "Please enter the following code in the program to reset the password for your account\n";
                            msg = msg + randomCode;
                            System.out.println(msg);
                            // Get properties object    
                            Properties properties = new Properties();    
                            properties.put("mail.smtp.host", "smtp.gmail.com");    
                            properties.put("mail.smtp.socketFactory.port", "465");    
                            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");    
                            properties.put("mail.smtp.auth", "true");    
                            properties.put("mail.smtp.port", "465");    

                            // Get Session   
                            Session session = Session.getDefaultInstance(properties,    
                                new javax.mail.Authenticator() {    
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {    
                                        return new PasswordAuthentication(senderEmail, senderPassword);  
                                    }    
                                }
                            );    
                            
                            
                            //Store in database
                            ForgotPasswordRequest fp = new ForgotPasswordRequest();
                            java.sql.Date sqlDate = new java.sql.Date(helper.getCurrentDate().getTime());
                            fp.setDate(sqlDate);
                            System.out.println("HELPER TIME " + helper.getCurrentTime().toString());
                            fp.setTime(helper.getCurrentTime().toString());
                            System.out.println("hoola");
                            fp.setCode(randomCode);
                            
                            //TODO Change this
                            int id = dbHandler.getIdForVerification(accountType, username, emailAddress);
                            LoginStorage.getInstance().setId(id);
                            fp.setId(id);
                            System.out.println("IDDD " + id);
                            System.out.println("ATTTTT " + accountType);
                            System.out.println("USER " + username);
                            System.out.println("EMAIL "+ emailAddress);
                            int autoGenereatedId = dbHandler.storeForgotPasswordRequestAndGetItsKey(accountType, fp);
                            
                            
//                            dbHandler.storeForgotPasswordRequest(accountType, 
//                                new ForgotPasswordRequest (
//                                    
//                                )
//                            );
//                            
                            
                            // Compose message    
                            try {    
                                MimeMessage message = new MimeMessage(session);
                                message.addRecipient(Message.RecipientType.TO,new InternetAddress(recepientEmail));    
                                message.setSubject(subject);    
                                message.setText(msg);    


                                // Send message  
                                Transport.send(message);    
                                helper.showDialogBox(true, "Message Sent");
                                showInputCodeDialog(event, accountType, autoGenereatedId, fp);
                            } 
                            catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            System.out.println("EXISTS " + emailExists);
                            helper.showDialogBox(true, "Provided email is not associated with any account");
                        }
                    }
                    else {
                        helper.showDialogBox(true, "Provided value doesn't confirm with email address pattern");
                    }
                }
                else {
                    helper.showDialogBox(true, "No email address provided");
                }
            }   
        }
        else {
            helper.showDialogBox(true, "Please select account type and enter username");
        }

        
    }
    
    protected String getRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$&";
        int lengthOfCode = 8;
        int counter = 0;
        Random random = new Random();
        String code = "";
        while(counter < lengthOfCode) {
            //int index = (int) (random.nextFloat() * characters.length());
            int index = (int) (random.nextFloat() * random.nextInt(characters.length()-1) + 1);
            //System.out.println("index " + index);
            code = code + characters.charAt(index);
            //System.out.println("code " + code);
            counter++;
        }
        return code;
    }
    
    public void showInputCodeDialog(ActionEvent event, String accountType, int autoGeneratedId, ForgotPasswordRequest fpr) throws SQLException, IOException {
        boolean codeVerification = false;
        while(codeVerification == false) {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Enter verification code");
            tid.setHeaderText("Enter the verification code you received in your email");
            Optional<String> codeTextField = tid.showAndWait();
            if(codeTextField.isPresent()) {
                String code = codeTextField.get();
                codeVerification = dbHandler.verifyCode(accountType, code, autoGeneratedId, fpr);
                System.out.println("CV " + codeVerification);
            }
            
            if(codeVerification) {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/com/Project/FXML/AdminCreatePassword.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
