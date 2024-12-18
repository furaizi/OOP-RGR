package org.example.controller;

import org.example.model.TextProcessor;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Контроллер для управления логикой текстового редактора.
 */
public class TextEditorController {
    private final TextProcessor textProcessor;
    private final JTextPane textPane;
    private final StyledDocument document;

    public TextEditorController(JTextPane textPane) {
        this.textProcessor = new TextProcessor();
        this.textPane = textPane;
        this.document = textPane.getStyledDocument();
    }

    /**
     * Загружает текст в текстовый процессор и обновляет текстовую панель.
     *
     * @param text текст для загрузки
     */
    public void loadText(String text) {
        updateProcessor(() -> textProcessor.setContent(text));
    }

    /**
     * Сохраняет текст из текстовой панели в текстовый процессор.
     */
    public void saveText() {
        textProcessor.setContent(textPane.getText());
    }

    /**
     * Выполняет действие с текстовым процессором, обновляя текстовую панель после изменений.
     *
     * @param action действие, выполняемое над текстовым процессором
     */
    private void updateProcessor(Runnable action) {
        action.run();
        updateTextPane();
    }

    /**
     * Обновляет содержимое текстовой панели.
     */
    private void updateTextPane() {
        textPane.setText(textProcessor.getContent());
    }

    /**
     * Действие: Подсчет слов.
     */
    public void countWordsAction() {
        saveText();
        showMessage("Word Count", "Word Count: " + textProcessor.countWords());
    }

    /**
     * Действие: Удаление лишних пробелов.
     */
    public void removeExtraSpacesAction() {
        updateProcessor(textProcessor::removeExtraSpaces);
    }

    /**
     * Действие: Замена текста.
     *
     * @param target      текст, который нужно заменить
     * @param replacement текст, на который нужно заменить
     */
    public void replaceTextAction(String target, String replacement) {
        updateProcessor(() -> textProcessor.replaceText(target, replacement));
    }

    /**
     * Действие: Очистка форматирования.
     */
    public void clearFormattingAction() {
        textPane.setCharacterAttributes(new SimpleAttributeSet(), true);
    }

    /**
     * Действие: Выравнивание текста.
     *
     * @param alignment тип выравнивания (StyleConstants.ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT)
     */
    public void applyTextAlignment(int alignment) {
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();

        if (start == end) {
            showMessage("Error", "No text selected for alignment.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleAttributeSet alignmentAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignmentAttribute, alignment);

        document.setParagraphAttributes(start, end - start, alignmentAttribute, false);
    }

    /**
     * Добавляет обработчик событий для кнопок.
     *
     * @param button      кнопка, к которой добавляется слушатель
     * @param actionType  тип действия ("countWords", "removeSpaces", и т.д.)
     */
//    public void addActionListener(JButton button, String actionType) {
//        Map<String, Runnable> actionMap = Map.of(
//                "countWords", this::countWordsAction,
//                "removeSpaces", this::removeExtraSpacesAction
//        ).getOrDefault(actionType, a -> showMessage("Error", "Unknown action", JOptionPane.ERROR_MESSAGE));
//
//        button.addActionListener(e -> actionMap.accept(actionType));
//    }

    /**
     * Отображает сообщение пользователю.
     *
     * @param title   заголовок сообщения
     * @param message текст сообщения
     */
    private void showMessage(String title, String message) {
        showMessage(title, message, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Отображает сообщение пользователю с указанием типа.
     *
     * @param title       заголовок сообщения
     * @param message     текст сообщения
     * @param messageType тип сообщения (JOptionPane.ERROR_MESSAGE, INFORMATION_MESSAGE и т.д.)
     */
    private void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
