package com.virtual_bank.gui;

import javax.swing.*;

public class AdminPage extends JPanel {
    private JPanel rankPanel;
    private JPanel missionPanel;

    public AdminPage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            if ("admin".equals(baseFrame.sessionManager.getUsername())) {
                // TODO
            }
            else {
                this.add(new JLabel("<html><h2>You have no access to this page.</h2></html>"));
            }
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"));
        }
    }    
}
