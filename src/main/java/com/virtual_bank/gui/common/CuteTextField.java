package com.virtual_bank.gui.common;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CuteTextField extends JTextField {

    // Constructor to set the default style of the text field
    public CuteTextField(int columns) {
        super(columns);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));  // Using a "cute" font
        setBackground(new Color(153, 227, 109, 60));            // Setting a light pink background
        setForeground(new Color(76, 188, 206, 255));            // Setting text color to deep pink
        setCaretColor(new Color(153, 227, 109, 60));            // Setting caret color to deep pink
        setOpaque(false);                                   // Making the text field transparent
//        setBorder(new EmptyBorder(10, 10, 10, 10));         // Adding padding

        // Add a focus listener to change background color when focused
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(new Color(194, 243, 147));    // Slightly darker pink when focused
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(new Color(153, 227, 109, 60));    // Restore the background color when focus is lost
                repaint();
            }
        });

        // Add a document listener to repaint the text field when text changes
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
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
