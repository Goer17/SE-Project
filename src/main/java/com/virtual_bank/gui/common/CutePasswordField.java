package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;

public class CutePasswordField extends JPasswordField {

    // 密码框
    public CutePasswordField() {
        this(2);
    }

    // 密码框
    public CutePasswordField(int columns) {
        super(columns);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));  // 字体
        setBackground(new Color(255, 153, 0, 60));            // 背景
        setForeground(new Color(66, 133, 244, 190));            // 前景
        setCaretColor(new Color(255, 153, 0, 60));            // 插入符
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(5, 57, 96), 2), // 粉色边框，内边距
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
    }

    // 圆角
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
    }

    // 圆角
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 边框
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}
