package ru.cft.tasks;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;

public class IntegerFilePool extends FilePool<Integer> {

    public IntegerFilePool(Collection<Path> paths) {
        super(paths);
    }

    public IntegerFilePool(Collection<Path> paths, Comparator<Integer> comparator) {
        super(paths, comparator);
    }

    protected Integer cast(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }
}
