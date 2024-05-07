package com.virtual_bank.gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class HomePage extends JPanel {
    private JPanel missionPanel;
    private JPanel targetPanel;

    public HomePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.setLayout(new BorderLayout());
            this.add(new JLabel("<html><h2>Hello, " + baseFrame.sessionManager.getUsername() + ".👋</h2></html>."), BorderLayout.NORTH);
            this.missionPanel = new JPanel();
            this.missionPanel.add(new JLabel("Missions panel"));
            this.targetPanel = new JPanel();
            this.targetPanel.add(new JLabel("Targets panel"));
            this.add(this.missionPanel, BorderLayout.EAST);
            this.add(this.targetPanel, BorderLayout.WEST);
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER);
        }
    }
}