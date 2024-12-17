package org.example.view;

import org.example.controller.TextEditorController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class MainView {
    private JFrame frame;
    private JTextPane textPane;

    private JButton countWordsButton;
    private JButton removeSpacesButton;
    private JButton replaceTextButton;
    private JButton clearFormattingButton;

    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> sizeComboBox;
    private JToggleButton boldButton;
    private JToggleButton italicButton;
    private JButton colorButton;

    private TextEditorController controller;

    public MainView() {
        initializeUI();
        controller = new TextEditorController(textPane);
        registerActions();
    }

    private void initializeUI() {
        frame = new JFrame("Text Editor Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Текстовая область
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());

        countWordsButton = new JButton("Count Words");
        removeSpacesButton = new JButton("Remove Extra Spaces");
        replaceTextButton = new JButton("Replace Text");
        clearFormattingButton = new JButton("Clear Formatting");

        buttonPanel.add(countWordsButton);
        buttonPanel.add(removeSpacesButton);
        buttonPanel.add(replaceTextButton);
        buttonPanel.add(clearFormattingButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Добавление тулбара
        addFormattingToolBar();

        frame.setVisible(true);
    }

    private void addFormattingToolBar() {
        JToolBar toolBar = new JToolBar();

        // Шрифты
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.addActionListener(e -> setFontFamily((String) fontComboBox.getSelectedItem()));
        toolBar.add(fontComboBox);

        // Размер шрифта
        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36};
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setSelectedItem(12);
        sizeComboBox.addActionListener(e -> setFontSize((Integer) sizeComboBox.getSelectedItem()));
        toolBar.add(sizeComboBox);

        // Жирный
        boldButton = new JToggleButton("B");
        boldButton.setFont(boldButton.getFont().deriveFont(Font.BOLD));
        boldButton.addActionListener(e -> new StyledEditorKit.BoldAction().actionPerformed(e));
        toolBar.add(boldButton);

        // Курсив
        italicButton = new JToggleButton("I");
        italicButton.setFont(italicButton.getFont().deriveFont(Font.ITALIC));
        italicButton.addActionListener(e -> new StyledEditorKit.ItalicAction().actionPerformed(e));
        toolBar.add(italicButton);

        // Цвет текста
        colorButton = new JButton("Color");
        colorButton.addActionListener(e -> chooseTextColor());
        toolBar.add(colorButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private void registerActions() {
        controller.addActionListener(countWordsButton, "countWords");
        controller.addActionListener(removeSpacesButton, "removeSpaces");
        replaceTextButton.addActionListener(e -> handleReplaceText());
        clearFormattingButton.addActionListener(e -> controller.clearFormattingAction());
    }

    private void setFontFamily(String fontName) {
        new StyledEditorKit.FontFamilyAction("font-family", fontName).actionPerformed(null);
    }

    private void setFontSize(int size) {
        new StyledEditorKit.FontSizeAction("font-size", size).actionPerformed(null);
    }

    private void chooseTextColor() {
        Color color = JColorChooser.showDialog(frame, "Choose Text Color", Color.BLACK);
        if (color != null) {
            new StyledEditorKit.ForegroundAction("text-color", color).actionPerformed(null);
        }
    }

    private void handleReplaceText() {
        String target = JOptionPane.showInputDialog(frame, "Text to replace:", "Replace Text", JOptionPane.QUESTION_MESSAGE);
        if (target == null || target.isEmpty()) return;

        String replacement = JOptionPane.showInputDialog(frame, "Replacement text:", "Replace Text", JOptionPane.QUESTION_MESSAGE);
        if (replacement == null) return;

        controller.replaceTextAction(target, replacement);
    }
}
