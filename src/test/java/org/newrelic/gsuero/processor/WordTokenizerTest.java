package org.newrelic.gsuero.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordTokenizerTest {

    @Test
    void tokenizeTest() {
        WordTokenizer tokenizer = new WordTokenizer().process("Garis Manuel Suero can't read sometimes");
        assertEquals(6, tokenizer.count());
        assertEquals("Garis", tokenizer.nextWord());
        assertEquals("Manuel", tokenizer.nextWord());
        assertEquals("Suero", tokenizer.nextWord());
        assertEquals("can't", tokenizer.nextWord());
        assertEquals("read", tokenizer.nextWord());
        assertEquals("sometimes", tokenizer.nextWord());
    }

    @Test
    void tokenizeWithManySpecialCharactersTest() {
        WordTokenizer tokenizer = new WordTokenizer().process("Garis Manuel Suero can't \n\r read sometimes!.");
        assertEquals(6, tokenizer.count());
        assertEquals("Garis", tokenizer.nextWord());
        assertEquals("Manuel", tokenizer.nextWord());
        assertEquals("Suero", tokenizer.nextWord());
        assertEquals("can't", tokenizer.nextWord());
        assertEquals("read", tokenizer.nextWord());
        assertEquals("sometimes", tokenizer.nextWord());
    }

    @Test
    void tokenizeWithLineEndingHyphenTest() {
        WordTokenizer tokenizer = new WordTokenizer().process("Garis Manuel Su-\n\rero can't \n\r read sometimes!.");
        assertEquals(6, tokenizer.count());
        assertEquals("Garis", tokenizer.nextWord());
        assertEquals("Manuel", tokenizer.nextWord());
        assertEquals("Suero", tokenizer.nextWord());
        assertEquals("can't", tokenizer.nextWord());
        assertEquals("read", tokenizer.nextWord());
        assertEquals("sometimes", tokenizer.nextWord());
    }

    @Test
    void tokenizerWithOtherApostrophes() {
        WordTokenizer tokenizer = new WordTokenizer().process("almost as soon as he came into the estate, concerning the rights\n" +
                "of fishing in the river or wood‐cutting in the forest, I don’t know\n" +
                "exactly which. He regarded it as his duty as a citizen and a man of");

        assertEquals(41, tokenizer.count());
    }

    @Test
    void tokenizeEmptyOrNullTest() {
        WordTokenizer tokenizer = new WordTokenizer().process("");
        assertEquals(0, tokenizer.count());

        tokenizer = new WordTokenizer().process(null);
        assertEquals(0, tokenizer.count());
    }
}
