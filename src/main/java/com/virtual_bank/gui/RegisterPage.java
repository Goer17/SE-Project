package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.virtual_bank.core.SessionManager;
import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;

public class RegisterPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel messageLabel;

    public RegisterPage(SessionManager sessionManager) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        messageLabel = new JLabel();
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // 添加间隔

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = new User(username, password, 0);

                boolean success = XMLDBManager.addUser(user);

                if (success) {
                    messageLabel.setText("Registration successful.");
                    // TODO
                } else {
                    messageLabel.setText("Registration failed. Please try again.");
                }
            }
        });
        panel.add(registerButton);

        this.add(panel);
    }
}
