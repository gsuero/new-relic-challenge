package org.newrelic.gsuero;

import org.apache.commons.lang3.StringUtils;
import org.newrelic.gsuero.processor.WordFrequencyFinder;
import org.newrelic.gsuero.processor.WordTokenizer;
import org.newrelic.gsuero.processor.model.WordFrequencyResult;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "checksum", mixinStandardHelpOptions = true, version = "checksum 4.0",
        description = "Executable from the command line that when given text(s) will return 100 of most common " +
                "three word sequences in descending order of frequency.")
public class Main implements Callable<Integer> {
    @CommandLine.Parameters(index = "0..*", description = "The file(s) to process")
    private String[] fileNames;

    @CommandLine.Option(names = "--stdin", interactive = true)
    private String value;


    public static void main(String[] args) throws IOException {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        WordFrequencyFinder finder = new WordFrequencyFinder(new WordTokenizer());

        if (StringUtils.isBlank(value)) {
            finder.addFiles(this.fileNames);
        } else {
            finder.addFiles(this.value);
        }

        List<WordFrequencyResult> results = finder
                .findFrequencies()
                .getResults(100);

        System.out.println(String.format("%d of most common three word sequences in descending order of frequency", 100));

        for (WordFrequencyResult result : results) {
            System.out.println(String.format("%s:", result.getFileName()));
            System.out.println(StringUtils.repeat('_', 63));
            printTable("WORDS", "OCCURRENCES");
            System.out.println(StringUtils.repeat('_', 63));
            result.getResults().entrySet().forEach(entry -> printTable(entry.getKey(), String.valueOf(entry.getValue())));
            System.out.println();
            System.out.println(StringUtils.repeat('#', 63));
            System.out.println();
        }

        return 0;
    }

    private void printTable(String words, String occurrences) {
        System.out.print(StringUtils.rightPad(words, 50));
        System.out.print("|");
        System.out.print(StringUtils.leftPad(occurrences, 12));
        System.out.println();
    }
}