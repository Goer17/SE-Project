package com.virtual_bank.gui.common;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CuteList<E> extends JList<E> {

    // Constructor to set the default style of the list
    public CuteList(ListModel<E> model) {
        super(model);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        // Using a "cute" font
        setBackground(new Color(246, 242, 194, 255));
        // Setting a light pink background
        setForeground(new Color(66, 133, 244, 190));
        // Setting text color to deep pink
        setSelectionBackground(new Color(115, 12, 12));
        // Setting selection background color to light pink
        setSelectionForeground(Color.WHITE);
        // Setting selection text color to white
        setCellRenderer(new CuteListCellRenderer<>());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(246, 242, 194, 255));

                // Slightly darker pink when mouse hovers
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(246, 242, 194, 255));
                // Restore the background color when mouse exits
            }
        });
    }

    // Custom cell renderer to support rounded corners and additional styling
    private static class CuteListCellRenderer<E> extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (component instanceof JLabel label) {
                label.setOpaque(false);
                // Making label transparent to allow custom painting
                label.setBorder(new EmptyBorder(10, 20, 10, 20));
                // Adding padding
            }
            return component;
        }

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
    }
}
