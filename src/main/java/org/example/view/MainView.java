package org.example.view;

import org.example.controller.TextEditorController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;

public class MainView {
    private final JFrame frame;
    private final JTextPane textPane;
    private final JComboBox<String> fontComboBox;
    private final JComboBox<Integer> sizeComboBox;
    private final JToggleButton boldButton;
    private final JToggleButton italicButton;
    private final JButton colorButton;
    private final JButton alignLeftButton;
    private final JButton alignCenterButton;
    private final JButton alignRightButton;
    private final TextEditorController controller;

    public MainView() {
        textPane = new JTextPane();
        setDefaultFontSize(textPane, 24);
        frame = initializeFrame();
        controller = new TextEditorController(textPane);

        fontComboBox = createFontComboBox();
        sizeComboBox = createSizeComboBox();
        boldButton = createToggleButton("B", Font.BOLD);
        italicButton = createToggleButton("I", Font.ITALIC);
        colorButton = createButton("Color");
        alignLeftButton = createImageButton("/icons/align_left.png", "Align Left");
        alignCenterButton = createImageButton("/icons/align_center.png", "Align Center");
        alignRightButton = createImageButton("/icons/align_right.png", "Align Right");

        setupToolBar();
        createMenuBar();
        registerActions();
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> setDefaultFontStyle(textPane));
    }

    private JFrame initializeFrame() {
        JFrame frame = new JFrame("Text Editor Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textPane), BorderLayout.CENTER);
        return frame;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Граница для стиля
        menuBar.setBackground(new Color(240, 240, 240)); // Светло-серый фон
        menuBar.setOpaque(true);

        Map<String, Consumer<Void>> fileActions = Map.of(
                "Save", v -> controller.saveText(),
                "Load", v -> handleLoadText(),
                "Exit", v -> System.exit(0)
        );
        menuBar.add(createMenu("File", fileActions));

        Map<String, Consumer<Void>> editActions = Map.of(
                "Count Words", v -> controller.countWordsAction(),
                "Remove Extra Spaces", v -> controller.removeExtraSpacesAction(),
                "Replace Text", v -> handleReplaceText(),
                "Clear Formatting", v -> clearFormatting()
        );
        menuBar.add(createMenu("Edit", editActions));

        frame.setJMenuBar(menuBar);
    }

    private JMenu createMenu(String title, Map<String, Consumer<Void>> actions) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Arial", Font.PLAIN, 16));
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        actions.forEach((name, action) -> {
            JMenuItem item = new JMenuItem(name);
            item.setPreferredSize(new Dimension(200, 30));
            item.setFont(new Font("Arial", Font.PLAIN, 14));
            item.addActionListener(e -> action.accept(null));
            menu.add(item);
        });
        return menu;
    }

    private void setupToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        toolBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        toolBar.addSeparator(new Dimension(10, 0));

        toolBar.add(fontComboBox);
        toolBar.add(sizeComboBox);
        toolBar.add(boldButton);
        toolBar.add(italicButton);
        toolBar.add(colorButton);
        toolBar.add(alignLeftButton);
        toolBar.add(alignCenterButton);
        toolBar.add(alignRightButton);

        frame.add(toolBar, BorderLayout.NORTH);
    }

    private JComboBox<String> createFontComboBox() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> comboBox = new JComboBox<>(fonts);
        comboBox.setPreferredSize(new Dimension(150, 30));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setSelectedItem("Arial");
        return comboBox;
    }

    private JComboBox<Integer> createSizeComboBox() {
        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48, 56, 64, 72};
        JComboBox<Integer> comboBox = new JComboBox<>(sizes);
        comboBox.setPreferredSize(new Dimension(80, 30));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setSelectedItem(24);
        return comboBox;
    }

    private JToggleButton createToggleButton(String text, int style) {
        JToggleButton button = new JToggleButton(text);
        button.setPreferredSize(new Dimension(50, 30));
        button.setFont(new Font("Arial", style, 14));
        return button;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 30));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

    private JButton createImageButton(String iconPath, String tooltip) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
        button.setPreferredSize(new Dimension(40, 40));
        button.setToolTipText(tooltip);
        return button;
    }

    private void registerActions() {
        fontComboBox.addActionListener(e -> setFontFamily((String) fontComboBox.getSelectedItem()));
        sizeComboBox.addActionListener(e -> setFontSize((Integer) sizeComboBox.getSelectedItem()));
        boldButton.addActionListener(e -> new StyledEditorKit.BoldAction().actionPerformed(null));
        italicButton.addActionListener(e -> new StyledEditorKit.ItalicAction().actionPerformed(null));
        colorButton.addActionListener(e -> chooseTextColor());
        alignLeftButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_LEFT));
        alignCenterButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_CENTER));
        alignRightButton.addActionListener(e -> controller.applyTextAlignment(StyleConstants.ALIGN_RIGHT));
    }

    private void setFontFamily(String fontName) {
        if (fontName != null) {
            new StyledEditorKit.FontFamilyAction("font-family", fontName).actionPerformed(null);
        }
    }

    private void setFontSize(Integer size) {
        if (size != null) {
            new StyledEditorKit.FontSizeAction("font-size", size).actionPerformed(null);
        }
    }

    private void chooseTextColor() {
        Color color = JColorChooser.showDialog(frame, "Choose Text Color", Color.BLACK);
        if (color != null) {
            new StyledEditorKit.ForegroundAction("text-color", color).actionPerformed(null);
        }
    }

    private void handleReplaceText() {
        String target = JOptionPane.showInputDialog(frame, "Text to replace:");
        String replacement = JOptionPane.showInputDialog(frame, "Replacement text:");
        if (target != null && replacement != null) {
            controller.replaceTextAction(target, replacement);
        }
    }

    private void clearFormatting() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
        setDefaultFontStyle(textPane);
    }

    private void setDefaultFontSize(JTextPane pane, int size) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, size);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
    }

    private void setDefaultFontStyle(JTextPane pane) {
        new StyledEditorKit.FontFamilyAction("font-family", "Arial").actionPerformed(null);
        new StyledEditorKit.FontSizeAction("font-size", 24).actionPerformed(null);
        fontComboBox.setSelectedItem("Arial");
        sizeComboBox.setSelectedItem(24);
    }

    private void handleLoadText() {
        String text = JOptionPane.showInputDialog(frame, "Enter text to load:");
        if (text != null) {
            controller.loadText(text);
        }
    }
}
