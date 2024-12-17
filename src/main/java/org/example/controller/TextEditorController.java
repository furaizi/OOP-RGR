package org.example.controller;

import org.example.model.TextFormatter;
import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextEditorController {
    private TextProcessor textProcessor;
    private TextFormatter textFormatter;
    private JTextPane textPane;
    private StyledDocument document;

    public TextEditorController(JTextPane textPane) {
        this.textProcessor = new TextProcessor();
        this.textFormatter = new TextFormatter();
        this.textPane = textPane;
        this.document = textPane.getStyledDocument();
    }

    /**
     * Загружает текст в модель и обновляет компонент.
     * @param text текст для загрузки
     */
    public void loadText(String text) {
        textProcessor.setContent(text);
        updateTextPane();
    }

    /**
     * Получает текст из компонента и сохраняет в модели.
     */
    public void saveText() {
        String content = textPane.getText();
        textProcessor.setContent(content);
    }

    /**
     * Обновляет текстовый компонент с данными из модели.
     */
    private void updateTextPane() {
        textPane.setText(textProcessor.getContent());
    }

    /**
     * Обработчик для подсчета слов.
     */
    public void countWordsAction() {
        saveText();
        int wordCount = textProcessor.countWords();
        JOptionPane.showMessageDialog(null, "Word Count: " + wordCount, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Обработчик для удаления лишних пробелов.
     */
    public void removeExtraSpacesAction() {
        saveText();
        textProcessor.removeExtraSpaces();
        updateTextPane();
    }

    /**
     * Обработчик для замены текста.
     * @param target      текст для замены
     * @param replacement текст-замена
     */
    public void replaceTextAction(String target, String replacement) {
        saveText();
        textProcessor.replaceText(target, replacement);
        updateTextPane();
    }

    /**
     * Применяет форматирование текста.
     * @param fontName название шрифта
     * @param size     размер шрифта
     * @param isBold   жирный ли шрифт
     * @param isItalic курсив
     * @param color    цвет текста
     */
    public void applyTextFormatting(String fontName, int size, boolean isBold, boolean isItalic, Color color) {
        textFormatter.setFont(fontName, size, isBold, isItalic);
        textFormatter.setColor(color);
        textFormatter.applyFormat(document, textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart());
    }

    /**
     * Очищает форматирование текста.
     */
    public void clearFormattingAction() {
        int start = textPane.getSelectionStart();
        int length = textPane.getSelectionEnd() - start;

        textFormatter.clearFormatting(document, start, length);
    }

    /**
     * Добавляет слушатель для кнопок действий.
     * @param button    кнопка
     * @param actionType тип действия: "countWords", "removeSpaces", "replace", и т.д.
     */
    public void addActionListener(JButton button, String actionType) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }
}
