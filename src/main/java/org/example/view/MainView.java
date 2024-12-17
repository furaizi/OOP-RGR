package org.example.view;

import org.example.controller.TextEditorController;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private JFrame frame;
    private JTextPane textPane;
    private JButton countWordsButton;
    private JButton removeSpacesButton;
    private JButton replaceTextButton;
    private JButton formatTextButton;
    private JButton clearFormattingButton;
    private TextEditorController controller;

    public MainView() {
        initializeUI();
        controller = new TextEditorController(textPane);
        registerActions();
    }

    /**
     * Инициализация графического интерфейса.
     */
    private void initializeUI() {
        frame = new JFrame("Text Editor Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Текстовая область
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        countWordsButton = new JButton("Count Words");
        removeSpacesButton = new JButton("Remove Extra Spaces");
        replaceTextButton = new JButton("Replace Text");
        formatTextButton = new JButton("Format Text");
        clearFormattingButton = new JButton("Clear Formatting");

        buttonPanel.add(countWordsButton);
        buttonPanel.add(removeSpacesButton);
        buttonPanel.add(replaceTextButton);
        buttonPanel.add(formatTextButton);
        buttonPanel.add(clearFormattingButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Регистрация действий для кнопок с помощью контроллера.
     */
    private void registerActions() {
        controller.addActionListener(countWordsButton, "countWords");
        controller.addActionListener(removeSpacesButton, "removeSpaces");
        replaceTextButton.addActionListener(e -> handleReplaceText());
        formatTextButton.addActionListener(e -> handleTextFormatting());
        clearFormattingButton.addActionListener(e -> controller.clearFormattingAction());
    }

    /**
     * Обработчик замены текста (с диалогом ввода).
     */
    private void handleReplaceText() {
        String target = JOptionPane.showInputDialog(frame, "Text to replace:", "Replace Text", JOptionPane.QUESTION_MESSAGE);
        if (target == null || target.isEmpty()) return;

        String replacement = JOptionPane.showInputDialog(frame, "Replacement text:", "Replace Text", JOptionPane.QUESTION_MESSAGE);
        if (replacement == null) return;

        controller.replaceTextAction(target, replacement);
    }

    /**
     * Обработчик форматирования текста (с диалогом ввода параметров).
     */
    private void handleTextFormatting() {
        String fontName = JOptionPane.showInputDialog(frame, "Enter font name:", "Arial");
        int fontSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter font size:", "12"));
        boolean isBold = JOptionPane.showConfirmDialog(frame, "Bold?", "Font Style", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        boolean isItalic = JOptionPane.showConfirmDialog(frame, "Italic?", "Font Style", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        Color color = JColorChooser.showDialog(frame, "Choose Text Color", Color.BLACK);

        if (fontName != null && color != null) {
            controller.applyTextFormatting(fontName, fontSize, isBold, isItalic, color);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}

