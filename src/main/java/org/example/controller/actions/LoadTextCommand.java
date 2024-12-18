package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public class LoadTextCommand extends TextCommand {

    public LoadTextCommand(JTextPane textPane, TextProcessor textProcessor) {
        super(textPane, textProcessor);
    }

    @Override
    public void execute() {
        String text = JOptionPane.showInputDialog(null, "Enter text to load:");
        if (text != null) {
            textProcessor.setContent(text);
            updateTextPane();
            JOptionPane.showMessageDialog(null, "Text loaded successfully.");
        }
    }
}