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
public class PackageSubscription {
    
    private final Package pack;
    private final String count;

    public PackageSubscription(Package p, String c) {
        this.pack = p;
        this.count = c;
    }
}
