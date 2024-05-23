package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;

public class CutePasswordField extends JPasswordField {

    // Constructor to set the default style of the password field
    public CutePasswordField() {
        this(2); // Default column size
    }

    // Constructor to set the default style of the password field with specific columns
    public CutePasswordField(int columns) {
        super(columns);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));  // Setting a cute font
        setBackground(new Color(255, 240, 245));            // Setting a light pink background
        setForeground(new Color(241, 156, 103));            // Setting a deep pink foreground
        setCaretColor(new Color(239, 198, 153));            // Setting the caret color to match the foreground
        setOpaque(false);                                   // Making the field transparent
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(176, 70, 8), 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));                                                 // Adding a pink border and padding
    }

    // Overriding the paintComponent method to support rounded corners
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Draw the text with the existing rendering pipeline
        super.paintComponent(g);
    }

    // Overriding the paintBorder method to support rounded corners
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle border
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}
