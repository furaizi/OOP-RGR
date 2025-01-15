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
}
