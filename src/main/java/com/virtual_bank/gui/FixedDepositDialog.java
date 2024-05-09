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


public class FixedDepositDialog extends JDialog {
    private JTextField depositAmountField;
    private JTextField depositDurationField;
    private JButton createButton;
    private JPanel contentPanel;
    private JLabel headerLabel;

    private List<ActionListener> listeners = new ArrayList<>();

    public void addFixedDepositListener(ActionListener listener) {
        listeners.add(listener);
    }

    private void notifyFixedDepositCreated() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "FixedDepositCreated");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event);
        }
    }


    public FixedDepositDialog(Frame owner, User currentUser) {
        super(owner, "Create Fixed Deposit", true);
        setSize(350, 220);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Header panel with title
        headerLabel = new JLabel("New Fixed Deposit", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(headerLabel, BorderLayout.NORTH);

        // Main content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 5)); // Dynamic rows, 2 columns
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Adding amount label and field
        JLabel amountLabel = new JLabel("Amount:");
        depositAmountField = new JTextField();
        contentPanel.add(amountLabel);
        contentPanel.add(depositAmountField);

        // Adding duration label and field
        JLabel durationLabel = new JLabel("Duration (months):");
        depositDurationField = new JTextField();
        contentPanel.add(durationLabel);
        contentPanel.add(depositDurationField);

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createButton = new JButton("Create Deposit");
        createButton.setFont(new Font("Serif", Font.PLAIN, 15));
        createButton.addActionListener(e -> createFixedDeposit(currentUser));
        buttonPanel.add(createButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createFixedDeposit(User currentUser) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());
            int duration = Integer.parseInt(depositDurationField.getText());

            if (amount <= 0 || duration <= 0) {
                JOptionPane.showMessageDialog(this, "Amount and duration must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusMonths(duration);
            double annualInterestRate = 0.05; // Assuming a fixed 5% annual interest rate

            FixedDeposit newDeposit = new FixedDeposit(currentUser.getUid(), amount, annualInterestRate, startDate, endDate);
            XMLDBManager.addFixedDeposit(newDeposit);

            JOptionPane.showMessageDialog(this, "Fixed Deposit Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            notifyFixedDepositCreated(); 
            dispose(); // Close the dialog

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
