package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public class SaveTextCommand extends TextCommand {

    public SaveTextCommand(JTextPane textPane, TextProcessor textProcessor) {
        super(textPane, textProcessor);
    }

    @Override
    public void execute() {
        updateTextProcessor();
        JOptionPane.showMessageDialog(null, "Text saved successfully.");
    }
}
