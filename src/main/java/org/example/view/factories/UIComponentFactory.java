package org.example.view.factories;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UIComponentFactory {

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    public static JMenuItem createMenuItem(String text, Runnable action) {
        var menuItem = new JMenuItem(text);
        menuItem.setFont(DEFAULT_FONT);
        menuItem.setPreferredSize(new Dimension(200, 30));
        menuItem.addActionListener(e -> action.run());
        return menuItem;
    }

    public static JToggleButton createToggleButton(String text, Runnable action) {
        var button = new JToggleButton(text);
        var style = switch (text) {
            case "B" -> Font.BOLD;
            case "I" -> Font.ITALIC;
            default -> Font.PLAIN;
        };
        button.setFont(DEFAULT_FONT.deriveFont(style));
        button.setPreferredSize(new Dimension(50, 30));
        button.addActionListener(e -> action.run());
        return button;
    }

    public static JButton createButton(String text, Runnable action) {
        var button = new JButton(text);
        button.setFont(DEFAULT_FONT);
        button.setPreferredSize(new Dimension(80, 30));
        button.addActionListener(e -> action.run());
        return button;
    }

    public static JButton createImageButton(String iconPath, String tooltip, Runnable action) {
        var button = new JButton(new ImageIcon(UIComponentFactory.class.getResource(iconPath)));
        button.setPreferredSize(new Dimension(40, 40));
        button.setToolTipText(tooltip);
        button.addActionListener(e -> action.run());
        return button;
    }

    public static <T> JComboBox<T> createComboBox(T[] items, ActionListener listener) {
        var comboBox = new JComboBox<>(items);
        comboBox.setFont(DEFAULT_FONT);
        comboBox.addActionListener(listener);
        return comboBox;
    }
}
