package org.example.view.builders;

import org.example.controller.TextEditorController;
import org.example.view.factories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class MenuBarBuilder {

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);

    public static JMenuBar build(TextEditorController controller, JFrame frame) {
        var menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        menuBar.setBackground(new Color(240, 240, 240));
        menuBar.setOpaque(true);

        var fileMenu = new JMenu("File");
        fileMenu.setFont(DEFAULT_FONT);
        fileMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fileMenu.add(UIComponentFactory.createMenuItem("Save", controller::saveText));
        fileMenu.add(UIComponentFactory.createMenuItem("Load", controller::loadText));
        fileMenu.add(UIComponentFactory.createMenuItem("Exit", () -> System.exit(0)));

        var editMenu = new JMenu("Edit");
        editMenu.setFont(DEFAULT_FONT);
        editMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        editMenu.add(UIComponentFactory.createMenuItem("Count Words", controller::countWords));
        editMenu.add(UIComponentFactory.createMenuItem("Remove Extra Spaces", controller::removeExtraSpaces));
        editMenu.add(UIComponentFactory.createMenuItem("Replace Text", controller::replaceText));
        editMenu.add(UIComponentFactory.createMenuItem("Clear Formatting", controller::clearFormatting));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        return menuBar;
    }
}