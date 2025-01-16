package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextProcessorTest {

    @Test
    public void testSetRealContent() {
        var expected = "Hello world!";
        var model = new TextProcessor();
        model.setContent(expected);

        var actual = model.getContent();
        assertNotNull(actual, "Set content should not be null");
        assertEquals(expected, actual, "Set content should be equal to the origin");
    }

    @Test
    public void testSetNullContent() {
        var model = new TextProcessor();
        assertThrows(NullPointerException.class, () -> model.setContent(null), "setContent method should throw NullPointerException when null is supplied");
    }

    @Test
    public void testForceSetContent() {
        var oldText = "old text";
        var newText = "new text";
        var model = new TextProcessor();
        model.setContent(oldText);
        model.setContent(newText);

        var actual = model.getContent();
        assertNotNull(actual);
        assertEquals(newText, actual, "setContent method should set new content when supplied, not add");
    }

    @Test
    public void testCountWordsWithEmptyString() {
        var shouldReturn0WhenEmptyString = testCountWords( "", 0);
        assertTrue(shouldReturn0WhenEmptyString);
    }

    @Test
    public void testCountWordsWithOneLineText_1() {
        var shouldReturn2 = testCountWords("Hello world!", 2);
        assertTrue(shouldReturn2);
    }

    @Test()
    public void testCountWordsWithMultipleSpacesText() {
        var shouldReturn2 = testCountWords("   Hello      world  !", 2);
        assertTrue(shouldReturn2);
    }

    @Test
    public void testCountWordsWithMultipleLinesText() {
        var text = """
                To be, or not to be, that is the question:
                Whether 'tis nobler in the mind to suffer
                The slings and arrows of outrageous fortune,
                Or to take arms against a sea of troubles
                """;
        var shouldReturn34 = testCountWords(text, 34);
        assertTrue(shouldReturn34);
    }

    @Test
    public void testCountWordsWithMultipleLinesAndSpacesText() {
        var text = """
                To    be   , or not   to be  , that is the question:    
                Whether    'tis nobler    in   the mind to    suffer
                    The slings    and arrows    of outrageous fortune   ,
                Or    to take    arms against   a    sea    of    troubles
                """;
        var shouldReturn34 = testCountWords(text, 34);
        assertTrue(shouldReturn34);
    }

    @Test
    public void testRemoveExtraSpacesWithEmptyString() {
        var shouldReturnEmptyString = testRemoveExtraSpaces("", "");
        assertTrue(shouldReturnEmptyString);
    }

    @Test
    public void testRemoveExtraSpacesWithOneSpaceString() {
        var shouldReturnOneSpace = testRemoveExtraSpaces(" ", " ");
        assertTrue(shouldReturnOneSpace);
    }

    @Test
    public void testRemoveExtraSpacesWithMultipleSpacesString() {
        var shouldReturnOneSpace = testRemoveExtraSpaces("       ", " ");
        assertTrue(shouldReturnOneSpace);
    }

    @Test
    public void testRemoveExtraSpacesWithOneLineText() {
        var text = "Hello world!";
        var shouldNotChangeContent = testRemoveExtraSpaces(text, text);
        assertTrue(shouldNotChangeContent);
    }

    @Test
    public void testRemoveExtraSpacesWithOneLineMultipleSpacesText() {
        var shouldRemoveExtraSpaces = testRemoveExtraSpaces("Hello      world!", "Hello world!");
        assertTrue(shouldRemoveExtraSpaces);
    }

    @Test
    public void testRemoveExtraSpacesWithMultipleLinesText() {
        var text = """
                To be, or not to be, that is the question:
                Whether 'tis nobler in the mind to suffer
                The slings and arrows of outrageous fortune,
                Or to take arms against a sea of troubles
                """;
        var shouldNotChangeContent = testRemoveExtraSpaces(text, text);
        assertTrue(shouldNotChangeContent);
    }

    @Test
    public void testRemoveExtraSpacesWithMultipleLinesAndSpacesText() {
        var defaultText = """
                To be, or not to be, that is the question:
                Whether 'tis nobler in the mind to suffer
                The slings and arrows of outrageous fortune,
                Or to take arms against a sea of troubles
                """;
        var spacedText = """
                To      be, or not    to be, that is     the question:
                Whether     'tis nobler in the mind      to suffer
                The slings       and arrows           of outrageous fortune,
                Or to    take arms     against a       sea of troubles
                """;

        var shouldRemoveExtraSpaces = testRemoveExtraSpaces(spacedText, defaultText);
        assertTrue(shouldRemoveExtraSpaces);
    }

    private static boolean testCountWords(String content, int expected) {
        var model = new TextProcessor();
        model.setContent(content);
        var actual = model.countWords();
        return expected == actual;
    }

    private static boolean testRemoveExtraSpaces(String content, String expected) {
        var model = new TextProcessor();
        model.setContent(content);
        model.removeExtraSpaces();
        return expected.equals(model.getContent());
    }
}
