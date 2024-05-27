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

//注册界面
public class RegisterPage extends JPanel {
    private CuteTextField usernameField;
    private CutePasswordField passwordField;
    private Button registerButton;
    private Label messageLabel;

    public RegisterPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 设置布局为垂直
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 边框

        panel.add(new CuteLabel("Username:")); // 用户名
        this.usernameField = new CuteTextField(20);
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // 垂直间隔

        panel.add(new CuteLabel("Password:")); // 密码
        this.passwordField = new CutePasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.messageLabel = new Label(); // 消息
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.registerButton = new Button("Register"); // 注册按钮
        this.registerButton.addActionListener(new ActionListener() { // 点击事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); // 获取输入的用户名
                String password = new String(passwordField.getPassword()); // 获取输入的密码
                if (password.length() < 6) { // 检查密码长度
                    messageLabel.setText("Password should be more than 6 characters"); // 提示密码长度不足
                    return; // 终止方法
                }

                User user = new User("#new", username, password, 0);

                boolean success = XMLDBManager.addUser(user); // 用户添加到数据库

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
