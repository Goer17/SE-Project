package com.virtual_bank.gui.common;

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

    // 初始化对话框组件
    private void initComponents() {
        textField = new CuteTextField(20); // 文本输入框
        Button okButton = new Button("OK"); // 确认按钮
        CuteLabel label = new CuteLabel("Please enter the amount:"); // 提示标签

        // 确认事件，获取文本框中的内容，关闭对话框
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = textField.getText(); // 获取文本框内容
                dispose(); // 关闭对话框
            }
        });

        // 面板布局和边距
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 输入面板，提示标签，文本输入框
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);

        // 按钮面板，确认按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        // 添加到主面板
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 对话框内容面板，调整大小，居中显示
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    // 显示对话框，返回输入的内容
    public String showDialog() {
        setVisible(true);
        return result;
    }

    // 测试
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
