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
        CuteLabel label = new CuteLabel("Please enter the amount:");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = textField.getText();
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

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
