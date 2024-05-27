package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CuteOptionPane {

    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        // Create a JPanel to contain the message and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 245));

        // Light pink background

        // Create a JLabel to display the message
        JLabel messageLabel = new JLabel(message.toString());
        messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        // Cute font
        messageLabel.setForeground(new Color(174, 221, 239));
        // Deep pink foreground
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        // Add some padding between message and buttons
        panel.add(Box.createVerticalStrut(10));

        // Create an "OK" button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        // Cute font
        okButton.setBackground(new Color(1, 81, 108));
        // Dark green background
        okButton.setForeground(Color.WHITE); // White text
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog when OK button is clicked
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(panel);
                dialog.dispose();
            }
        });
        panel.add(okButton);

        // Create a JDialog to display the panel as a dialog
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        // Test the CuteOptionPane
        CuteOptionPane.showMessageDialog(null, "Hello, this is a cute message!", "Cute Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
