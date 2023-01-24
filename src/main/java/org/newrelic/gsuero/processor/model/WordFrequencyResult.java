package org.newrelic.gsuero.processor.model;

import java.util.Map;

public class WordFrequencyResult {
    private String fileName;
    private Map<String, Integer> results;

    public WordFrequencyResult(String fileName, Map<String, Integer> results) {
        this.fileName = fileName;
        this.results = results;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, Integer> getResults() {
        return results;
    }
}
