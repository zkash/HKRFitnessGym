package com.Project.Controllers;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author shameer
 */
public class AccountHelper {
    Helper helper = new Helper();
    
    /**
     * Sets text on label when given condition is true
     * @param condition Condition in which the text is set on label
     * @param lbl Label
     */
    public void setTextOnCondition(boolean condition, Label lbl) {
        if(condition) {
            lbl.setText("Invalid Value"); 
        }
        else {
            lbl.setText("");
        }
    }
    
    
    /**
     * Handles the change of focus in text fields
     * @param tf Text Field
     * @param textFieldList
     * @param lbl Label
     */
    public void changeFocus(TextField tf, ArrayList<TextField> textFieldList, Label lbl) {
        TextField firstName = textFieldList.get(0); 
        TextField middleName = textFieldList.get(1);
        TextField lastName = textFieldList.get(2);
        TextField address = textFieldList.get(3);
        TextField phoneNumber = textFieldList.get(4);
        TextField email = textFieldList.get(5);
        TextField ssn = textFieldList.get(6);
        TextField username = textFieldList.get(7);
        TextField password = textFieldList.get(8);
        lbl.setText("");
        tf.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(!newPropertyValue) {
                if (tf == firstName || tf == middleName || tf == lastName) {
                    setTextOnCondition(helper.hasDigit(tf.getText()), lbl);
                }
                else if (tf == address) {  
                    String notAddressRegex = "[0-9]+";
                    setTextOnCondition(address.getText().matches(notAddressRegex), lbl);
                }
                else if (tf == phoneNumber) {
                    setTextOnCondition(helper.hasChar(phoneNumber.getText()), lbl);
                }
                else if (tf == email) {
                    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
                    String ead = email.getText();
                    setTextOnCondition(!helper.isEmpty(ead) && !ead.matches(emailRegex), lbl);
                }
                else if (tf == ssn) {
                    String ssnRegex = "[0-9]{6}-[0-9]{4}";
                    String ssnum = ssn.getText();
                    setTextOnCondition(!helper.isEmpty(ssnum) && !ssnum.matches(ssnRegex), lbl);
                }
                else if (tf == username) {
                    String unRegex = "^[A-Za-z][A-za-z0-9]*";
                    String uname = username.getText();
                    setTextOnCondition(!helper.isEmpty(uname) && !uname.matches(unRegex), lbl);
                }
                else if (tf == password) {
                    String pwRegex = "(?=[a-zA-Z]*[0-9])(?=[0-9]*[a-zA-Z])^[0-9a-zA-Z]{5,}$"; //minimum 1 alpha, 1 number, 5 chars
                    String pwd = password.getText();
                    setTextOnCondition(!helper.isEmpty(pwd) && !pwd.matches(pwRegex), lbl);
                }
            }
        });
    }
}
