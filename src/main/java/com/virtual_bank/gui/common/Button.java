package com.virtual_bank.gui.common;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {

    // 构造函数，设置按钮的默认样式
    public Button(String label) {
        super(label);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));  // 使用更"可爱"的字体
        setBackground(new Color(255, 182, 193));           // 设置淡粉色背景
        setForeground(Color.WHITE);                        // 设置文字颜色
        setFocusPainted(false);                            // 去除焦点的边框
        setContentAreaFilled(false);                       // 不在绘制按钮区域内填充背景色
        setBorderPainted(false);                           // 不绘制边框

        // 鼠标悬停时改变样式
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(239, 103, 125)); // 鼠标悬停时的背景颜色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(255, 182, 193)); // 鼠标离开后恢复背景颜色
            }
        });
    }

    // 重写按钮的绘制方法，以支持圆角
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker()); // 按下按钮时显示更深的颜色
        } else {
            g.setColor(getBackground());
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 绘制圆角矩形
        super.paintComponent(g);
    }

    // 覆盖paintBorder方法，不绘制边框
    @Override
    protected void paintBorder(Graphics g) {
        // 不绘制任何边框
    }
}
