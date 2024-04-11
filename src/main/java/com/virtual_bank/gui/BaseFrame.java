package com.virtual_bank.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseFrame extends JFrame implements ActionListener {
    protected JPanel navigationPanel;
    protected JPanel contentPanel;
    protected CardLayout cardLayout;

    public BaseFrame() {
        this.setTitle("Virtual Bank");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Initialize navigatation panel and content panel.
        this.initializeNavigationPanel();
        this.initializeContentPanel();
    }

    private void initializeNavigationPanel() {
        this.navigationPanel = new JPanel();
        this.navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(navigationPanel, BorderLayout.NORTH);

        // Add navigation buttons.
        this.addNavigationButton("Home");
        this.addNavigationButton("Profile");
    }

    protected void addNavigationButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        this.navigationPanel.add(button);
    }

    private void initializeContentPanel() {
        cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);
        this.add(this.contentPanel, BorderLayout.CENTER);

        this.contentPanel.add(new HomePage(), "Home");
        this.contentPanel.add(new ProfilePage(), "Profile");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle navigation events
        this.switchToPanel(e.getActionCommand());
    }

    protected void switchToPanel(String panelName) {
        this.cardLayout.show(contentPanel, panelName);
    }
}