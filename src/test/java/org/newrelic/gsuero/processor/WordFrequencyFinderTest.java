package org.newrelic.gsuero.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.newrelic.gsuero.processor.model.WordFrequencyResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordFrequencyFinderTest {

    @Test
    void findFrequenciesTest() {
        List<WordFrequencyResult> results = new WordFrequencyFinder()
                .addFiles("text-files/test.txt")
                .findFrequencies().getResults(3);

        assertEquals(1, results.size());

        assertEquals(2, results.get(0).getResults().get("garis manuel suero"));
        assertEquals(2, results.get(0).getResults().get("suero can't read"));
    }

    @Test
    void findFrequenciesTestLargerTest() {
        List<WordFrequencyResult> results = new WordFrequencyFinder()
                .addFiles("text-files/brothers-karamazov.txt", "text-files/moby-dick.txt")
                .findFrequencies()
                .getResults(5);

        assertEquals(2, results.size());

        assertEquals("brothers-karamazov.txt", results.get(0).getFileName());
        assertEquals(5, results.get(0).getResults().size());

        assertEquals("moby-dick.txt", results.get(1).getFileName());
        assertEquals(5, results.get(1).getResults().size());
    }

    @Test
    void findFrequenciesNoFiles() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new WordFrequencyFinder().addFiles(new String[] {});
        });
        Assertions.assertEquals("Invalid input. No files provided.", exception.getMessage());
    }

    @Test
    void findFrequenciesNoFile() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            new WordFrequencyFinder().addFiles("non-existent.file").findFrequencies();
        });

        Assertions.assertEquals("File non-existent.file does not exists...", exception.getMessage());
    }
}
