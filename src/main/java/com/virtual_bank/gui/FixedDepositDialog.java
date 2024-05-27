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
// 包导入

public class FixedDepositDialog extends JDialog {
    private CuteTextField depositAmountField;  // 存款金额
    private CuteTextField depositDurationField;  // 存款期限
    private Button createButton;  // 创建按钮
    private JPanel contentPanel;  // 内容面板
    private CuteLabel headerLabel;  // 标题标签

    private List<ActionListener> listeners = new ArrayList<>();  // 事件列表

    public void addFixedDepositListener(ActionListener listener) {
        listeners.add(listener);  // 定期存款事件
    }

    private void notifyFixedDepositCreated() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "FixedDepositCreated");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event);  // 通知监听器定期存款已创建
        }
    }

    public FixedDepositDialog(Frame owner, User currentUser) {
        super(owner, "Create Fixed Deposit", true);  // 定期存款对话框
        setSize(700, 300);
        setLocationRelativeTo(owner);  // 父窗口居中
        getContentPane().setLayout(new BorderLayout(10, 10));  // 布局

        // 标题面板
        headerLabel = new CuteLabel("New Fixed Deposit");  // 新的定期存款标题
        getContentPane().add(headerLabel, BorderLayout.NORTH);

        // 主内容面板
        contentPanel =  new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 5));  // 动态行，2列
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));  // 边框

        // 金额标签和字段
        CuteLabel amountLabel = new CuteLabel("Amount:");  // 金额标签
        depositAmountField = new CuteTextField(10);  // 金额输入字段
        contentPanel.add(amountLabel);
        contentPanel.add(depositAmountField);

        // 期限标签和字段
        CuteLabel durationLabel = new CuteLabel("Duration (months):");  // 期限标签
        depositDurationField =  new CuteTextField(10);  // 期限输入字段
        contentPanel.add(durationLabel);
        contentPanel.add(depositDurationField);

        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createButton = new Button("Create Deposit");  // 存款按钮
        createButton.addActionListener(e -> createFixedDeposit(currentUser));  // 添加按钮事件
        buttonPanel.add(createButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // 关闭操作
    }

    private void createFixedDeposit(User currentUser) {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());  // 获取输入金额
            int duration = Integer.parseInt(depositDurationField.getText());  // 获取输入期限

            if (amount <= 0 || duration <= 0) {
                JOptionPane.showMessageDialog(this, "Amount and duration must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;  // 显示错误信息（金额或期限为非正数）
            }

            LocalDate startDate = LocalDate.now();  // 获取当前日期作为开始日期
            LocalDate endDate = startDate.plusMonths(duration);  // 计算结束日期
            double annualInterestRate = 0.05;  // 固定年利率为5%

            FixedDeposit newDeposit = new FixedDeposit(currentUser.getUid(), amount, annualInterestRate, startDate, endDate);
            XMLDBManager.addFixedDeposit(newDeposit);  // 添加新的定期存款

            JOptionPane.showMessageDialog(this, "Fixed Deposit Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            notifyFixedDepositCreated();  // 通知监听器定期存款已创建
            dispose();  // 关闭对话框

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);  // 输入错误信息
        }
    }
}
