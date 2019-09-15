package ru.cft.tasks;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class BasicFunctionalityTest {

    private final String root = System.getProperty("user.dir") + "\\src\\test\\resources\\";
    private final Collection<Path> inputPaths = Arrays.asList(
            Paths.get(root + "input\\input1.txt"),
            Paths.get(root + "input\\input2.txt"),
            Paths.get(root + "input\\input3.txt")
    );
    private final Path outputPath = Paths.get(root + "output\\output.txt");

    @Test
    public void testIntegersAscending() {
        Comparator<Integer> comparator = Comparator.naturalOrder();
        FilePool<Integer> filePool = new FilePool<Integer>(inputPaths, comparator) {
            @Override
            protected Integer cast(String value) throws NumberFormatException {
                return Integer.parseInt(value);
            }
        };

        FilesMerger<Integer> merger = new FilesMerger<>(filePool);
        merger.merge(outputPath);
    }

    @Test
    public void testIntegersDescending() {
        Comparator<Integer> comparator = Comparator.reverseOrder();
        FilePool<Integer> filePool = new FilePool<Integer>(inputPaths, comparator) {
            @Override
            protected Integer cast(String value) throws NumberFormatException {
                return Integer.parseInt(value);
            }
        };

        FilesMerger<Integer> merger = new FilesMerger<>(filePool);
        merger.merge(outputPath);
    }

    @Test
    public void testStringsAscending() {
        Comparator<String> comparator = Comparator.naturalOrder();
        FilePool<String> filePool = new FilePool<String>(inputPaths, comparator) {
            @Override
            protected String cast(String value) {
                return value;
            }
        };

        FilesMerger<String> merger = new FilesMerger<>(filePool);
        merger.merge(outputPath);
    }

    @Test
    public void testStringsDescending() {
        Comparator<String> comparator = Comparator.reverseOrder();
        FilePool<String> filePool = new FilePool<String>(inputPaths, comparator) {
            @Override
            protected String cast(String value) {
                return value;
            }
        };

        FilesMerger<String> merger = new FilesMerger<>(filePool);
        merger.merge(outputPath);
    }
}
