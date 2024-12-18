package org.example.controller;

import org.example.controller.actions.*;
import org.example.controller.handlers.TextAlignmentHandler;
import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextEditorController {
    private final JTextPane textPane;
    private final TextProcessor textProcessor;
    private final TextAlignmentHandler alignmentHandler;

    public TextEditorController(JTextPane textPane) {
        this.textPane = textPane;
        this.textProcessor = new TextProcessor();
        this.alignmentHandler = new TextAlignmentHandler(textPane);
    }

    // Команды
    public void saveText() {
        new SaveTextCommand(textPane, textProcessor).execute();
    }

    public void loadText() {
        new LoadTextCommand(textPane, textProcessor).execute();
    }

    public void countWords() {
        new CountWordsCommand(textPane, textProcessor).execute();
    }

    public void removeExtraSpaces() {
        new RemoveExtraSpacesCommand(textPane, textProcessor).execute();
    }

    public void replaceText() {
        new ReplaceTextCommand(textPane, textProcessor).execute();
    }

    // Выравнивание
    public void applyTextAlignment(int alignment) {
        alignmentHandler.applyAlignment(alignment);
    }

    // Форматирование
    public void setFontFamily(String fontName) {
        if (fontName != null) {
            new StyledEditorKit.FontFamilyAction("font-family", fontName).actionPerformed(null);
        }
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
        var color = JColorChooser.showDialog(null, "Choose Text Color", Color.BLACK);
        if (color != null) {
            new StyledEditorKit.ForegroundAction("text-color", color).actionPerformed(null);
        }
    }

    public void clearFormatting() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
        setDefaultFontStyle();
    }

    private void setDefaultFontStyle() {
        setFontFamily("Arial");
        setFontSize(24);
    }
}
