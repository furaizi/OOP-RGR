package org.example.controller.handlers;

import javax.swing.*;
import javax.swing.text.*;

public class TextAlignmentHandler {

    private final JTextPane textPane;

    public TextAlignmentHandler(JTextPane textPane) {
        this.textPane = textPane;
    }

    public void applyAlignment(int alignment) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, alignment);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
    }
}
