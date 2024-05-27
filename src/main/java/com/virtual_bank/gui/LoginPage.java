package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;

import com.virtual_bank.gui.common.*;
import com.virtual_bank.gui.common.Button;

public class LoginPage extends JPanel {
    private CuteTextField usernameField;  // Username
    private CutePasswordField passwordField;  // Password
    private Button loginButton;  // Login button
    private Label messageLabel;  // Message

    public LoginPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Border

        panel.add(new CuteLabel("Username:"));  // Username
        this.usernameField = new CuteTextField(20);  // Initialize
        panel.add(usernameField);  // Add to panel
        panel.add(Box.createRigidArea(new Dimension(0, 5)));  // Spacing

        panel.add(new CuteLabel("Password:"));  // Password
        this.passwordField = new CutePasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.messageLabel = new Label(); // Message
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.loginButton = new Button("Login");  // Login button
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();  // Get username
                String password = new String(passwordField.getPassword());  // Get password
                User user = XMLDBManager.findUser(username);  // Find user
                boolean success = user != null && user.getPasswd().equals(password);  // Validate

                if (success) {
                    messageLabel.setText("Successfully login.");  // Success message
                    baseFrame.sessionManager.login(user.getUid(), user.getName());  // Login
                    baseFrame.refresh();  // Refresh main page
                }
                else {
                    messageLabel.setText("Your username or password is incorrect!");  // Error message
                }
            }
        });
        panel.add(this.loginButton);

        this.add(panel);  // Add panel to main panel
    }
}
