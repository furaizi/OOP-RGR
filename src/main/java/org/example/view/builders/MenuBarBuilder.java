package org.example.view.builders;

import org.example.controller.TextEditorController;
import org.example.view.factories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuBarBuilder {

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);

    public static JMenuBar build(TextEditorController controller, JFrame frame) {
        var menuBar = createMenuBar(frame);
        menuBar.add(buildMenu("File", List.of(
                new MenuItemConfig("Save", controller::saveText),
                new MenuItemConfig("Load", controller::loadText),
                new MenuItemConfig("Exit", () -> System.exit(0))
        )));
        menuBar.add(buildMenu("Edit", List.of(
                new MenuItemConfig("Count Words", controller::countWords),
                new MenuItemConfig("Remove Extra Spaces", controller::removeExtraSpaces),
                new MenuItemConfig("Replace Text", controller::replaceText),
                new MenuItemConfig("Clear Formatting", controller::clearFormatting)
        )));
        return menuBar;
    }

    private static JMenuBar createMenuBar(JFrame frame) {
        var menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        menuBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        menuBar.setBackground(new Color(240, 240, 240));
        return menuBar;
    }

    private static JMenu buildMenu(String title, List<MenuItemConfig> items) {
        var menu = new JMenu(title);
        menu.setFont(DEFAULT_FONT);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        items.forEach(item -> menu.add(UIComponentFactory.createMenuItem(item.text(), item.action())));
        return menu;
    }

    private record MenuItemConfig(String text, Runnable action) {}
}
