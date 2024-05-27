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
    private CuteTextField usernameField;  // 用户名
    private CutePasswordField passwordField;  // 密码
    private Button loginButton;  // 登录按钮
    private Label messageLabel;  // 消息

    public LoginPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // 垂直布局
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // 边框

        panel.add(new CuteLabel("Username:"));  // 用户名
        this.usernameField = new CuteTextField(20);  // 初始化
        panel.add(usernameField);  // 添加到面板
        panel.add(Box.createRigidArea(new Dimension(0, 5)));  // 间隔

        panel.add(new CuteLabel("Password:"));  // 密码
        this.passwordField = new CutePasswordField(20);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.messageLabel = new Label(); // 消息
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        this.loginButton = new Button("Login");  // 登录按钮
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();  // 获取用户名
                String password = new String(passwordField.getPassword());  // 获取密码
                User user = XMLDBManager.findUser(username);  // 查找用户
                boolean success = user != null && user.getPasswd().equals(password);  // 验证

                if (success) {
                    messageLabel.setText("Successfully login.");  // 成功消息
                    baseFrame.sessionManager.login(user.getUid(), user.getName());  // 登录
                    baseFrame.refresh();  // 刷新主页面
                }
                else {
                    messageLabel.setText("Your username or password is incorrect!");  // 错误消息
                }
            }
        });
        panel.add(this.loginButton);

        this.add(panel);  // 添加面板到主面板
    }
}
