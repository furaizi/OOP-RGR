package org.example.view.factories;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UIComponentFactory {

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    public static JMenuItem createMenuItem(String text, Runnable action) {
        return tuneComponent(new JMenuItem(text), action, 200, 30);
    }

    public static JToggleButton createToggleButton(String text, Runnable action) {
        var button = new JToggleButton(text);
        var style = switch (text) {
            case "B" -> Font.BOLD;
            case "I" -> Font.ITALIC;
            default -> Font.PLAIN;
        };
        tuneComponent(button, action, 50, 30);
        button.setFont(DEFAULT_FONT.deriveFont(style));
        return button;
    }

    public static JButton createColorButton(String text, Runnable action) {
        return tuneComponent(new JButton(text), action, 80, 30);
    }

    public static JButton createImageButton(String iconPath, String tooltip, Runnable action) {
        var button = new JButton(new ImageIcon(UIComponentFactory.class.getResource(iconPath)));
        tuneComponent(button, action, 40, 40);
        button.setToolTipText(tooltip);
        return button;
    }

    public static <T> JComboBox<T> createComboBox(T[] items, ActionListener listener) {
        var comboBox = new JComboBox<>(items);
        comboBox.setFont(DEFAULT_FONT);
        comboBox.addActionListener(listener);
        return comboBox;
    }

    private static <T extends AbstractButton> T tuneComponent(T component, Runnable action, int width, int height) {
        component.setFont(DEFAULT_FONT);
        component.setPreferredSize(new Dimension(width, height));
        component.addActionListener(e -> action.run());
        return component;
    }
}
