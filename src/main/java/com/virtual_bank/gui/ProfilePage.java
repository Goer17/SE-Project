package com.virtual_bank.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import com.virtual_bank.core.XMLDBManager;

public class ProfilePage extends JPanel {
    public ProfilePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            String username = baseFrame.sessionManager.getUsername();
            int money = XMLDBManager.findUser(username).getMoney();
            this.add(new JLabel("<html><h1>Profile</h1><br><h3>" + username + "</h3><br>" + "<h3>Your account balance: " + "<span style='color: blue;'>" + money + "</span></h3></html>"), BorderLayout.CENTER);
        }
        else {
            this.add(new JLabel("Please login first"), BorderLayout.CENTER); 
        }
    }
}
