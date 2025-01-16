package org.example.model;

import java.util.*;

public class TextProcessor {
    private final StringBuilder documentContent;

    public TextProcessor() {
        this.documentContent = new StringBuilder();
    }

    public void setContent(String text) {
        Objects.requireNonNull(text, "Text cannot be null");
        documentContent.setLength(0);
        documentContent.append(text);
    }

    public String getContent() {
        return documentContent.toString();
    }

    public int countWords() {
        var text = documentContent.toString().trim();
        return (int) Arrays.stream(text.split("\\s+"))
                            .filter(str -> str.chars().anyMatch(Character::isLetter))
                            .count();
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
        var updatedText = documentContent.toString().replace(target, replacement);
        documentContent.setLength(0);
        documentContent.append(updatedText);
    }

}
