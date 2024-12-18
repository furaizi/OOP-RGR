package org.example.controller.actions;

import javax.swing.*;
import org.example.model.TextProcessor;

public class CountWordsCommand extends TextCommand {

    public CountWordsCommand(JTextPane textPane, TextProcessor textProcessor) {
        super(textPane, textProcessor);
    }

    @Override
    public void execute() {
        updateTextProcessor();
        var wordCount = textProcessor.countWords();
        JOptionPane.showMessageDialog(null, "Word count: " + wordCount);
    }
}