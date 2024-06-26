package com.virtual_bank.gui;

import javax.swing.*;
import com.virtual_bank.core.SessionManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtual_bank.gui.common.*;
import com.virtual_bank.gui.common.Button;

public class BaseFrame extends JFrame implements ActionListener {
    protected JPanel navigationPanel; 
    protected JPanel contentPanel; 
    protected CardLayout cardLayout; // card Layout
    protected SessionManager sessionManager; 

    public BaseFrame(SessionManager sessionManager) {
        this.sessionManager = sessionManager;

        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);

        this.navigationPanel = new JPanel();
        this.navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // window attributes
        this.setTitle("Virtual Bank");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.initializeNavigationPanel();
        this.initializeContentPanel();

        // Switch panels based on login status
        if (!sessionManager.isLoggedIn()) {
            this.switchToPanel("Login");
        } else {
            if (!"admin".equals(sessionManager.getUsername())) {
                this.switchToPanel("Home");
            } else {
                this.switchToPanel("Admin");
            }
        }
    }

    private void initializeNavigationPanel() {
        if (this.sessionManager.isLoggedIn()) {
            if (!this.sessionManager.getUsername().equals("admin")) {
                this.addNavigationButton("Home");
                this.addNavigationButton("Profile");
            } else {
                // Admin
                this.addNavigationButton("Admin");
            }
        }
        if (!this.sessionManager.isLoggedIn()) {
            this.addNavigationButton("Login");
            this.addNavigationButton("Register");
        } else {
            this.addNavigationButton("Logout");
        }
        this.add(navigationPanel, BorderLayout.NORTH);
    }

    protected void addNavigationButton(String name) {
        Button button = new Button(name);
        button.addActionListener(this);
        this.navigationPanel.add(button);
    }

    private void initializeContentPanel() {
        if (this.sessionManager.isLoggedIn()) {
            if (!"admin".equals(this.sessionManager.getUsername())) {
                this.contentPanel.add(new HomePage(this), "Home");
                this.contentPanel.add(new ProfilePage(this), "Profile");
            } else {
                this.contentPanel.add(new AdminPage(this), "Admin");
            }
        }

        if (!this.sessionManager.isLoggedIn()) {
            this.contentPanel.add(new LoginPage(this), "Login");
            this.contentPanel.add(new RegisterPage(this), "Register");
        }

        this.add(this.contentPanel, BorderLayout.CENTER);
    }

    // refresh the page
    public void refresh() {
        this.navigationPanel.removeAll();
        this.contentPanel.removeAll();

        this.initializeNavigationPanel();
        this.navigationPanel.revalidate();
        this.navigationPanel.repaint();

        this.initializeContentPanel();
        this.contentPanel.revalidate();
        this.contentPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.switchToPanel(e.getActionCommand());
    }

    protected void switchToPanel(String panelName) {
        if (!"Logout".equals(panelName)) {
            this.cardLayout.show(contentPanel, panelName);
        } else {
            // logout
            this.sessionManager.logout();
            this.refresh();
        }
    }
}
