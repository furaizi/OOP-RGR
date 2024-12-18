package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public abstract class TextCommand implements TextAction {
    protected final JTextPane textPane;
    protected final TextProcessor textProcessor;

    public TextCommand(JTextPane textPane, TextProcessor textProcessor) {
        this.textPane = textPane;
        this.textProcessor = textProcessor;
    }

    protected void updateTextProcessor() {
        textProcessor.setContent(textPane.getText());
    }

    protected void updateTextPane() {
        textPane.setText(textProcessor.getContent());
    }
}
