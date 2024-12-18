package org.example.view;

import org.example.controller.TextEditorController;
import org.example.view.builders.MenuBarBuilder;
import org.example.view.builders.ToolbarBuilder;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final JFrame frame;
    private final JTextPane textPane;
    private final TextEditorController controller;

    public MainView() {
        textPane = new JTextPane();
        controller = new TextEditorController(textPane);
        setDefaultFontSize(24);

        frame = initializeFrame();
        frame.add(new JScrollPane(textPane), BorderLayout.CENTER);
        frame.add(ToolbarBuilder.build(controller, frame), BorderLayout.NORTH);
        frame.setJMenuBar(MenuBarBuilder.build(controller, frame));

        frame.setVisible(true);
    }

    private JFrame initializeFrame() {
        JFrame frame = new JFrame("Text Editor Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private void setDefaultFontSize(int size) {
        textPane.setFont(new Font("Arial", Font.PLAIN, size));
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
