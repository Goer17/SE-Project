package com.virtual_bank.gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class HomePage extends JPanel {
    public HomePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.add(new JLabel("Hello, " + baseFrame.sessionManager.getUsername() + "."), BorderLayout.CENTER);
        }
        else {
            this.add(new JLabel("Please login first"), BorderLayout.CENTER); 
        }
    }
}