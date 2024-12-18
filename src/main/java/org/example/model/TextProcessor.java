package org.example.model;

import java.util.*;
import java.util.stream.Collectors;


public class TextProcessor {
    private final StringBuilder documentContent;

    public TextProcessor() {
        this.documentContent = new StringBuilder();
    }

    public void setContent(String text) {
        Objects.requireNonNull(text, "Text cannot be null");
        documentContent.setLength(0); // Очистка содержимого перед установкой нового текста
        documentContent.append(text);
    }

    public String getContent() {
        return documentContent.toString();
    }


    public void appendContent(String text) {
        Objects.requireNonNull(text, "Text to append cannot be null");
        documentContent.append(text);
    }


    public int countWords() {
        var text = documentContent.toString().trim();
        return text.isEmpty() ? 0 : text.split("\\s+").length;
    }


    public int countCharacters() {
        return documentContent.length();
    }


    public void removeExtraSpaces() {
        var cleanedText = documentContent.toString()
                                         .trim()
                                         .replaceAll("\\s+", " ");
        documentContent.setLength(0);
        documentContent.append(cleanedText);
    }


    public void replaceText(String target, String replacement) {
        Objects.requireNonNull(target, "Target text cannot be null");
        Objects.requireNonNull(replacement, "Replacement text cannot be null");
        var updatedText = documentContent.toString()
                                         .replace(target, replacement);
        documentContent.setLength(0);
        documentContent.append(updatedText);
    }


    public void sortLines(boolean byLength) {
        var sortedLines = Arrays.stream(documentContent.toString().split("\\n"))
                                      .sorted(byLength ? Comparator.comparingInt(String::length) : String::compareTo)
                                      .collect(Collectors.joining("\n"));

        documentContent.setLength(0);
        documentContent.append(sortedLines);
    }


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
