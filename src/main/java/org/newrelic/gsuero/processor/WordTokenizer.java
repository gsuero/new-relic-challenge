package org.newrelic.gsuero.processor;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.RegExUtils.removePattern;
import static org.apache.commons.lang3.RegExUtils.replacePattern;

public class WordTokenizer {

    private final static Pattern WORD_PATTERN = Pattern.compile("(\\w+((\\'|’)(t|s))?)");
    private List<String> words;
    private int wordIndex = 0;

    /**
     * Process file and matches all words into tokenizer
     * @return this
     */
    WordTokenizer process(String text) {
        this.words = new ArrayList<>();
        if (StringUtils.isBlank(text)) {
            return this;
        }

        Matcher wordMatcher = WORD_PATTERN.matcher(sanitize(text));
        while(wordMatcher.find()) {
            if (wordMatcher.groupCount() > 0) {
                words.add(wordMatcher.group(1));
            }
        }
        this.wordIndex = 0;
        return this;
    }

    /**
     * Returns first next word found in the processed text
     * @return
     */
    String nextWord() {
        return IterableUtils.get(words, wordIndex++);
    }

    /**
     * Gets word count
     * @return
     */
    int count() {
        return words.size();
    }

    List<String> getAll() {
        return words;
    }
    private String sanitize(String text) {
        // remove carriage returns and hyphens
        return removePattern(replacePattern(text, "[\\n\\r]+", " "), "(\\-\\s|\\-)");
    }



}
