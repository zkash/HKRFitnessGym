package com.Project.Controllers;

import com.Project.Models.DBHandler;
import com.Project.Models.Helper;
import com.Project.Models.LoginStorage;
import com.Project.Models.Subscription;
import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Optional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberViewSubscriptionsController implements Initializable {
    @FXML private TableView<Subscription> memberViewSubscriptionsTable;
    @FXML private TableColumn<Subscription, String> packageNameColumn;
    @FXML private TableColumn<Subscription, String> priceColumn; 
    @FXML private TableColumn<Subscription, String> startTimeColumn;
    @FXML private TableColumn<Subscription, String> endTimeColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStartDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionEndDateColumn;
    @FXML private TableColumn<Subscription, String> subscriptionStatusColumn;
    @FXML private TableColumn<Subscription, String> offeredPriceColumn;
    @FXML private TableColumn<Subscription, String> messageColumn;
    
    @FXML private DatePicker subscriptionStartDatePicker;
    @FXML private DatePicker subscriptionEndDatePicker;
   
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    private final int memberId = LoginStorage.getInstance().getId();
    private final String username = LoginStorage.getInstance().getUsername();
    
    ObservableList<Subscription> data, row, allRows;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String filter = "All";
            data = dbHandler.memberViewSubscription(memberId, filter);
            setDataInTable(data);    
        } 
        catch (SQLException e) {
            helper.showDialogBox(true, "Could not fetch data from database and show in table because of an error");
        }
    }    
    
    
    /**
     * Cancels a subscription
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    public void handleCancelBtnClick(ActionEvent event) throws IOException, SQLException {
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
        
        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription first to cancel");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            switch (subscriptionStatus) {
                case "Canceled":
                    helper.showDialogBox(true, "You have already canceled this subscription or this subscription has already expired");
                    break;
                case "Requested":
                    helper.showDialogBox(true, "You cannot cancel this subscription. \nYour subscription request is being processed");
                    break;
                case "Declined":
                    helper.showDialogBox(true, "You cannot cancel this subscription. \nYour subscription has been declined. For more information, please contact the gym");
                    break;
                default:
                    int subscriptionId = row.get(0).getSubscriptionId();
                    Optional<ButtonType> result = DialogBoxChoice( "Confirm Cancellation", "Are you sure you want to cancel this subscription?", "/com/Project/FXML/MemberViewSubscriptions");
                    if (result.get().getText().equals("Yes")) {
                        try {
                            dbHandler.cancelSubscription(subscriptionId);
                            helper.showDialogBox(false, "Successfully canceled subscription");
                            row.forEach(allRows::remove);
                        }
                        catch(SQLException e) {
                            helper.showDialogBox(true, "Cannot cancel subscription");
                        }
                    }   break;
            }
        }
    }
    
    
    /**
     * Sends a request to renew subscription
     * @param event
     * @throws SQLException 
     */
    public void handleRenewBtnClick(ActionEvent event) throws SQLException {
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
       
        java.util.Date currentDate = helper.getCurrentDate();

        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription first to renew");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            switch (subscriptionStatus) {
                case "Active":
                    helper.showDialogBox(true, "Your subscription has not expired yet");
                    break;
                case "Requested":
                    helper.showDialogBox(true, "You cannot renew this subscription. \nYour subscription request is being processed.");
                    break;
                case "Declined":
                    helper.showDialogBox(true, "You cannot renew this subscription. \nYour subscription has been declined. For more information, please contact the gym.");
                    break;
                default:
                    LocalDate subscriptionStartLocalDate = subscriptionStartDatePicker.getValue();
                    LocalDate subscriptionEndLocalDate = subscriptionEndDatePicker.getValue();
                    
                    if (subscriptionStartLocalDate == null || subscriptionEndLocalDate  == null) {
                        helper.showDialogBox(true, "Enter subscription start date and end date");
                    }
                    else {
                        Date subscriptionStartDate = helper.convertLocalDateToSQLDate(subscriptionStartLocalDate);
                        Date subscriptionEndDate = helper.convertLocalDateToSQLDate(subscriptionEndLocalDate);
                        
                        if(subscriptionStartDate.before(currentDate) || subscriptionEndDate.before(currentDate)) {
                            helper.showDialogBox(true, "Subscription start date and end date cannot be earlier than current date");
                        }
                        else {
                            Date packageStartDate = row.get(0).getStartDate();
                            Date packageEndDate = row.get(0).getEndDate();
                            
                            if((subscriptionStartDate.before(packageStartDate) || 
                                    subscriptionStartDate.after(packageEndDate)) || 
                                    (subscriptionEndDate.before(packageStartDate) || 
                                    subscriptionEndDate.after(packageEndDate))) {
                                helper.showDialogBox(true, "Subscription start date and end date must be within the range of Package start date and end date");
                            }
                            else {
                                if(subscriptionStartDate.after(subscriptionEndDate)) {
                                    helper.showDialogBox(true, "Subscription start date must be earlier than subscription end date");
                                }
                                else {
                                    String packageName = row.get(0).getPackageName();
                                    int packageId = dbHandler.getPackageIdFromPackageName(packageName);
                                    
                                    Subscription subs = new Subscription();
                                    
                                    subs.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
                                    subs.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
                                    subs.setPackageId(packageId);
                                    subs.setMemberId(memberId);
                                    
                                    try {
                                        dbHandler.subscribeToPackage(subs);
                                        helper.showDialogBox(false, "Successfully renewed subscription");
                                    }
                                    catch(SQLException e) {
                                        helper.showDialogBox(true, "Cannot renew subscription");
                                    }
                                }
                            }
                        }
                    }   
                    break;
            }
        }
    }

    
    /**
     * Handles radio box with label 'All' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterAllSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "All";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Active' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterActiveSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Active";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Expired' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterExpiredSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Expired";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Handles radio box with label 'Canceled' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterCanceledSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Canceled";
        setDataInTableByFilter(filter);
    }
    
    /**
     * Handles radio box with label 'Requested' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterRequestedSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Requested";
        setDataInTableByFilter(filter);
    }
    
    /**
     * Handles radio box with label 'Declined' click
     * @param event
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void subscriptionFilterDeclinedSelected(ActionEvent event) throws SQLException, IllegalArgumentException, InvocationTargetException {
        String filter = "Declined";
        setDataInTableByFilter(filter);
    }
    
    
    /**
     * Creates a PDF file of subscription details and saves it location selected by the user
     * @param event
     * @throws IOException 
     */
    public void handleSaveDetailsBtnClick(ActionEvent event) throws IOException {
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 

        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription first to save invoice");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            
            switch (subscriptionStatus) {
                case "Requested":
                    helper.showDialogBox(true, "You cannot save invoice for this subscription. \nYour subscription request is being processed");
                    break;
                case "Declined":
                    helper.showDialogBox(true, "You cannot save invoice for this subscription. \nYour subscription request has been declined");
                    break;
                default:
                    String fileName = "HKRFitnessGym_Subscription_Invoice_" + row.get(0).getSubscriptionId() + ".pdf";
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Invoice");
                    fileChooser.setInitialFileName(fileName);
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    File file = fileChooser.showSaveDialog(stage);
                    String filePath = file.getAbsolutePath();
                    try {
                        PDDocument document = new PDDocument();
                        PDPage page = new PDPage();
                        document.addPage(page);
                        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                            helper.putTextInPdf(contentStream, 30, 210, 700, "HKRFitnessGym");
                            helper.putTextInPdf(contentStream, 20, 230, 670, "Subscription Details");
                            helper.putTextInPdf(contentStream, 15, 100, 630, "Username: ");
                            helper.putTextInPdf(contentStream, 20, 400, 630, username);
                            helper.putTextInPdf(contentStream, 15, 100, 580, "Subscription ID: ");
                            helper.putTextInPdf(contentStream, 20, 400, 580, Integer.toString(row.get(0).getSubscriptionId()));
                            helper.putTextInPdf(contentStream, 15, 100, 530, "Subscription Status: ");
                            helper.putTextInPdf(contentStream, 20, 400, 530, row.get(0).getSubscriptionStatus());
                            helper.putTextInPdf(contentStream, 15, 100, 480, "Package Name: ");
                            helper.putTextInPdf(contentStream, 20, 400, 480, row.get(0).getPackageName());
                            helper.putTextInPdf(contentStream, 15, 100, 430, "Package Cost: ");
                            helper.putTextInPdf(contentStream, 20, 400, 430, Float.toString(row.get(0).getPrice()));
                            helper.putTextInPdf(contentStream, 15, 100, 380, "Package Start Date: ");
                            helper.putTextInPdf(contentStream, 20, 400, 380, helper.convertDateToString(row.get(0).getStartDate()));
                            helper.putTextInPdf(contentStream, 15, 100, 330, "Package End Date: ");
                            helper.putTextInPdf(contentStream, 20, 400, 330, helper.convertDateToString(row.get(0).getEndDate()));
                            helper.putTextInPdf(contentStream, 15, 100, 280, "Start Time: ");
                            helper.putTextInPdf(contentStream, 20, 400, 280, row.get(0).getStartTime());
                            helper.putTextInPdf(contentStream, 15, 100, 230, "Start Time: ");
                            helper.putTextInPdf(contentStream, 20, 400, 230, row.get(0).getEndTime());
                            helper.putTextInPdf(contentStream, 15, 100, 180, "Subscription Start Date: ");
                            helper.putTextInPdf(contentStream, 20, 400, 180, helper.convertDateToString(row.get(0).getSubscriptionStartDate()));
                            helper.putTextInPdf(contentStream, 15, 100, 130, "Subscription End Date: ");
                            helper.putTextInPdf(contentStream, 20, 400, 130, helper.convertDateToString(row.get(0).getSubscriptionEndDate()));
                            helper.putTextInPdf(contentStream, 15, 100, 80, "Offered Price: ");
                            helper.putTextInPdf(contentStream, 20, 400, 80, Float.toString(row.get(0).getOfferPrice()));
                            
                            helper.drawRectangleInPdf(contentStream, 55, 40, 500, 700, Color.white, "Stroke");
                            
                            helper.drawLineInPdf(contentStream, 40, 30, 570, 30, Color.black, 1);
                            helper.drawLineInPdf(contentStream, 55, 660, 555, 660, Color.black, 1);
                            
                            contentStream.close();
                        }
                        document.save(filePath);
                    }
                    catch(IOException e) {
                        helper.showDialogBox(true, "File could be saved because of an error");
                    }   break;
            }
        }
    }
    
    
    /**
     * Shows a confirmation dialog box
     * @param header The header of the dialog box
     * @param content The message of the dialog box
     * @param nextScene The URL of the scene to redirect to 
     * @return
     * @throws IOException 
     */
    public static Optional<ButtonType> DialogBoxChoice(String header, String content, String nextScene) throws IOException {    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(content);
        alert.setHeaderText(header);
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    
    
    /**
     * Sets data in table view
     * @param data 
     */
    public void setDataInTable(ObservableList<Subscription> data) {
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
        subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
        subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
        offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("declineMessage"));
        memberViewSubscriptionsTable.setItems(data);   
    }
    
    
    /**
     * Gets the data from database, sets in in table, and fixes the width of the columns
     * @param filter
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public void setDataInTableByFilter(String filter) throws SQLException, IllegalArgumentException, InvocationTargetException {
        data = dbHandler.memberViewSubscription(memberId, filter);
        setDataInTable(data);
        helper.fitColumns(memberViewSubscriptionsTable); 
    }
}