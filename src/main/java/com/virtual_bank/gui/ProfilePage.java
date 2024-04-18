package com.virtual_bank.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import java.util.List;

import com.virtual_bank.core.XMLDBManager;
import com.virtual_bank.core.*;


public class ProfilePage extends JPanel {
    private JPanel balanceLabel;
    private JTextArea transactionsArea;

    public ProfilePage(BaseFrame baseFrame) {
        if (baseFrame.sessionManager.isLoggedIn()) {
            this.setLayout(new BorderLayout());
            String username = baseFrame.sessionManager.getUsername();
            User currentUser = XMLDBManager.findUser(username);
            int money = XMLDBManager.findUser(username).getMoney();

            balanceLabel = new JPanel();
            balanceLabel.add(new JLabel(getBalanceHtml(username, money)), BorderLayout.CENTER);
            this.add(balanceLabel, BorderLayout.WEST);

            JButton depositButton = new JButton("Deposit Money");
            JButton withdrawButton = new JButton("Withdraw Money");

            depositButton.addActionListener(e -> updateBalance(true, baseFrame));
            withdrawButton.addActionListener(e -> updateBalance(false, baseFrame));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(depositButton);
            buttonPanel.add(withdrawButton);
            this.add(buttonPanel, BorderLayout.SOUTH);

            transactionsArea = new JTextArea(10, 30);
            transactionsArea.setEditable(false);
            updateTransactionsDisplay(currentUser.getUid()); 

            JScrollPane scrollPane = new JScrollPane(transactionsArea);
            this.add(scrollPane, BorderLayout.EAST);

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
    

    private void updateBalance(boolean isDeposit,BaseFrame baseFrame) {
        String transactionType = isDeposit ? "Deposit" : "Withdraw";
        String input = JOptionPane.showInputDialog(this, "Enter amount to " + transactionType + ":");
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
            
            balanceLabel.removeAll();
            balanceLabel.add(new JLabel(this.getBalanceHtml(currentUser.getName(), currentUser.getMoney())), BorderLayout.CENTER);
            updateTransactionsDisplay(currentUser.getUid());
            this.revalidate();
            this.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransactionsDisplay(String uid) {
        List<Transaction> transactions = XMLDBManager.getTransactionsForUser(uid);
        transactionsArea.setText(""); // 清空现有记录
        for (Transaction transaction : transactions) {
            transactionsArea.append(transaction.getDate() + " - " + transaction.getType() + " - $" + transaction.getAmount() + "\n");
        }
    }
}
