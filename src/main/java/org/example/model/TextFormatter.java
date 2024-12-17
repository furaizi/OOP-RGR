package org.example.model;

import java.awt.*;
import javax.swing.text.*;

public class TextFormatter {
    private SimpleAttributeSet attributes;

    public TextFormatter() {
        attributes = new SimpleAttributeSet();
    }

    /**
     * Устанавливает шрифт для текста.
     * @param fontName название шрифта
     * @param size     размер шрифта
     * @param isBold   делает текст жирным
     * @param isItalic делает текст курсивным
     */
    public void setFont(String fontName, int size, boolean isBold, boolean isItalic) {
        int style = Font.PLAIN;
        if (isBold) style |= Font.BOLD;
        if (isItalic) style |= Font.ITALIC;

        StyleConstants.setFontFamily(attributes, fontName);
        StyleConstants.setFontSize(attributes, size);
        StyleConstants.setBold(attributes, isBold);
        StyleConstants.setItalic(attributes, isItalic);
    }

    /**
     * Устанавливает цвет текста.
     * @param color цвет текста
     */
    public void setColor(Color color) {
        StyleConstants.setForeground(attributes, color);
    }

    /**
     * Устанавливает цвет фона для текста.
     * @param color цвет фона
     */
    public void setBackgroundColor(Color color) {
        StyleConstants.setBackground(attributes, color);
    }

    /**
     * Устанавливает выравнивание текста.
     * @param alignment выравнивание: StyleConstants.ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT, ALIGN_JUSTIFIED
     */
    public void setAlignment(int alignment, StyledDocument document, int startOffset, int length) {
        SimpleAttributeSet alignmentAttributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(alignmentAttributes, alignment);
        document.setParagraphAttributes(startOffset, length, alignmentAttributes, false);
    }

    /**
     * Применяет атрибуты форматирования к тексту.
     * @param document    документ, в который применяется форматирование
     * @param startOffset начальная позиция текста
     * @param length      длина форматируемого текста
     */
    public void applyFormat(StyledDocument document, int startOffset, int length) {
        document.setCharacterAttributes(startOffset, length, attributes, false);
    }

    /**
     * Очищает форматирование текста в указанном диапазоне.
     * @param document    StyledDocument документ
     * @param startOffset начальная позиция текста
     * @param length      длина очищаемого текста
     */
    public void clearFormatting(StyledDocument document, int startOffset, int length) {
        SimpleAttributeSet emptyAttributes = new SimpleAttributeSet();
        document.setCharacterAttributes(startOffset, length, emptyAttributes, true);
    }

    /**
     * Получает текущие атрибуты форматирования.
     * @return SimpleAttributeSet текущие атрибуты
     */
    public SimpleAttributeSet getAttributes() {
        return attributes;
    }
}
