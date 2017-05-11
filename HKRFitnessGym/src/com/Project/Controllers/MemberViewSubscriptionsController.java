/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
/**
 * FXML Controller class
 *
 * @author shameer
 */
public class MemberViewSubscriptionsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField searchPackage;
    //private  ObservableList<Package> data;
    private  ObservableList<Package> searchData;
    
    private final int memberId = LoginStorage.getInstance().getId();
    private final String username = LoginStorage.getInstance().getUsername();
    
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
    
    ObservableList<Subscription> subscription;
    
    private final DBHandler dbHandler = new DBHandler();
    private final Helper helper = new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            subscription = dbHandler.memberViewSubscription();
            System.out.println(subscription);
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            subscriptionStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStartDate"));
            subscriptionEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
            subscriptionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
            offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));
            messageColumn.setCellValueFactory(new PropertyValueFactory<>("declineMessage"));
            memberViewSubscriptionsTable.setItems(null);
            memberViewSubscriptionsTable.setItems(subscription);    
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MemberViewPackagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void cancelBtnClick(ActionEvent event) throws IOException, SQLException {
        ObservableList<Subscription> row , allRows;
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
        boolean cancelError = true;
        if(row.isEmpty()) {
            helper.showDialogBox(cancelError, "Select a subscription first to cancel");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            if(subscriptionStatus.equals("Cancelled")) {
                helper.showDialogBox(cancelError, "You have already cancelled this subscription or this subscription has already expired");
            }
            else if(subscriptionStatus.equals("Requested")) {
                helper.showDialogBox(cancelError, "You cannot cancel this subscription. \nYour subscription request is being processed");
            }
            else if(subscriptionStatus.equals("Declined")) {
                helper.showDialogBox(cancelError, "You cannot cancel this subscription. \nYour subscription has been declined. For more information, please contact the gym");
            }
            else {
                int subscriptionId = row.get(0).getSubscriptionId();
                Optional<ButtonType> result = DialogBoxChoice( "Confirm Cancellation", "Are you sure you want to cancel this subscription?", "/com/Project/FXML/MemberViewSubscriptions");
                if (result.get().getText().equals("Yes")) {
                    cancelError = dbHandler.cancelSubscription(subscriptionId);
                    if (cancelError) {
                        helper.showDialogBox(cancelError, "Cannot cancel subscription");
                    }
                    else {
                        helper.showDialogBox(cancelError, "Successfully cancelled subscription");
                        row.forEach(allRows::remove);
                    }
                }
            }    
        }
        
    }
    
    public void renewBtnClick(ActionEvent event) throws SQLException {
        System.out.println("Hello");
        ObservableList<Subscription> row , allRows;
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 
        
        
        boolean renewError = true;
        java.util.Date currentDate = helper.getCurrentDate();
        
        System.out.println("ROW " + row);

        if(row.isEmpty()) {
            helper.showDialogBox(renewError, "Select a subscription first to renew");
        }
        else {
            
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            if(subscriptionStatus.equals("Active")) {
                helper.showDialogBox(renewError, "Your subscription has not expired yet");
            }
            else if(subscriptionStatus.equals("Requested")) {
                helper.showDialogBox(renewError, "You cannot renew this subscription. \nYour subscription request is being processed.");
            }
            else if(subscriptionStatus.equals("Declined")) {
                helper.showDialogBox(renewError, "You cannot renew this subscription. \nYour subscription has been declined. For more information, please contact the gym.");
            }
            else {
                LocalDate subscriptionStartLocalDate = subscriptionStartDatePicker.getValue();
                LocalDate subscriptionEndLocalDate = subscriptionEndDatePicker.getValue();
                if (subscriptionStartLocalDate == null || subscriptionEndLocalDate  == null) {
                        helper.showDialogBox(renewError, "Enter subscription start date and end date");
                }
                else {
                    Date subscriptionStartDate = helper.convertLocalDateToSQLDate(subscriptionStartLocalDate);
                    Date subscriptionEndDate = helper.convertLocalDateToSQLDate(subscriptionEndLocalDate);
                    
                    if(subscriptionStartDate.before(currentDate) || subscriptionEndDate.before(currentDate)) {
                        helper.showDialogBox(renewError, "Subscription start date and end date cannot be earlier than current date");
                    }
                    else {
                        Date packageStartDate = row.get(0).getStartDate();
                        Date packageEndDate = row.get(0).getEndDate();
                        System.out.println("ksalfdsdffdsfdssfdfsdsdf " + packageEndDate);
                        if((subscriptionStartDate.before(packageStartDate) || subscriptionStartDate.after(packageEndDate))
                                || (subscriptionEndDate.before(packageStartDate) || subscriptionEndDate.after(packageEndDate))) {
                            helper.showDialogBox(renewError, "Subscription start date and end date must be within the range of Package start date and end date");
                        }
                        else {
                            if(subscriptionStartDate.after(subscriptionEndDate)) {
                                helper.showDialogBox(renewError, "Subscription start date must be earlier than subscription end date");
                            }
                             else {
                                Subscription subs = new Subscription();
                                subs.setSubscriptionStartDate((java.sql.Date)subscriptionStartDate);
                                subs.setSubscriptionEndDate((java.sql.Date)subscriptionEndDate);
                                String packageName = row.get(0).getPackageName();
                                int packageId = dbHandler.getPackageIdFromPackageName(packageName);
                                subs.setPackageId(packageId);
                                subs.setMemberId(memberId);
                                renewError = dbHandler.subscribeToPackage(subs);
                                if (renewError) {
                                    helper.showDialogBox(renewError, "Cannot renew subscription");
                                }
                                else {
                                    helper.showDialogBox(renewError, "Successfully renewed subscription");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
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
    
    public void saveDetailsBtnClick(ActionEvent event) throws IOException {
        ObservableList<Subscription> row , allRows;
        allRows = memberViewSubscriptionsTable.getItems();
        row = memberViewSubscriptionsTable.getSelectionModel().getSelectedItems(); 

        if(row.isEmpty()) {
            helper.showDialogBox(true, "Select a subscription first to save invoice");
        }
        else {
            String subscriptionStatus = row.get(0).getSubscriptionStatus();
            if(subscriptionStatus.equals("Requested") || subscriptionStatus.equals("Declined")) {
                helper.showDialogBox(true, "You cannot save invoice for this subscription. \nYour subscription request is being processed");
            }
            else {
                String fileName = "HKRFitnessGym_Subscription_Invoice_" + row.get(0).getSubscriptionId() + ".pdf";
                
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Invoice");
                fileChooser.setInitialFileName(fileName);
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                File file = fileChooser.showSaveDialog(stage);
                String filePath = file.getAbsolutePath();
               // System.out.println("PATH " + path);
                //String path = fileChooser.
                
                if(file != null) {
                    try {
                        PDDocument document = new PDDocument();
                        PDPage page = new PDPage();
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        //System.out.println("PAGE MD " + page.getMetadata().getLength());
//                        //Begin the Content stream 
//                        contentStream.beginText(); 
//       
//                        //Setting the font to the Content stream  
//                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
//
//                        //Setting the position for the line 
//                        contentStream.newLineAtOffset(10, 400);
//
//                        String text = "Member Name";
//
//                        //Adding text in the form of string 
//                        contentStream.showText(text);      
//
//                        //Ending the content stream
//                        contentStream.endText();
                        
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
           
//Setting the non stroking color
                        contentStream.setNonStrokingColor(Color.yellow);
      

      //Drawing a rectangle 
      contentStream.addRect(55, 40, 500, 700);

      //Drawing a rectangle
      contentStream.stroke();
      contentStream.setLineWidth(1f);
      contentStream.setStrokingColor(Color.BLACK);
contentStream.moveTo(40f, 30f);
contentStream.lineTo(570f, 30f);
contentStream.closeAndStroke();

contentStream.stroke();
      contentStream.setLineWidth(1f);
      contentStream.setStrokingColor(Color.BLACK);
contentStream.moveTo(55, 660);
contentStream.lineTo(555, 660);
contentStream.closeAndStroke();
                        System.out.println("Content added");

                        //Closing the content stream
                        contentStream.close();
                        document.save(filePath);

}
                    catch(IOException e) {
                        helper.showDialogBox(true, "File could be saved because of an error");
                    }
                }
            }
        }
//            else {
//                
//                try {
//                String fileName = "GymInvoiceSubscription" + row.get(0).getSubscriptionId() + ".pdf";
//                String fileDestination = System.getProperty("user.dir");
//                
//                PDDocument document = new PDDocument();
////                System.out.println("FIlen " + document);
//                PDPage page = new PDPage();
////                System.out.println("FIle2 " + fileName);
//                document.addPage(page);
//                System.out.println("FIle " + fileName);
//                
////                PDPageContentStream contentStream = new PDPageContentStream(document, page);
////                System.out.println("A " + fileName);
////                contentStream.beginText();
////                String text = "HKR Fitness Gym";
////                contentStream.showText(text);
////                
////                contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
////                contentStream.newLineAtOffset(25, 500);
////                    System.out.println("B" + fileName);
////               // content.drawString("HKR Fitness Gym");
////                 System.out.println(System.getProperty("user.dir"));
//////                 content.addRect(100, 100, 100, 100);
//////                
//////                    System.out.println("c");
////                        contentStream.endText();
//////                    System.out.println("d");
////                contentStream.close();





//    PDPageContentStream contentStream = new PDPageContentStream(document, page);
//      
//      //Begin the Content stream 
//      contentStream.beginText(); 
//       
//      //Setting the font to the Content stream  
//      contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
//
//      //Setting the position for the line 
//      contentStream.newLineAtOffset(25, 500);
//
//      String text = "This is the sample document and we are adding content to it.";
//
//      //Adding text in the form of string 
//      contentStream.showText(text);      
//
//      //Ending the content stream
//      contentStream.endText();
//
//      System.out.println("Content added");
//
//      //Closing the content stream
//      contentStream.close();
//      document.save("Hello.pdf");
//   





//
//                    System.out.println("e");
//                document.save(fileDestination + "/" + fileName);
//                    System.out.println("f");
//                document.close();
//                
//                System.out.println(System.getProperty("user.dir"));
//                
//                }
//                catch(Exception e) {
//
//                }
//            }
//        }
    }
    }