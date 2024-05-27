package com.virtual_bank.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.List;
import com.virtual_bank.core.*;
import com.virtual_bank.gui.common.Button;
import com.virtual_bank.gui.common.CustomInputDialog;
import com.virtual_bank.gui.common.CuteTextArea;

public class ProfilePage extends JPanel {
    private JPanel balanceLabel;
    private CuteTextArea transactionsArea;
    private CuteTextArea fixedDepositsArea;
    private Button fixedDepositButton;
    private Button depositButton;
    private Button withdrawButton;

    // 用户个人资料页面
    public ProfilePage(BaseFrame baseFrame) {
        // 检查用户是否登录
        if (baseFrame.sessionManager.isLoggedIn()) {
            // 边距和布局
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            this.setLayout(new BorderLayout());

            String username = baseFrame.sessionManager.getUsername();
            User currentUser = XMLDBManager.findUser(username);
            int money = XMLDBManager.findUser(username).getMoney();
            XMLDBManager.processMaturedDepositsForUser(currentUser.getUid());

            // 余额面板
            balanceLabel = new JPanel();
            balanceLabel.add(new JLabel(getBalanceHtml(username, money)), BorderLayout.CENTER);
            this.add(balanceLabel, BorderLayout.WEST);

            // 按钮
            Button depositButton = new Button("Deposit Money");
            Button withdrawButton = new Button("Withdraw Money");
            fixedDepositButton = new Button("Create Fixed Deposit");

            // 按钮事件
            fixedDepositButton.addActionListener(e -> {
                FixedDepositDialog dialog = new FixedDepositDialog(baseFrame, currentUser);

                // 定期存款后刷新
                dialog.addFixedDepositListener(evt -> {
                    baseFrame.refresh();
                    baseFrame.switchToPanel("Profile");
                });

                dialog.setVisible(true);
            });
            
            depositButton.addActionListener(e -> updateBalance(true, baseFrame));
            withdrawButton.addActionListener(e -> updateBalance(false, baseFrame));

            // 添加到按钮面板
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(depositButton);
            buttonPanel.add(withdrawButton);
            buttonPanel.add(fixedDepositButton);
            this.add(buttonPanel, BorderLayout.SOUTH);

            // 交易记录
            transactionsArea = new CuteTextArea(10, 30);
            transactionsArea.setEditable(false);
            updateTransactionsDisplay(currentUser.getUid());
            JScrollPane transactionsScrollPane = new JScrollPane(transactionsArea);

            // 定期存款显示
            fixedDepositsArea = new CuteTextArea(10, 30);
            fixedDepositsArea.setEditable(false);
            updateFixedDepositsDisplay(currentUser.getUid());
            JScrollPane fixedDepositsScrollPane = new JScrollPane(fixedDepositsArea);

            // 创建上两个面板
            JPanel displayPanel = new JPanel(new GridLayout(1, 2));
            displayPanel.add(transactionsScrollPane);
            displayPanel.add(fixedDepositsScrollPane);
            this.add(displayPanel, BorderLayout.CENTER);

            // 重绘
            this.revalidate();
            this.repaint();
        } else {
            // 未登录时显示
            this.add(new JLabel("<html><h2>Please login first :)</h2></html>"), BorderLayout.CENTER);
        }
    }

    // 显示用户余额
    private String getBalanceHtml(String username, int money) {
        return "<html><h3>Profile</h3><br><h3>" + username + "</h3><br>" +
               "<h3>Your account balance: " + "<span style='color: blue;'>" +
               money + "</span></h3></html>";
    }

    // 更新余额
    private void updateBalance(boolean isDeposit, BaseFrame baseFrame) {
        String transactionType = isDeposit ? "Deposit" : "Withdraw";
        CustomInputDialog dialog = new CustomInputDialog(null, transactionType, true);
        String input = dialog.showDialog();

        // 取消输入则返回
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

            // 余额检测
            if (!isDeposit && newBalance < 0) {
                JOptionPane.showMessageDialog(this, "Insufficient funds!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentUser.setMoney(newBalance);
            XMLDBManager.saveUser(currentUser);

            // 创建新的交易记录
            Transaction newTransaction = new Transaction(currentUser.getUid(), transactionType, amount);
            XMLDBManager.addTransaction(newTransaction);
            
            baseFrame.refresh();
            baseFrame.switchToPanel("Profile");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 更新交易记录
    private void updateTransactionsDisplay(String uid) {
        List<Transaction> transactions = XMLDBManager.getTransactionsForUser(uid);
        transactionsArea.setText("");
        for (Transaction transaction : transactions) {
            transactionsArea.append(transaction.getDate() + " - " + transaction.getType() + " - $" + transaction.getAmount() + "\n");
        }
    }

    // 更新定期存款
    private void updateFixedDepositsDisplay(String uid) {
        List<FixedDeposit> fixedDeposits = XMLDBManager.getFixedDepositsForUser(uid);
        fixedDepositsArea.setText("");
        for (FixedDeposit deposit : fixedDeposits) {
            double maturityAmount = deposit.calculateMaturityAmount();
            fixedDepositsArea.append(deposit.getStartDate() + " - " + deposit.getEndDate() +
                                     " - Amount: $" + String.format("%.2f", deposit.getAmount()) +
                                     " - Rate: " + String.format("%.2f%%", deposit.getAnnualInterestRate() * 100) +
                                     " - Maturity Amount: $" + String.format("%.2f", maturityAmount) + "\n");
        }
    }
}
