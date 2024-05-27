package com.virtual_bank.gui;
import com.virtual_bank.core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.virtual_bank.core.User;
import com.virtual_bank.core.XMLDBManager;
import com.virtual_bank.gui.common.Button;
import com.virtual_bank.gui.common.CuteLabel;
import com.virtual_bank.gui.common.CuteTextField;

public class FixedDepositDialog extends JDialog {
    private CuteTextField depositAmountField;  
    private CuteTextField depositDurationField;  
    private Button createButton; 
    private JPanel contentPanel; 
    private CuteLabel headerLabel;  

    private List<ActionListener> listeners = new ArrayList<>();  // Event list

    public void addFixedDepositListener(ActionListener listener) {
        listeners.add(listener);  // Fixed deposit event
    }

    private void notifyFixedDepositCreated() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "FixedDepositCreated");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event);  // Notify listeners that fixed deposit has been created
        }
    }

    public FixedDepositDialog(Frame owner, User currentUser) {
        super(owner, "Create Fixed Deposit", true);  // Fixed deposit dialog
        setSize(700, 300);
        setLocationRelativeTo(owner);  // Center on parent window
        getContentPane().setLayout(new BorderLayout(10, 10));  // Layout

        // Title panel
        headerLabel = new CuteLabel("New Fixed Deposit");  // New fixed deposit title
        getContentPane().add(headerLabel, BorderLayout.NORTH);

        // Main content panel
        contentPanel =  new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 5));  
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));  

        // Amount label and field
        CuteLabel amountLabel = new CuteLabel("Amount:");  // Amount label
        depositAmountField = new CuteTextField(10);  // Amount input field
        contentPanel.add(amountLabel);
        contentPanel.add(depositAmountField);

        // Duration label and field
        CuteLabel durationLabel = new CuteLabel("Duration (months):");  // Duration label
        depositDurationField =  new CuteTextField(10);  // Duration input field
        contentPanel.add(durationLabel);
        contentPanel.add(depositDurationField);

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createButton = new Button("Create Deposit");  // Deposit button
        createButton.addActionListener(e -> createFixedDeposit(currentUser));  // Add button event
        buttonPanel.add(createButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Close operation
    }

    private void createFixedDeposit(User currentUser) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());  // Get entered amount
            int duration = Integer.parseInt(depositDurationField.getText());  // Get entered duration

            if (amount <= 0 || duration <= 0) {
                JOptionPane.showMessageDialog(this, "Amount and duration must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;  // amount or duration are not positive
            }

            LocalDate startDate = LocalDate.now();  // Get current date as start date
            LocalDate endDate = startDate.plusMonths(duration);  // Calculate end date
            double annualInterestRate = 0.05;  // Fixed annual interest rate at 5%

            FixedDeposit newDeposit = new FixedDeposit(currentUser.getUid(), amount, annualInterestRate, startDate, endDate);
            XMLDBManager.addFixedDeposit(newDeposit);  // Add new fixed deposit into database

            JOptionPane.showMessageDialog(this, "Fixed Deposit Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            notifyFixedDepositCreated();  // Notify listeners that fixed deposit has been created
            dispose();  // Close dialog

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);  // error message
        }
    }
}
