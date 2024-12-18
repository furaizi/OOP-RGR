package org.example.view;

import org.example.controller.TextEditorController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class MainView {
    private JFrame frame;
    private JTextPane textPane;

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
        setDefaultFontSize(textPane, 24); // Устанавливаем размер шрифта по умолчанию
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Добавляем меню-бар
        createMenuBar();

        // Добавление тулбара
        addFormattingToolBar();

        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Настройка размера и стиля меню-бара
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Граница для стиля
        menuBar.setBackground(new Color(240, 240, 240)); // Светло-серый фон
        menuBar.setOpaque(true);

        // Меню "File"
        JMenu fileMenu = createStyledMenu("File");
        JMenuItem saveItem = createStyledMenuItem("Save");
        JMenuItem loadItem = createStyledMenuItem("Load");
        JMenuItem exitItem = createStyledMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Обработчики для пунктов "File"
        saveItem.addActionListener(e -> controller.saveText());
        loadItem.addActionListener(e -> handleLoadText());
        exitItem.addActionListener(e -> System.exit(0));

        menuBar.add(fileMenu);

        // Меню "Edit"
        JMenu editMenu = createStyledMenu("Edit");
        JMenuItem countWordsItem = createStyledMenuItem("Count Words");
        JMenuItem removeSpacesItem = createStyledMenuItem("Remove Extra Spaces");
        JMenuItem replaceTextItem = createStyledMenuItem("Replace Text");
        JMenuItem clearFormattingItem = createStyledMenuItem("Clear Formatting");
        editMenu.add(countWordsItem);
        editMenu.add(removeSpacesItem);
        editMenu.add(replaceTextItem);
        editMenu.addSeparator();
        editMenu.add(clearFormattingItem);

        // Обработчики для пунктов "Edit"
        countWordsItem.addActionListener(e -> controller.countWordsAction());
        removeSpacesItem.addActionListener(e -> controller.removeExtraSpacesAction());
        replaceTextItem.addActionListener(e -> handleReplaceText());
        clearFormattingItem.addActionListener(e -> controller.clearFormattingAction());

        menuBar.add(editMenu);

        // Устанавливаем меню-бар в окно
        frame.setJMenuBar(menuBar);
    }

    // Метод для создания стилизованного меню
    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(new Font("Arial", Font.PLAIN, 16)); // Увеличиваем шрифт
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Добавляем отступы
        return menu;
    }

    // Метод для создания стилизованных пунктов меню
    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 14)); // Шрифт пунктов меню
        menuItem.setPreferredSize(new Dimension(200, 30)); // Увеличиваем размер пунктов меню
        return menuItem;
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
        fontComboBox.addActionListener(e -> setFontFamily((String) fontComboBox.getSelectedItem()));
        sizeComboBox.addActionListener(e -> setFontSize());
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

    private void setFontSize() {
        Integer selectedSize = (Integer) sizeComboBox.getSelectedItem();
        if (selectedSize != null) {
            new StyledEditorKit.FontSizeAction("font-size", selectedSize).actionPerformed(null);
        }
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

    private void setDefaultFontSize(JTextPane textPane, int fontSize) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, fontSize);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
    }

    private void handleLoadText() {
        String text = JOptionPane.showInputDialog(frame, "Enter text to load:", "Load Text", JOptionPane.PLAIN_MESSAGE);
        if (text != null) {
            controller.loadText(text);
        }
    }
}
