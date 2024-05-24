package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;

import com.virtual_bank.gui.common.*;
import com.virtual_bank.gui.common.Button;
;

public class LoginPage extends JPanel {
    private CuteTextField usernameField;
    private CutePasswordField passwordField;
    private Button loginButton;
    private Label messageLabel;

    public LoginPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new CuteLabel("Username:"));
        this.usernameField = new CuteTextField(20);
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(new CuteLabel("Password:"));
        this.passwordField = new CutePasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.messageLabel = new Label();
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.loginButton = new Button("Login");
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = XMLDBManager.findUser(username);
                boolean success = user != null && user.getPasswd().equals(password);

                if (success) {
                    messageLabel.setText("Successfully login.");
                    baseFrame.sessionManager.login(user.getUid(), user.getName());
                    baseFrame.refresh();
                }
                else {
                    messageLabel.setText("Your username or password is incorrect!");
                }
            }
        });
        panel.add(this.loginButton);

        this.add(panel);
    }
}
