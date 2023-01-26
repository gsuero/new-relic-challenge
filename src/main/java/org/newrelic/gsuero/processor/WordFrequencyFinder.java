package org.newrelic.gsuero.processor;

import org.newrelic.gsuero.processor.model.TextFrequencyRequest;
import org.newrelic.gsuero.processor.model.WordFrequencyResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;

/**
 * A program executable from the command line that when given text(s) will return 100 of most common three word sequences in descending order of frequency.
 *
 * For example, if I ran `ruby ./solution.rb texts/moby_dick.txt` or `java main.java texts/moby_dick.txt` the first lines of the result would (probably) be:
 *
 * ```
 * the sperm whale - 85
 * the white whale - 71
 * of the whale - 67
 * ```
 */
public class WordFrequencyFinder {

    private WordTokenizer tokenizer;

    public WordFrequencyFinder() {
        super();
        this.tokenizer = new WordTokenizer();
    }

    public WordFrequencyFinder(WordTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    private List<TextFrequencyRequest> requests = new ArrayList<>();


    public WordFrequencyFinder addFiles(String... fileNames) {
        if (fileNames == null || fileNames.length < 1) {
            throw new RuntimeException("Invalid input. No files provided.");
        }
        for (String fileName : fileNames) {
            Path temp = Path.of(fileName);
            if (Files.exists(temp)) {
                this.requests.add(new TextFrequencyRequest(temp));
            } else {
                throw new RuntimeException(String.format("File %s does not exists...", temp));
            }
        }
        return this;
    }
    public WordFrequencyFinder findFrequencies() {
        for (TextFrequencyRequest request : this.requests) {
            request.setWords(tokenizer.process(readFileAsString(request.getFilePath())).getAll());
            request.setFrequencies(new HashMap<>());
            calculateFrequencies(request.getWords(), 3, request.getFrequencies());
        }
        return this;
    }
    public List<WordFrequencyResult> getResults(int top) {
        List<WordFrequencyResult> result = new ArrayList<>();
        for (TextFrequencyRequest request : this.requests) {
            Map<String, Integer> occurrences = request.getFrequencies().entrySet().stream()
                    .sorted(reverseOrder(comparingInt(Map.Entry::getValue)))
                    .limit(top)
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new));

            result.add(new WordFrequencyResult(request.getFilePath().getFileName().toString(), occurrences));
        }

        return result;

    }

    private static String readFileAsString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void calculateFrequencies(List<String> words, int sequenceLength, Map<String, Integer> frequencyMap) {
        StringBuilder combined = new StringBuilder();
        int wordSequenceIndex = 0;
        for (int index = 0; index < words.size(); index++) {
            String word = words.get(index);

            combined.append(word.toLowerCase());

            wordSequenceIndex++;

            if (wordSequenceIndex == sequenceLength) {
                // go back second word after the previous start (w [w] w) w
                index = index - (sequenceLength - 1);
                addOrIncrement(combined.toString(), frequencyMap);
                combined.setLength(0);
                wordSequenceIndex = 0;

                continue;
            }
            combined.append(' ');

        }
    }

    private void addOrIncrement(String combinedWords, Map<String, Integer> frequencyMap) {
        if (frequencyMap.containsKey(combinedWords)) {
            frequencyMap.merge(combinedWords, 1, Integer::sum);
        } else {
            frequencyMap.put(combinedWords, 1);
        }
    }

}