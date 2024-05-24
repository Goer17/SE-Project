package com.virtual_bank.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;

import com.virtual_bank.gui.common.Button;
import com.virtual_bank.gui.common.CuteLabel;
import com.virtual_bank.gui.common.CutePasswordField;
import com.virtual_bank.gui.common.CuteTextField;

public class RegisterPage extends JPanel {
    private CuteTextField usernameField;
    private CutePasswordField passwordField;
    private Button registerButton;
    private Label messageLabel;

    public RegisterPage(BaseFrame baseFrame) {
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

        this.registerButton = new Button("Register");
        this.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (password.length() < 6) {
                    messageLabel.setText("Password should be more than 6 characters");
                    return;
                }

                User user = new User("#new", username, password, 0);

                boolean success = XMLDBManager.addUser(user);

                if (success) {
                    messageLabel.setText("Successfull register.");
                    baseFrame.sessionManager.login(user.getUid(), user.getName());
                    baseFrame.refresh();
                } else {
                    messageLabel.setText("Your username is duplicate.");
                }
            }
        });
        panel.add(registerButton);

        this.add(panel);
    }
}