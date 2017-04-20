/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Project.Controllers;

/**
 *
 * @author shameer
 */
public class LoginStatus {
    private static int ssn;
    private static boolean login;
    
    public static void setSSN(int ssnum) {
        ssn = ssnum;
    }
    
    public static void setLogin(boolean loginState) {
        login = loginState;
    }
    
    public static int getSSN() {
        return ssn;
    }
    
    public static boolean getLogin() {
        return login;
    }
}
