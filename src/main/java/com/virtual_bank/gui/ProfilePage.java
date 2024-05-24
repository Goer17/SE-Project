package com.virtual_bank.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import java.util.List;

import com.virtual_bank.core.*;
import com.virtual_bank.gui.common.Button;
//import com.virtual_bank.gui.common.CutePanel;
import com.virtual_bank.gui.common.CuteTextArea;

public class ProfilePage extends JPanel {
    private JPanel balanceLabel;
    private CuteTextArea transactionsArea;
    private CuteTextArea fixedDepositsArea;
    private Button fixedDepositButton;


    public ProfilePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setLayout(new BorderLayout());
            String username = baseFrame.sessionManager.getUsername();
            User currentUser = XMLDBManager.findUser(username);
            int money = XMLDBManager.findUser(username).getMoney();
            XMLDBManager.processMaturedDepositsForUser(currentUser.getUid());

            balanceLabel = new JPanel();
            balanceLabel.add(new JLabel(getBalanceHtml(username, money)), BorderLayout.CENTER);
            this.add(balanceLabel, BorderLayout.WEST);

            Button depositButton = new Button("Deposit Money");
            Button withdrawButton = new Button("Withdraw Money");
            fixedDepositButton = new Button("Create Fixed Deposit");

            fixedDepositButton.addActionListener(e -> {
                FixedDepositDialog dialog = new FixedDepositDialog(baseFrame, currentUser);


                dialog.addFixedDepositListener(evt -> {
                    baseFrame.refresh();
                    baseFrame.switchToPanel("Profile");
                });

                dialog.setVisible(true);
            });


            depositButton.addActionListener(e -> updateBalance(true, baseFrame));
            withdrawButton.addActionListener(e -> updateBalance(false, baseFrame));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(depositButton);
            buttonPanel.add(withdrawButton);
            buttonPanel.add(fixedDepositButton);
            this.add(buttonPanel, BorderLayout.SOUTH);

            transactionsArea = new CuteTextArea(10, 30);
            transactionsArea.setEditable(false);
            updateTransactionsDisplay(currentUser.getUid()); 
            JScrollPane transactionsScrollPane = new JScrollPane(transactionsArea);

            // Fixed deposits display setup
            fixedDepositsArea = new CuteTextArea(10, 30);
            fixedDepositsArea.setEditable(false);
            updateFixedDepositsDisplay(currentUser.getUid());
            JScrollPane fixedDepositsScrollPane = new JScrollPane(fixedDepositsArea);

            // Panel to hold both transactions and fixed deposits
            JPanel displayPanel = new JPanel(new GridLayout(1, 2));
            displayPanel.add(transactionsScrollPane);
            displayPanel.add(fixedDepositsScrollPane);
            this.add(displayPanel, BorderLayout.CENTER);

            this.revalidate();
            this.repaint();
        }
        else {
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER); 
        }
    }

    private String getBalanceHtml(String username, int money) {
        return "<html><h3>Profile</h3><br><h3>" + username + "</h3><br>" +
               "<h3>Your account balance: " + "<span style='color: blue;'>" +
               money + "</span></h3></html>";
    }
    

    private void updateBalance(boolean isDeposit, BaseFrame baseFrame) {
        String transactionType = isDeposit ? "Deposit" : "Withdraw";
        String input = JOptionPane.showInputDialog(this, "Enter amount to " + transactionType + ":");

        if (input == null) {
            return; 
        }

        try {
            int amount = Integer.parseInt(input);
            if (amount < 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User currentUser = XMLDBManager.findUser(baseFrame.sessionManager.getUsername());
            int currentMoney = currentUser.getMoney();
            int newBalance = isDeposit ? currentMoney + amount : currentMoney - amount;

            if (!isDeposit && newBalance < 0) {
                JOptionPane.showMessageDialog(this, "Insufficient funds!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentUser.setMoney(newBalance);
            XMLDBManager.saveUser(currentUser);

            Transaction newTransaction = new Transaction(currentUser.getUid(), transactionType, amount);
            XMLDBManager.addTransaction(newTransaction);
            
            baseFrame.refresh();
            baseFrame.switchToPanel("Profile");
            // balanceLabel.removeAll();
            // balanceLabel.add(new JLabel(this.getBalanceHtml(currentUser.getName(), currentUser.getMoney())), BorderLayout.CENTER);
            // updateTransactionsDisplay(currentUser.getUid());
            // this.revalidate();
            // this.repaint();
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransactionsDisplay(String uid) {
        List<Transaction> transactions = XMLDBManager.getTransactionsForUser(uid);
        transactionsArea.setText(""); 
        for (Transaction transaction : transactions) {
            transactionsArea.append(transaction.getDate() + " - " + transaction.getType() + " - $" + transaction.getAmount() + "\n");
        }
    }

    private void updateFixedDepositsDisplay(String uid) {
        List<FixedDeposit> fixedDeposits = XMLDBManager.getFixedDepositsForUser(uid);
        fixedDepositsArea.setText(""); // Clear previous entries
        for (FixedDeposit deposit : fixedDeposits) {
            double maturityAmount = deposit.calculateMaturityAmount(); // Calculate the total amount at maturity
            fixedDepositsArea.append(deposit.getStartDate() + " - " + deposit.getEndDate() +
                                     " - Amount: $" + String.format("%.2f", deposit.getAmount()) +
                                     " - Rate: " + String.format("%.2f%%", deposit.getAnnualInterestRate() * 100) +
                                     " - Maturity Amount: $" + String.format("%.2f", maturityAmount) + "\n");
        }
    }
    


}
