package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;

public class CutePasswordField extends JPasswordField {

    // 构造函数，设置默认样式的密码框
    public CutePasswordField() {
        this(2); // 默认列数为2
    }

    // 构造函数，设置指定列数的默认样式的密码框
    public CutePasswordField(int columns) {
        super(columns);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));  // 设置可爱的字体
        setBackground(new Color(255, 153, 0, 60));            // 设置浅粉色背景
        setForeground(new Color(66, 133, 244, 190));            // 设置深粉色前景
        setCaretColor(new Color(255, 153, 0, 60));            // 设置插入符颜色为前景色
        setOpaque(false);                                   // 设置为透明
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(5, 57, 96), 2), // 设置粉色边框和内边距
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
    }

    // 重写 paintComponent 方法以支持圆角
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制圆角矩形背景
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // 使用现有的渲染管道绘制文本
        super.paintComponent(g);
    }

    // 重写 paintBorder 方法以支持圆角
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制圆角矩形边框
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}
