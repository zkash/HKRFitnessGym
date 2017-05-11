package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.ForgotPasswordRequest;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
 * @author shameer
 */

public class LoginPageController implements Initializable {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private ComboBox accountTypeComboBox;

    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Implement new Runnable() by overriding run() method to set focus on first textfield
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                usernameTextField.requestFocus();
            }
        });
        
        //Set account type values in combobox
        ObservableList<String> accountTypeOptions = FXCollections.observableArrayList("Admin", "Member");
        accountTypeComboBox.setItems(accountTypeOptions);   
    }    

    
    /**
     * Loads the properties file that has system email and password to handle forgot password
     * @return 
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/com/Project/Properties/hkrFitnessGymForgotPassword.properties")) {
            properties.load(fis);
        }
        catch (Exception e) {
        }
        return properties;
    }
   
    
    /**
     * Handles login button click
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void loginBtnClick(ActionEvent event) throws IOException, SQLException {
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
    }

    
    /**
     * Exits the application
     * @param event 
     */
    @FXML 
    private void exitBtnClick(ActionEvent event) {
        System.exit(0);
    }
    
    
    /**
     * Redirects to Admin Main Page
     * @param event
     * @throws IOException 
     */
    private void visitAdminPage(ActionEvent event) throws IOException {
        helper.navigateScene(event, "AdminMainPage.fxml");
    }
    
    
    /**
     * Redirects to Member Main Page
     * @param event
     * @throws IOException 
     */
    private void visitMemberPage(ActionEvent event) throws IOException {
        helper.navigateScene(event, "MemberMainPage.fxml");
    }

    
    /**
     * Handles the forget password hyperlink click
     * @param event
     * @throws IOException
     * @throws AddressException
     * @throws MessagingException
     * @throws SQLException 
     */
    @FXML
    private void forgotPasswordLinkClick(ActionEvent event) throws IOException, AddressException, MessagingException, SQLException {
        String username = usernameTextField.getText();
        if(!helper.isEmpty(username) && !(accountTypeComboBox.getValue()==null)) {
            String accountType = accountTypeComboBox.getValue().toString();
            
            //Set in LoginStorage
            LoginStorage.getInstance().setAccountType(accountType);
            
            boolean usernameValid = dbHandler.verifyUsername(username, accountType);
            
            if(!usernameValid) {
                helper.showDialogBox(true, "Account doesn't exists");
            }
            else {
                String title = "Email Verification";
                String header = "Enter the email address that \nyou have connected with your account";
                Optional<String> email = helper.showTextInputDialog(title, header);

                if(email.isPresent()) {
                    String emailAddress = email.get();

                    if(!emailAddress.isEmpty()) {
                        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

                        if(emailAddress.matches(emailRegex)) {
                            

                            Boolean emailExists = dbHandler.checkEmailExistence(accountType, email.get());

                            if(emailExists) {
                                //Generate random code
                                String randomCode = getRandomCode();

                                //Send email
                                sendEmail(emailAddress, randomCode);  

                                //Store random code in database
                                ForgotPasswordRequest fp = new ForgotPasswordRequest();
                                int autoGeneratedId = storeRandomCodeInDatabaseAndGetItsId(fp, randomCode, accountType, username, emailAddress);

                                helper.showDialogBox(true, "Message Sent");

                                //Show random code entry dialog
                                showCodeDialogAndRedirect(event, accountType, autoGeneratedId, fp);  
                            }
                            else {
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
        }
        else {
            helper.showDialogBox(true, "Please select account type and enter username");
        }     
    }
    
    
    /**
     * Generates a random code
     * @return 
     */
    protected String getRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$&";
        int lengthOfCode = 8;
        int counter = 0;
        Random random = new Random();
        String code = "";
        while(counter < lengthOfCode) {
            //int index = (int) (random.nextFloat() * characters.length());
            int index = (int) (random.nextFloat() * random.nextInt(characters.length()-1) + 1);
            code = code + characters.charAt(index);
            counter++;
        }
        return code;
    }
    
    
    /**
     * Shows the dialog box where the user can enter the random code and redirect to change password 
     * @param event
     * @param accountType Account type - Admin or Member
     * @param autoGeneratedId Automatically generated Id in the table that stores the request
     * @param fpr ForgotPasswordRequest class object
     * @throws SQLException
     * @throws IOException 
     */
    public void showCodeDialogAndRedirect(ActionEvent event, String accountType, int autoGeneratedId, ForgotPasswordRequest fpr) throws SQLException, IOException {
        boolean codeVerification = false;
        while(codeVerification == false) {
            Optional<String> codeTextField = helper.showTextInputDialog("Enter verification code", "Enter the verification code you received in your email");
            
            if(codeTextField.isPresent()) {
                String code = codeTextField.get();
                codeVerification = dbHandler.verifyCode(accountType, code, autoGeneratedId, fpr);
            }
            
            if(codeVerification) {
                helper.navigateScene(event, "CreatePassword.fxml");
            }
        }
    }
    
    
    /**
     * Sets the credentials, gets properties and sessions, composes message, and sends the email
     * @param emailAddress Email address stored in the database in which to send email
     * @param randomCode Randomly generated code
     */
    public void sendEmail(String emailAddress, String randomCode) {
        //Set credentials
        Properties senderProperties = loadProperties();
        String senderEmail = senderProperties.getProperty("senderEmail");
        String senderPassword = senderProperties.getProperty("senderPassword");
        String recepientEmail = emailAddress;

        //Set subject and message
        String subject = "Reset Password";
        String msg = "Please enter the following code in the program to reset the password for your account\n";
        msg = msg + randomCode;
        
        //Get properties object    
        Properties properties = new Properties();    
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "465");    

        //Get Session   
        Session session = Session.getDefaultInstance(properties,    
            new javax.mail.Authenticator() {    
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {    
                    return new PasswordAuthentication(senderEmail, senderPassword);  
                }    
            }
        );   
        
        //Compose message    
        try {    
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(recepientEmail));    
            message.setSubject(subject);    
            message.setText(msg);    

            //Send message  
            Transport.send(message);    
        } 
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * Stores the random code in database and get its primary key id
     * @param fpr ForgotPasswordRequest class object
     * @param randomCode Randomly generated code
     * @param accountType Account type - Admin or Member
     * @param username Username of the user attempting to receive the email
     * @param emailAddress Email address of the user attempting to receive the email
     * @return
     * @throws SQLException 
     */
    public int storeRandomCodeInDatabaseAndGetItsId(ForgotPasswordRequest fpr, String randomCode, String accountType, String username, String emailAddress) throws SQLException {
        java.sql.Date sqlDate = new java.sql.Date(helper.getCurrentDate().getTime());
        fpr.setDate(sqlDate);
        fpr.setTime(helper.getCurrentTime().toString());
        fpr.setCode(randomCode);
        int id = dbHandler.getIdForVerification(accountType, username, emailAddress);
        
        //Set in LoginStorage
        LoginStorage.getInstance().setId(id);
        
        fpr.setId(id);
        int autoGeneratedId = dbHandler.storeForgotPasswordRequestAndGetItsKey(accountType, fpr);
        return autoGeneratedId;                    
    }
}