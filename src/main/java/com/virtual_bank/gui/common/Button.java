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

    // make default seeing of button
    public Button(String label) {
        super(label);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        // universal "Cute" font
        setBackground(new Color(183, 222, 255));
        // use light blue as background color
        setForeground(Color.WHITE);
        // white font
        setFocusPainted(false);
        // eliminate border around focus(last time click)
        setContentAreaFilled(false);
        // do not fill the content area(will overlay round conor)
        setBorderPainted(false);
        // no boder

        // 鼠标悬停时改变样式
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(87 , 149, 248));
                // when mouse is pointing at the botton
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(183, 222, 255));
                // when mouse leave
            }
        });
    }

    // rewrite to make round cornor
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
            // set color to be darker when button is pushed
        } else {
            g.setColor(getBackground());
        }
        Graphics2D g2 = (Graphics2D) g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        // round corner
        super.paintComponent(g);
    }


}
