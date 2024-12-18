package org.example.view.builders;

import org.example.controller.TextEditorController;
import org.example.view.MainView;
import org.example.view.factories.UIComponentFactory;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class ToolbarBuilder {

    public static JToolBar build(TextEditorController controller, JFrame frame) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        toolBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        toolBar.addSeparator(new Dimension(10, 0));

        toolBar.add(createFontComboBox(controller));
        toolBar.add(createSizeComboBox(controller));
        toolBar.add(UIComponentFactory.createToggleButton("B", controller::toggleBold));
        toolBar.add(UIComponentFactory.createToggleButton("I", controller::toggleItalic));
        toolBar.add(UIComponentFactory.createButton("Color", controller::chooseTextColor));
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_left.png", "Align Left",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_LEFT)));
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_center.png", "Align Center",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_CENTER)));
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_right.png", "Align Right",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_RIGHT)));

        return toolBar;
    }

    private static JComboBox<String> createFontComboBox(TextEditorController controller) {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        return UIComponentFactory.createComboBox(fonts, e -> controller.setFontFamily((String) ((JComboBox<?>) e.getSource()).getSelectedItem()));
    }

    private static JComboBox<Integer> createSizeComboBox(TextEditorController controller) {
        Integer[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48, 56, 64, 72};
        return UIComponentFactory.createComboBox(sizes, e -> controller.setFontSize((Integer) ((JComboBox<?>) e.getSource()).getSelectedItem()));
    }
}
