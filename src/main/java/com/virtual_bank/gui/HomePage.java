package com.virtual_bank.gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class HomePage extends JPanel {
    public HomePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.add(new JLabel("<html><h2>Hello, " + baseFrame.sessionManager.getUsername() + ".👋</h2></html>."), BorderLayout.CENTER);
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER);
        }
    }
}