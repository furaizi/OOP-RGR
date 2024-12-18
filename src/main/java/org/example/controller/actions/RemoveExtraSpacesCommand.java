package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public class RemoveExtraSpacesCommand extends TextCommand {

    public RemoveExtraSpacesCommand(JTextPane textPane, TextProcessor textProcessor) {
        super(textPane, textProcessor);
    }

    @Override
    public void execute() {
        updateTextProcessor();
        textProcessor.removeExtraSpaces();
        updateTextPane();
        JOptionPane.showMessageDialog(null, "Extra spaces removed.");
    }
}