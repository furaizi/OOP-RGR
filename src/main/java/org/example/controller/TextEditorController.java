package org.example.controller;

import org.example.controller.handlers.TextAlignmentHandler;
import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public void saveFormattedText() {
        var fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.getSelectedFile();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                var doc = textPane.getStyledDocument();
                new RTFEditorKit().write(fos, doc, 0, doc.getLength());
                JOptionPane.showMessageDialog(null, "Text saved successfully with formatting.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to save file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadFormattedText() {
        var fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                RTFEditorKit rtfEditorKit = new RTFEditorKit();
                StyledDocument doc = (StyledDocument) rtfEditorKit.createDefaultDocument();
                rtfEditorKit.read(fis, doc, 0);
                textPane.setDocument(doc); // Устанавливаем документ в textPane
                JOptionPane.showMessageDialog(null, "Text loaded successfully with formatting.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to load file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Команды
    public void saveText() {
        textProcessor.setContent(textPane.getText());
        JOptionPane.showMessageDialog(null, "Text saved successfully.");
    }

    public void loadText() {
        var text = Optional.ofNullable(JOptionPane.showInputDialog("Enter text to load:"));
        text.ifPresent(content -> {
            textPane.setText(content);
            textProcessor.setContent(content);
        });
    }

    public void countWords() {
        textProcessor.setContent(textPane.getText());
        JOptionPane.showMessageDialog(null, "Word count: " + textProcessor.countWords());
    }

    public void removeExtraSpaces() {
        textProcessor.setContent(textPane.getText());
        textProcessor.removeExtraSpaces();
        textPane.setText(textProcessor.getContent());
        JOptionPane.showMessageDialog(null, "Extra spaces removed.");
    }

    public void replaceText() {
        var target = JOptionPane.showInputDialog("Enter text to replace:");
        var replacement = JOptionPane.showInputDialog("Enter replacement text:");
        if (target != null && replacement != null) {
            textProcessor.replaceText(target, replacement);
            textPane.setText(textProcessor.getContent());
        }
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
        resetTextPaneFormatting();
    }

    // Вспомогательные методы
    private void resetTextPaneFormatting() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
        setFontFamily(DEFAULT_FONT);
        setFontSize(DEFAULT_FONT_SIZE);
    }
}
