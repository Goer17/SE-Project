package com.virtual_bank.gui.common;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.border.EmptyBorder;

public class CuteLabel extends JLabel {

    // Constructor to set the default style of the label
    public CuteLabel(String text) {
        super(text);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        // 使用"可爱"的字体
        setForeground(new Color(177, 134, 253));
        // 设置文本颜色为浅黄色
        setOpaque(false);
        // 设置为不透明，这样才能绘制自定义背景
        setBorder(new EmptyBorder(10, 20, 10, 20));
        // 添加内边距
    }

    // 重写 paintComponent 方法，以支持圆角
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景透明，不绘制背景部分，只绘制文本
        // g2.setColor(getBackground());
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // 绘制文本，调用现有的渲染管道
        super.paintComponent(g);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        repaint();
        // 重新绘制以确保标签正确更新
    }
}
