package com.virtual_bank.gui;

import javax.swing.*;

import com.virtual_bank.core.SessionManager;

public class Main {
    public static void main(String[] args) {
        SessionManager sessionManager = SessionManager.getInstance(); // 获取 SessionManager 的单例实例

        SwingUtilities.invokeLater(() -> { // 运行GUI 
            new BaseFrame(sessionManager).setVisible(true);
        });
    }
}
