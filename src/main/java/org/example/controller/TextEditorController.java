package org.example.controller;

import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class TextEditorController {
    private TextProcessor textProcessor;
    private JTextPane textPane;
    private StyledDocument document;

    public TextEditorController(JTextPane textPane) {
        this.textProcessor = new TextProcessor();
        this.textPane = textPane;
        this.document = textPane.getStyledDocument();
    }

    public void loadText(String text) {
        textProcessor.setContent(text);
        updateTextPane();
    }

    public void saveText() {
        String content = textPane.getText();
        textProcessor.setContent(content);
    }

    private void updateTextPane() {
        textPane.setText(textProcessor.getContent());
    }

    public void countWordsAction() {
        saveText();
        int wordCount = textProcessor.countWords();
        JOptionPane.showMessageDialog(null, "Word Count: " + wordCount, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    public void removeExtraSpacesAction() {
        saveText();
        textProcessor.removeExtraSpaces();
        updateTextPane();
    }

    public void replaceTextAction(String target, String replacement) {
        saveText();
        textProcessor.replaceText(target, replacement);
        updateTextPane();
    }

    public void clearFormattingAction() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
    }

    public void addActionListener(JButton button, String actionType) {
        button.addActionListener(e -> {
            switch (actionType) {
                case "countWords":
                    countWordsAction();
                    break;
                case "removeSpaces":
                    removeExtraSpacesAction();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown action", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}
