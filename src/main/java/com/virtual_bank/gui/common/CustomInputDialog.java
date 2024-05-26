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
        textField = new CuteTextField(20); // 创建一个文本输入框
        Button okButton = new Button("OK"); // 创建一个确认按钮
        CuteLabel label = new CuteLabel("Please enter the amount:"); // 创建一个提示标签

        // 确认按钮的事件监听器，用于获取文本框中的内容并关闭对话框
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = textField.getText(); // 获取文本框中的内容
                dispose(); // 关闭对话框
            }
        });

        // 创建面板，并设置布局和边距
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 创建输入面板，包含提示标签和文本输入框，并设置布局
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);

        // 创建按钮面板，包含确认按钮，并设置布局
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        // 将输入面板和按钮面板添加到主面板中
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 设置对话框的内容面板，并自动调整对话框大小，将对话框居中显示
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    // 显示对话框，并返回用户输入的内容
    public String showDialog() {
        setVisible(true);
        return result;
    }

    // 主方法用于测试对话框的功能
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 创建一个自定义输入对话框实例并显示
                CustomInputDialog dialog = new CustomInputDialog(null, "Custom Input Dialog", true);
                String userInput = dialog.showDialog();
                System.out.println("User input: " + userInput); // 输出用户输入的内容
            }
        });
    }
}
