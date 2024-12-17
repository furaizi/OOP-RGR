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
    private JButton alignLeftButton;
    private JButton alignCenterButton;
    private JButton alignRightButton;

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

        // Существующие элементы форматирования
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        toolBar.add(fontComboBox);

        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36};
        sizeComboBox = new JComboBox<>(sizes);
        toolBar.add(sizeComboBox);

        boldButton = new JToggleButton("B");
        boldButton.setFont(boldButton.getFont().deriveFont(Font.BOLD));
        toolBar.add(boldButton);

        italicButton = new JToggleButton("I");
        italicButton.setFont(italicButton.getFont().deriveFont(Font.ITALIC));
        toolBar.add(italicButton);

        colorButton = new JButton("Color");
        toolBar.add(colorButton);

        // Новые кнопки для выравнивания
        alignLeftButton = new JButton("L");
        alignCenterButton = new JButton("C");
        alignRightButton = new JButton("R");

        toolBar.add(alignLeftButton);
        toolBar.add(alignCenterButton);
        toolBar.add(alignRightButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private void registerActions() {
        controller.addActionListener(countWordsButton, "countWords");
        controller.addActionListener(removeSpacesButton, "removeSpaces");
        replaceTextButton.addActionListener(e -> handleReplaceText());
        clearFormattingButton.addActionListener(e -> controller.clearFormattingAction());
        alignLeftButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_LEFT));
        alignCenterButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_CENTER));
        alignRightButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_RIGHT));

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
