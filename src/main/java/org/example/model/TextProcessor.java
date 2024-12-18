package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс TextProcessor отвечает за управление содержимым текста и операции над ним.
 */
public class TextProcessor {
    private final StringBuilder documentContent;

    public TextProcessor() {
        this.documentContent = new StringBuilder();
    }

    /**
     * Устанавливает содержимое документа.
     *
     * @param text исходный текст
     */
    public void setContent(String text) {
        Objects.requireNonNull(text, "Text cannot be null");
        documentContent.setLength(0); // Очистка содержимого перед установкой нового текста
        documentContent.append(text);
    }

    /**
     * Получает текущее содержимое документа.
     *
     * @return содержимое документа
     */
    public String getContent() {
        return documentContent.toString();
    }

    /**
     * Добавляет текст к текущему документу.
     *
     * @param text текст для добавления
     */
    public void appendContent(String text) {
        Objects.requireNonNull(text, "Text to append cannot be null");
        documentContent.append(text);
    }

    /**
     * Подсчитывает количество слов в документе.
     *
     * @return количество слов
     */
    public int countWords() {
        String text = documentContent.toString().trim();
        return text.isEmpty() ? 0 : text.split("\\s+").length;
    }

    /**
     * Подсчитывает количество символов в документе (включая пробелы).
     *
     * @return количество символов
     */
    public int countCharacters() {
        return documentContent.length();
    }

    /**
     * Удаляет лишние пробелы из содержимого документа (двойные пробелы и пробелы в начале/конце).
     */
    public void removeExtraSpaces() {
        String cleanedText = documentContent.toString().trim().replaceAll("\\s+", " ");
        documentContent.setLength(0);
        documentContent.append(cleanedText);
    }

    /**
     * Заменяет все вхождения целевого текста на новый текст в документе.
     *
     * @param target      текст, который нужно заменить
     * @param replacement текст, на который нужно заменить
     */
    public void replaceText(String target, String replacement) {
        Objects.requireNonNull(target, "Target text cannot be null");
        Objects.requireNonNull(replacement, "Replacement text cannot be null");
        String updatedText = documentContent.toString().replace(target, replacement);
        documentContent.setLength(0);
        documentContent.append(updatedText);
    }

    /**
     * Сортирует строки документа.
     *
     * @param byLength если true, сортирует по длине строки, иначе по алфавиту
     */
    public void sortLines(boolean byLength) {
        List<String> lineList = Arrays.stream(documentContent.toString().split("\\n"))
                .sorted(byLength ? Comparator.comparingInt(String::length) : String::compareTo)
                .collect(Collectors.toList());

        documentContent.setLength(0);
        documentContent.append(String.join("\n", lineList));
    }

    /**
     * Анализирует частоту символов в документе.
     *
     * @return карта с символами и их количеством в документе
     */
    public Map<Character, Integer> analyzeCharacterFrequency() {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : documentContent.toString().toCharArray()) {
            if (!Character.isWhitespace(c)) {
                frequencyMap.merge(c, 1, Integer::sum);
            }
        }

        return frequencyMap;
    }
}
