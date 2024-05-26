package com.virtual_bank.gui;

import javax.swing.*;
import com.virtual_bank.core.SessionManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.virtual_bank.gui.common.*;
import com.virtual_bank.gui.common.Button;

// 基础框架类，继承自 JFrame 并实现 ActionListener 接口
public class BaseFrame extends JFrame implements ActionListener {
    protected JPanel navigationPanel; // 导航面板
    protected JPanel contentPanel; // 内容面板
    protected CardLayout cardLayout; // 卡片布局，用于切换不同的内容面板
    protected SessionManager sessionManager; // 会话管理器

    // 构造函数，初始化基础框架
    public BaseFrame(SessionManager sessionManager) {
        this.sessionManager = sessionManager;

        // 初始化卡片布局和内容面板
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);

        // 初始化导航面板
        this.navigationPanel = new JPanel();
        this.navigationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // 设置窗口属性
        this.setTitle("Virtual Bank");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // 初始化导航面板和内容面板
        this.initializeNavigationPanel();
        this.initializeContentPanel();

        // 根据登录状态切换到相应面板
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

    // 初始化导航面板，添加导航按钮
    private void initializeNavigationPanel() {
        // 根据用户登录状态和角色添加导航按钮
        if (this.sessionManager.isLoggedIn()) {
            if (!this.sessionManager.getUsername().equals("admin")) {
                this.addNavigationButton("Home");
                this.addNavigationButton("Profile");
            } else {
                // 超级用户
                this.addNavigationButton("Admin");
            }
        }
        if (!this.sessionManager.isLoggedIn()) {
            this.addNavigationButton("Login");
            this.addNavigationButton("Register");
        } else {
            this.addNavigationButton("Logout");
        }
        // 将导航面板添加到窗口北部
        this.add(navigationPanel, BorderLayout.NORTH);
    }

    // 添加导航按钮
    protected void addNavigationButton(String name) {
        Button button = new Button(name);
        button.addActionListener(this);
        this.navigationPanel.add(button);
    }

    // 初始化内容面板，添加各个功能页面
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
        // 将内容面板添加到窗口中心
        this.add(this.contentPanel, BorderLayout.CENTER);
    }

    // 刷新页面，重新初始化导航和内容面板
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
        // 处理导航事件，切换到相应的面板
        this.switchToPanel(e.getActionCommand());
    }

    // 切换到指定的面板
    protected void switchToPanel(String panelName) {
        if (!"Logout".equals(panelName)) {
            this.cardLayout.show(contentPanel, panelName);
        } else {
            // 处理登出操作
            this.sessionManager.logout();
            this.refresh();
        }
    }
}
