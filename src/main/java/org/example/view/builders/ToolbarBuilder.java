package org.example.view.builders;

import org.example.controller.TextEditorController;
import org.example.view.factories.UIComponentFactory;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class ToolbarBuilder {

    private static final Integer[] FONT_SIZES = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48, 56, 64, 72};

    public static JToolBar build(TextEditorController controller, JFrame frame) {
        var toolBar = createToolBar(frame);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(createFontComboBox(controller));
        toolBar.add(createSizeComboBox(controller));
        addFormattingButtons(toolBar, controller);
        addAlignmentButtons(toolBar, controller);
        return toolBar;
    }

    private static JToolBar createToolBar(JFrame frame) {
        var toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        toolBar.setPreferredSize(new Dimension(frame.getWidth(), 50));
        return toolBar;
    }

    private static JComboBox<String> createFontComboBox(TextEditorController controller) {
        var fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        return UIComponentFactory.createComboBox(fonts, e -> controller.setFontFamily((String) ((JComboBox<?>) e.getSource()).getSelectedItem()));
    }

    private static JComboBox<Integer> createSizeComboBox(TextEditorController controller) {
        return UIComponentFactory.createComboBox(FONT_SIZES, e -> controller.setFontSize((Integer) ((JComboBox<?>) e.getSource()).getSelectedItem()));
    }

    private static void addFormattingButtons(JToolBar toolBar, TextEditorController controller) {
        toolBar.add(UIComponentFactory.createToggleButton("B", controller::toggleBold));
        toolBar.add(UIComponentFactory.createToggleButton("I", controller::toggleItalic));
        toolBar.add(UIComponentFactory.createColorButton("Color", controller::chooseTextColor));
    }

    private static void addAlignmentButtons(JToolBar toolBar, TextEditorController controller) {
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_left.png", "Align Left",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_LEFT)));
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_center.png", "Align Center",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_CENTER)));
        toolBar.add(UIComponentFactory.createImageButton("/icons/align_right.png", "Align Right",
                () -> controller.applyTextAlignment(StyleConstants.ALIGN_RIGHT)));
    }
}
