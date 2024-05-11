package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;

import com.virtual_bank.gui.common.Button;

public class RegisterPage extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Button registerButton;
    private JLabel messageLabel;

    public RegisterPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        this.usernameField = new JTextField(20);
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(new JLabel("Password:"));
        this.passwordField = new JPasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.messageLabel = new JLabel();
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.registerButton = new Button("Register");
        this.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (password.length() < 6) {
                    messageLabel.setText("Your password should contain at least 6 characters.");
                    return;
                }

                User user = new User("#new", username, password, 0);

                boolean success = XMLDBManager.addUser(user);

                if (success) {
                    messageLabel.setText("Successfull register.");
                    baseFrame.sessionManager.login(user.getUid(), user.getName());
                    baseFrame.refresh();
                } else {
                    messageLabel.setText("Your username is duplicate. Please try another one.");
                }
            }
        });
        panel.add(registerButton);

        this.add(panel);
    }
}