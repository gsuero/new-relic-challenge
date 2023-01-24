package org.newrelic.gsuero.processor.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class TextFrequencyRequest {
    private final Path filePath;
    private List<String> words;
    private Map<String, Integer> frequencies;

    public TextFrequencyRequest(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Map<String, Integer> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Map<String, Integer> frequencies) {
        this.frequencies = frequencies;
    }
}
