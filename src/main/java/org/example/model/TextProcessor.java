package org.example.model;

import java.util.*;
import java.util.stream.Collectors;

public class TextProcessor {
    private StringBuilder documentContent;

    public TextProcessor() {
        this.documentContent = new StringBuilder();
    }

    /**
     * Устанавливает содержимое документа.
     * @param text исходный текст
     */
    public void setContent(String text) {
        this.documentContent = new StringBuilder(text);
    }

    /**
     * Получает текущее содержимое документа.
     * @return содержимое документа
     */
    public String getContent() {
        return documentContent.toString();
    }

    /**
     * Добавляет текст к текущему документу.
     * @param text текст для добавления
     */
    public void appendContent(String text) {
        documentContent.append(text);
    }

    /**
     * Подсчитывает количество слов в документе.
     * @return количество слов
     */
    public int countWords() {
        String text = documentContent.toString();
        if (text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    /**
     * Подсчитывает количество символов в документе (включая пробелы).
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
        documentContent = new StringBuilder(cleanedText);
    }

    /**
     * Заменяет все вхождения целевого текста на новый текст в документе.
     * @param target      текст, который нужно заменить
     * @param replacement текст, на который нужно заменить
     */
    public void replaceText(String target, String replacement) {
        String updatedText = documentContent.toString().replace(target, replacement);
        documentContent = new StringBuilder(updatedText);
    }

    /**
     * Сортирует строки документа.
     * @param byLength если true, сортирует по длине строки, иначе по алфавиту
     */
    public void sortLines(boolean byLength) {
        String[] lines = documentContent.toString().split("\\n");
        List<String> lineList = Arrays.asList(lines);

        if (byLength) {
            lineList = lineList.stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .collect(Collectors.toList());
        } else {
            lineList = lineList.stream()
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());
        }

        documentContent = new StringBuilder(String.join("\n", lineList));
    }

    /**
     * Анализирует частоту символов в документе.
     * @return карта с символами и их количеством в документе
     */
    public Map<Character, Integer> analyzeCharacterFrequency() {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : documentContent.toString().toCharArray()) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }
}
