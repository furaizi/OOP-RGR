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
        toolBar.setFloatable(false); // Запрещает перемещение тулбара
        toolBar.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Добавляет границу для стиля
        toolBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        toolBar.addSeparator(new Dimension(10, 0));

        // Существующие элементы форматирования
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setPreferredSize(new Dimension(150, 30));
        fontComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        toolBar.add(fontComboBox);

        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48, 56, 64, 72, 80, 96};
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setPreferredSize(new Dimension(80, 30));
        sizeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        sizeComboBox.setSelectedItem(24); // Устанавливаем 24 как размер по умолчанию
        toolBar.add(sizeComboBox);

        boldButton = new JToggleButton("B");
        boldButton.setFont(new Font("Arial", Font.BOLD, 14));
        boldButton.setPreferredSize(new Dimension(50, 30));
        toolBar.add(boldButton);

        italicButton = new JToggleButton("I");
        italicButton.setFont(new Font("Arial", Font.ITALIC, 14));
        italicButton.setPreferredSize(new Dimension(50, 30));
        toolBar.add(italicButton);

        colorButton = new JButton("Color");
        colorButton.setFont(new Font("Arial", Font.PLAIN, 14));
        colorButton.setPreferredSize(new Dimension(80, 30));
        toolBar.add(colorButton);

        // Новые кнопки для выравнивания
        alignLeftButton = new JButton(new ImageIcon(getClass().getResource("/icons/align_left.png")));
        alignLeftButton.setToolTipText("Align Left");
        alignLeftButton.setPreferredSize(new Dimension(40, 40));
        toolBar.add(alignLeftButton);

        alignCenterButton = new JButton(new ImageIcon(getClass().getResource("/icons/align_center.png")));
        alignCenterButton.setToolTipText("Align Center");
        alignCenterButton.setPreferredSize(new Dimension(40, 40));
        toolBar.add(alignCenterButton);

        alignRightButton = new JButton(new ImageIcon(getClass().getResource("/icons/align_right.png")));
        alignRightButton.setToolTipText("Align Right");
        alignRightButton.setPreferredSize(new Dimension(40, 40));
        toolBar.add(alignRightButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private void registerActions() {
        controller.addActionListener(countWordsButton, "countWords");
        controller.addActionListener(removeSpacesButton, "removeSpaces");
        replaceTextButton.addActionListener(e -> handleReplaceText());
        clearFormattingButton.addActionListener(e -> controller.clearFormattingAction());

        fontComboBox.addActionListener(e -> setFontFamily((String) fontComboBox.getSelectedItem()));
        sizeComboBox.addActionListener(e -> setFontSize((Integer) sizeComboBox.getSelectedItem()));
        boldButton.addActionListener(e -> new StyledEditorKit.BoldAction().actionPerformed(e));
        italicButton.addActionListener(e -> new StyledEditorKit.ItalicAction().actionPerformed(e));
        colorButton.addActionListener(e -> chooseTextColor());
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
