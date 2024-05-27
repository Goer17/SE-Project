package com.virtual_bank.gui;

import javax.swing.*;

import com.virtual_bank.core.SessionManager;

public class Main {
    public static void main(String[] args) {
        SessionManager sessionManager = SessionManager.getInstance(); 

        SwingUtilities.invokeLater(() -> { 
            new BaseFrame(sessionManager).setVisible(true);
        });
    }
}
