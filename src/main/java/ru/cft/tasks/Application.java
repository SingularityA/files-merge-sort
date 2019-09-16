package ru.cft.tasks;

import org.apache.commons.cli.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class Application {

    private final static Logger logger = Logger.getLogger(Application.class.getSimpleName());

    private final static Collection<Path> inputPaths = new ArrayList<>();
    private static Path outputPath;

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(prepareOptions(), args);
        } catch (ParseException e) {
            logger.info("Incorrect command line arguments");
            return;
        }

        List<String> leftovers = commandLine.getArgList();
        if (leftovers.size() < 2) {
            logger.info("You need to specify at list one input and one output file");
            return;
        }
        outputPath = Paths.get(leftovers.get(0));
        for (int i = 1; i < leftovers.size(); i++) {
            Path path = Paths.get(leftovers.get(i));
            if (Files.exists(path)) {
                inputPaths.add(path);
            }
            else {
                logger.info("File [" + path.toString() + "] doesn't exist. It will be ignored");
            }
        }

        if (inputPaths.size() < 1) {
            logger.info("You need to specify at list one correct input file");
            return;
        }

        if (commandLine.hasOption("i")) {
            parse(commandLine, Integer::parseInt);
        } else if (commandLine.hasOption("s")) {
            parse(commandLine, v -> v);
        } else {
            logger.info("Missing arg -i or -s");
        }
    }

    private static <T extends Comparable<T>> void parse(CommandLine commandLine, Function<String, T> castFunction) {
        Comparator<T> comparator;
        if (commandLine.hasOption("d")) {
            comparator = Comparator.reverseOrder();
        } else {
            comparator = Comparator.naturalOrder();
        }

        FilePool<T> filePool = new FilePool<T>(inputPaths, comparator) {
            @Override
            protected T cast(String value) throws NumberFormatException {
                return castFunction.apply(value);
            }
        };

        FilesMerger<T> filesMerger = new FilesMerger<>(filePool);
        filesMerger.merge(outputPath);
    }

    private static Options prepareOptions() {
        Options options = new Options();

        Option descend = Option.builder("d")
                .longOpt("descending")
                .build();
        Option ascend = Option.builder("a")
                .longOpt("ascending")
                .build();
        Option intType = Option.builder("i")
                .longOpt("integer")
                .build();
        Option stringType = Option.builder("s")
                .longOpt("string")
                .build();

        options.addOption(descend)
                .addOption(ascend)
                .addOption(intType)
                .addOption(stringType);
        return options;
    }
}
