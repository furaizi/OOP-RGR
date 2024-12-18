package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public class ReplaceTextCommand extends TextCommand {

    public ReplaceTextCommand(JTextPane textPane, TextProcessor textProcessor) {
        super(textPane, textProcessor);
    }

    @Override
    public void execute() {
        String target = JOptionPane.showInputDialog("Enter text to replace:");
        String replacement = JOptionPane.showInputDialog("Enter replacement text:");
        if (target != null && replacement != null) {
            updateTextProcessor();
            textProcessor.replaceText(target, replacement);
            updateTextPane();
            JOptionPane.showMessageDialog(null, "Text replaced successfully.");
        }
    }
}
