package com.virtual_bank.gui.common;

import javax.swing.*;
import java.awt.*;

public class CuteTextArea extends JTextArea {

    // Constructor to set the default style of the text area
    public CuteTextArea() {
        this(10, 40); // Default rows and columns
    }

    // Constructor to set the default style of the text area with specific rows and columns
    public CuteTextArea(int rows, int columns) {
        super(rows, columns);
        setFont(new Font("Comic Sans MS", Font.PLAIN, 10));  // Setting a cute font
        setBackground(new Color(217, 245, 202));            // Setting a cute green background
        setForeground(new Color(0, 92, 203));                         // Setting text color to black
        setCaretColor(Color.BLACK);                         // Setting caret color to black
        setLineWrap(true);                                  // Enable line wrapping
        setWrapStyleWord(true);                             // Wrap at word boundaries
        setOpaque(true);                                    // Make the text area opaque
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Adding padding
    }
}
