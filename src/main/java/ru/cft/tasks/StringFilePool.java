package ru.cft.tasks;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;

public class StringFilePool extends FilePool<String> {

    public StringFilePool(Collection<Path> paths) {
        super(paths);
    }

    public StringFilePool(Collection<Path> paths, Comparator<String> comparator) {
        super(paths, comparator);
    }

    protected String cast(String value) throws NumberFormatException {
        return value;
    }
}
