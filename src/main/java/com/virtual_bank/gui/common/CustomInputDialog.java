package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomInputDialog extends JDialog {
    private CuteTextField textField;
    private String result;

    public CustomInputDialog(Frame parent, String title, boolean modal) {
        //this is to make dialog in deposit screen more cartoon, take input from the keyboard and output as a string
        super(parent, title, modal);
        initComponents();
    }

    // initialization
    private void initComponents() {
        textField = new CuteTextField(20);
        // use exsting cute textfield
        Button okButton = new Button("OK");
        // use esisting button
        CuteLabel label = new CuteLabel("Please enter the amount:");
        // use cute label

        // when click close the dialog
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = textField.getText();
                // get the input
                dispose();
                // close that
            }
        });

        //set up a new panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // label at north,textfield at center
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);

        // button at right(flow layout)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        // two sub_main panel
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // set to the center
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    // back to the last dialog and return the result
    public String showDialog() {
        setVisible(true);
        return result;
    }


}
