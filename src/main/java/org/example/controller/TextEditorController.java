package org.example.controller;

import org.example.controller.handlers.TextAlignmentHandler;
import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class TextEditorController {
    private final JTextPane textPane;
    private final TextProcessor textProcessor;
    private final TextAlignmentHandler alignmentHandler;

    private static final String DEFAULT_FONT = "Arial";
    private static final int DEFAULT_FONT_SIZE = 24;

    public TextEditorController(JTextPane textPane) {
        this.textPane = textPane;
        this.textProcessor = new TextProcessor();
        this.alignmentHandler = new TextAlignmentHandler(textPane);
    }

    // Команды
    public void saveText() {
        executeCommand(() -> JOptionPane.showMessageDialog(null, "Text saved successfully."),
                       () -> textProcessor.setContent(textPane.getText()));
    }

    public void loadText() {
        var text = Optional.ofNullable(JOptionPane.showInputDialog("Enter text to load:"));
        text.ifPresent(content -> executeCommand(
                () -> textPane.setText(content),
                () -> textProcessor.setContent(content)
        ));
    }

    public void countWords() {
        executeCommand(() -> JOptionPane.showMessageDialog(null, "Word count: " + textProcessor.countWords()),
                       () -> textProcessor.setContent(textPane.getText()));
    }

    public void removeExtraSpaces() {
        executeCommand(() -> JOptionPane.showMessageDialog(null, "Extra spaces removed."),
                       textProcessor::removeExtraSpaces);
    }

    public void replaceText() {
        var target = Objects.requireNonNull(JOptionPane.showInputDialog("Enter text to replace:"));
        var replacement = Objects.requireNonNull(JOptionPane.showInputDialog("Enter replacement text:"));
        executeCommand(() -> textProcessor.replaceText(target, replacement));
    }

    // Выравнивание
    public void applyTextAlignment(int alignment) {
        alignmentHandler.applyAlignment(alignment);
    }

    // Форматирование
    public void setFontFamily(String fontName) {
        Optional.ofNullable(fontName).ifPresent(
                name -> new StyledEditorKit.FontFamilyAction("font-family", name).actionPerformed(null));
    }

    public void setFontSize(int size) {
        new StyledEditorKit.FontSizeAction("font-size", size).actionPerformed(null);
    }

    public void toggleBold() {
        new StyledEditorKit.BoldAction().actionPerformed(null);
    }

    public void toggleItalic() {
        new StyledEditorKit.ItalicAction().actionPerformed(null);
    }

    public void chooseTextColor() {
        Optional.ofNullable(JColorChooser.showDialog(null, "Choose Text Color", Color.BLACK))
                .ifPresent(color -> new StyledEditorKit.ForegroundAction("text-color", color).actionPerformed(null));
    }

    public void clearFormatting() {
        executeCommand(this::resetTextPaneFormatting);
    }

    // Вспомогательные методы
    private void resetTextPaneFormatting() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
        setFontFamily(DEFAULT_FONT);
        setFontSize(DEFAULT_FONT_SIZE);
    }

    private void executeCommand(Runnable uiUpdate) {
        executeCommand(uiUpdate, null);
    }

    private void executeCommand(Runnable uiUpdate, Runnable processorUpdate) {
        if (processorUpdate != null) processorUpdate.run();
        uiUpdate.run();
        textPane.setText(textProcessor.getContent());
    }
}
