package org.example;

import org.example.view.MainView;

import javax.swing.*;

public class TextEditorApp {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(MainView::new);
    }
}
