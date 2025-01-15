package org.example.model;

import org.junit.jupiter.api.DisplayName;
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
        var model = new TextProcessor();
        model.setContent("");

        var actual = model.countWords();
        assertEquals(0, actual, "countWords should return 0 when empty string is passed");
    }

    @Test
    public void testCountWordsWithOneLineText_1() {
        var model = new TextProcessor();
        model.setContent("Hello world!");

        var actual = model.countWords();
        assertEquals(2, actual, "\"Hello world!\" are two words");
    }

    @DisplayName("test \"countWords\" method with one line and multiple spaces text")
    @Test()
    public void testCountWordsWithOneLineText_2() {
        var model = new TextProcessor();
        model.setContent("   Hello      world  !");

        var actual = model.countWords();
        assertEquals(2, actual, "\"   Hello      world  !\" are two words");
    }

    @Test
    public void testCountWordsWithMultipleLinesText_1() {
        var text = """
                To be, or not to be, that is the question:
                Whether 'tis nobler in the mind to suffer
                The slings and arrows of outrageous fortune,
                Or to take arms against a sea of troubles
                """;
        var model = new TextProcessor();
        model.setContent(text);

        var actual = model.countWords();
        assertEquals(34, actual, "This text contains 34 words");
    }

    @DisplayName("test \"countWords\" method with multiple lines and spaces text")
    @Test
    public void testCountWordsWithMultipleLinesText_2() {
        var text = """
                To    be   , or not   to be  , that is the question:    
                Whether    'tis nobler    in   the mind to    suffer
                    The slings    and arrows    of outrageous fortune   ,
                Or    to take    arms against   a    sea    of    troubles
                """;
        var model = new TextProcessor();
        model.setContent(text);

        var actual = model.countWords();
        assertEquals(34, actual, "This text contains 34 words");
    }
}
