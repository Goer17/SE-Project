package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;

public class CutePanel extends JPanel {

    private CardLayout cardLayout;

    // Constructor to set the default style of the panel
    public CutePanel() {
        this(new CardLayout());
    }

    // Constructor to set the default style of the panel with a specific layout
    public CutePanel(LayoutManager layout) {
        super(layout);
        if (layout instanceof CardLayout) {
            this.cardLayout = (CardLayout) layout;
        }
        setBackground(new Color(255, 240, 245));            // Setting a light pink background
        setForeground(new Color(225, 180, 211));            // Setting a deep pink foreground
        setOpaque(false);                                   // Making the panel transparent
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adding padding
    }

    // Overriding the paintComponent method to support rounded corners
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
    }

    // Overriding the paintBorder method to support custom border
    @Override
    protected void paintBorder(Graphics g) {
        // Do not draw any border
    }

    // Method to add a component to the CardLayout
    public void addCard(Component component, String name) {
        if (cardLayout != null) {
            add(component, name);
        } else {
            throw new UnsupportedOperationException("Current layout is not a CardLayout");
        }
    }

    // Method to show a specific card
    public void showCard(String name) {
        if (cardLayout != null) {
            cardLayout.show(this, name);
        } else {
            throw new UnsupportedOperationException("Current layout is not a CardLayout");
        }
    }

    // Method to get the CardLayout
    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
