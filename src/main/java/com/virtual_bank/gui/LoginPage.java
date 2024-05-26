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
    private CuteTextField usernameField;  // 用户名输入字段
    private CutePasswordField passwordField;  // 密码输入字段
    private Button loginButton;  // 登录按钮
    private Label messageLabel;  // 消息标签

    public LoginPage(BaseFrame baseFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // 使用垂直布局
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // 设置边框

        panel.add(new CuteLabel("Username:"));  // 添加用户名标签
        this.usernameField = new CuteTextField(20);  // 初始化用户名输入字段
        panel.add(usernameField);  // 添加用户名输入字段到面板
        panel.add(Box.createRigidArea(new Dimension(0, 5)));  // 添加间隔

        panel.add(new CuteLabel("Password:"));  // 添加密码标签
        this.passwordField = new CutePasswordField(20);  // 初始化密码输入字段
        panel.add(passwordField);  // 添加密码输入字段到面板
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // 添加间隔

        this.messageLabel = new Label();  // 初始化消息标签
        panel.add(messageLabel);  // 添加消息标签到面板
        panel.add(Box.createRigidArea(new Dimension(0, 5)));  // 添加间隔

        this.loginButton = new Button("Login");  // 初始化登录按钮
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();  // 获取输入的用户名
                String password = new String(passwordField.getPassword());  // 获取输入的密码
                User user = XMLDBManager.findUser(username);  // 查找用户
                boolean success = user != null && user.getPasswd().equals(password);  // 验证用户名和密码

                if (success) {
                    messageLabel.setText("Successfully login.");  // 显示成功消息
                    baseFrame.sessionManager.login(user.getUid(), user.getName());  // 执行登录操作
                    baseFrame.refresh();  // 刷新主页面
                }
                else {
                    messageLabel.setText("Your username or password is incorrect!");  // 显示错误消息
                }
            }
        });
        panel.add(this.loginButton);  // 添加登录按钮到面板

        this.add(panel);  // 添加面板到主面板
    }
}
