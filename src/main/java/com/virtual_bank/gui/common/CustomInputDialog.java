package com.virtual_bank.gui.common;

import com.virtual_bank.gui.ProfilePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomInputDialog extends JDialog {
    private CuteTextField textField;
    private String result;

    public CustomInputDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();
    }



    private void initComponents() {
        textField = new CuteTextField(20);
        Button okButton = new Button("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = textField.getText();
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public String showDialog() {
        setVisible(true);
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CustomInputDialog dialog = new CustomInputDialog(null, "Custom Input Dialog", true);
                String userInput = dialog.showDialog();
                System.out.println("User input: " + userInput);
            }
        });
    }
}
